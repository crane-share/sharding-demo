package com.crane.sharding.service.impl;

import com.crane.sharding.DO.TruckTraceDO;
import com.crane.sharding.dao.TruckTraceDAO;
import com.crane.sharding.query.TruckTraceQuery;
import com.crane.sharding.service.TruckTraceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * service层接口实现
 *
 * @author szss
 * @date 2018年09月30日
 */
@Slf4j
@Service
public class TruckTraceServiceImpl implements TruckTraceService {

    @Resource
    private TruckTraceDAO truckTraceDAO;

    private final static String LOCATE_FAIL = "定位失败";

    /**
     * 地球的赤道半径，单位为公里
     */
    private final static double EARTH_RADIUS = 6378.137;

    private final static int CLOSED_DISTANCE = 1000;

    @Override
    //@Transactional(transactionManager = "shadringTransactionManager")
    public Integer save(TruckTraceDO truckTraceDO) {
        return truckTraceDAO.insert(truckTraceDO);
    }

    @Override
    public Integer update(TruckTraceDO truckTraceDO) {
        if ((truckTraceDO = truckTraceDAO.queryById(truckTraceDO.getId())) == null) {
            return 0;
        }

        return truckTraceDAO.update(truckTraceDO);
    }



    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两个坐标之间的距离，单位为米
     *
     * @param lat1 坐标1的纬度
     * @param lon1 坐标1的经度
     * @param lat2 坐标2的纬度
     * @param lon2 坐标2的经度
     * @return 返回两个坐标之间的距离，单位为米
     */
    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

}
