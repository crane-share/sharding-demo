package com.crane.sharding.controller;

import com.crane.sharding.DO.TruckTraceDO;
import com.crane.sharding.service.TruckTraceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 控制器
 *
 * @author szss
 * @date 2018年09月30日
 */
@RestController
public class TruckTraceController {

    private static Logger LOGGER = LoggerFactory.getLogger(TruckTraceController.class);

    @Autowired
    private TruckTraceService truckTraceService;


    /**
     * post一个对象入库
     *
     * @param truckTraceDO
     * @return
     */
    @PostMapping("/api/truckTrace")
    public Integer saveTruckTrace(@RequestBody TruckTraceDO truckTraceDO) {
        try {
            if (null == truckTraceDO) {
                return 0;
            }

            Integer cnt = truckTraceService.save(truckTraceDO);
            //如果cnt为0不符合业务，需要自己判断后设置result为fail
            return cnt;
        } catch (Exception e) {
            LOGGER.error("saveTruckTrace exception,{}", truckTraceDO, e);
            return -1;
        }
    }

    /**
     * 修改一个对象
     *
     * @param truckTraceDO
     * @return
     */
    @PutMapping("/api/truckTrace")
    public Integer updateTruckTrace(@RequestBody TruckTraceDO truckTraceDO) {
        try {
            if (null == truckTraceDO || truckTraceDO.getId() == null) {
                return 0;
            }

            Integer cnt = truckTraceService.update(truckTraceDO);
            //如果cnt为0不符合业务，需要自己判断后设置result为fail
            return cnt;
        } catch (Exception e) {
            LOGGER.error("updateTruckTrace exception,{}", truckTraceDO, e);
            return -1;
        }
    }

}
