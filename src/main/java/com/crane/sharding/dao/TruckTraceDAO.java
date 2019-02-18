package com.crane.sharding.dao;

import com.crane.sharding.DO.TruckTraceDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 *
 *  dao
 *
 * @author szss
 * @date 2018年09月30日
 */
public interface TruckTraceDAO{

   /**
     * 根据主键查询
     * @param id
     * @return
     */
    TruckTraceDO queryById(@Param("id") Long id);

    /**
     * 货车到货
     *
     * @param truckTraceCode 物流跟踪单号
     * @return 成功返回1，失败返回0
     */
    Integer truckArrival(String truckTraceCode);

    Integer insert(TruckTraceDO truckTraceDO);

    Integer update(TruckTraceDO truckTraceDO);

    Integer delete(Long truckTraceDO);

    List<TruckTraceDO> query(TruckTraceDO truckTraceDO);

    Integer queryCount(TruckTraceDO truckTraceDO);
}