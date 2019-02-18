package com.crane.sharding.config;

import com.google.common.base.Preconditions;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import io.shardingsphere.core.keygen.TimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 给予Twiter的snowflake
 *
 * 参考地址：https://blog.csdn.net/feelwing1314/article/details/80237178
 *
 *
 */
public class SntKeyGenerator {

    private static final Logger log = LoggerFactory.getLogger(DefaultKeyGenerator.class);
    public static final long EPOCH;
    private static final long SEQUENCE_BITS = 12L;
    private static final long WORKER_ID_BITS = 10L;
    private static final long SEQUENCE_MASK = 4095L;
    private static final long WORKER_ID_LEFT_SHIFT_BITS = 12L;
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = 22L;
    private static final long WORKER_ID_MAX_VALUE = 1024L;
    private static TimeService timeService = new TimeService();
    private static long workerId;
    private long sequence;
    private long lastTime;

    public SntKeyGenerator() {
        long initWorkId = this.initWorkerId();
        Preconditions.checkArgument(initWorkId >= 0L && initWorkId < WORKER_ID_MAX_VALUE);
        workerId = this.initWorkerId();

    }

    public static void setWorkerId(long initWorkId) {
        Preconditions.checkArgument(initWorkId >= 0L && initWorkId < WORKER_ID_MAX_VALUE);
        workerId = workerId;
    }

    public synchronized Number generateKey() {
        long currentMillis = timeService.getCurrentMillis();
        Preconditions.checkState(this.lastTime <= currentMillis, "Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds", new Object[]{this.lastTime, currentMillis});
        if (this.lastTime == currentMillis) {
            if (0L == (this.sequence = ++this.sequence & 4095L)) {
                currentMillis = this.waitUntilNextTime(currentMillis);
            }
        } else {
            this.sequence = 0L;
        }

        this.lastTime = currentMillis;
        if (log.isDebugEnabled()) {
            log.debug("{}-{}-{}", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new Date(this.lastTime)), workerId, this.sequence);
        }

        return currentMillis - EPOCH << 22 | workerId << 12 | this.sequence;
    }

    private long waitUntilNextTime(long lastTime) {
        long time;
        for(time = timeService.getCurrentMillis(); time <= lastTime; time = timeService.getCurrentMillis()) {
        }

        return time;
    }

    public static void setTimeService(TimeService timeService) {
        timeService = timeService;
    }

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 10, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        EPOCH = calendar.getTimeInMillis();
    }

    /**
     *
     * TODO 需要切换成根据机器名来定义workID
     *  workerId生成的规则后，感觉对服务器IP后10位（特别是IPV6）数值比较约束.
     *
     * <p>
     * 有以下优化思路：
     * 因为workerId最大限制是2^10，我们生成的workerId只要满足小于最大workerId即可。
     * 1.针对IPV4:
     * ....IP最大 255.255.0，1.255。而（255+255+255+255) < 1024。
     * ....因此采用IP段数值相加即可生成唯一的workerId，不受IP位限制。
     * 2.针对IPV6:
     * ....IP最大ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff
     * ....为了保证相加生成出的workerId < 1024,思路是将每个bit位的后6位相加。这样在一定程度上也可以满足workerId不重复的问题。
     * </p>
     * 使用这种IP生成workerId的方法,必须保证IP段相加不能重复
     *
     * @author DogFc
     */
    protected long initWorkerId() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (final UnknownHostException e) {
            throw new IllegalStateException("Cannot get LocalHost InetAddress, please check your network!");
        }
        // 得到IP地址的byte[]形式值
        byte[] ipAddressByteArray = address.getAddress();
        long workerId = 0L;
        //如果是IPV4，计算方式是遍历byte[]，然后把每个IP段数值相加得到的结果就是workerId
        if (ipAddressByteArray.length == 4) {
            for (byte byteNum : ipAddressByteArray) {
                workerId += byteNum & 0xFF;
            }
            //如果是IPV6，计算方式是遍历byte[]，然后把每个IP段后6位（& 0B111111 就是得到后6位）数值相加得到的结果就是workerId
        } else if (ipAddressByteArray.length == 16) {
            for (byte byteNum : ipAddressByteArray) {
                workerId += byteNum & 0B111111;
            }
        } else {
            throw new IllegalStateException("Bad LocalHost InetAddress, please check your network!");
        }
        return workerId;
    }


    /**
     * 根据机器名最后的数字编号获取工作进程Id.如果线上机器命名有统一规范,建议使用此种方式.
     * 例如机器的HostName为:dangdang-db-sharding-dev-01(公司名-部门名-服务名-环境名-编号)
     * ,会截取HostName最后的编号01作为workerId.
     *
     * @author DonneyYoung
     **/
    private long initWorkerIdHostName() {
        InetAddress address;
        Long workerId;
        try {
            address = InetAddress.getLocalHost();
        } catch (final UnknownHostException e) {
            throw new IllegalStateException("Cannot get LocalHost InetAddress, please check your network!");
        }
        // 先得到服务器的hostname，例如JTCRTVDRA44，linux上可通过命令"cat /proc/sys/kernel/hostname"查看；
        String hostName = address.getHostName();
        try {
            // 计算workerId的方式：
            // 第一步hostName.replaceAll("\\d+$", "")，即去掉hostname后纯数字部分，例如JTCRTVDRA44去掉后就是JTCRTVDRA
            // 第二步hostName.replace(第一步的结果, "")，即将原hostname的非数字部分去掉，得到纯数字部分，就是workerId
            workerId = Long.valueOf(hostName.replace(hostName.replaceAll("\\d+$", ""), ""));
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Wrong hostname:%s, hostname must be end with number!", hostName));
        }
        return workerId;
    }
}
