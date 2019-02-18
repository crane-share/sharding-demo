package com.crane.sharding.service;


import com.crane.sharding.DO.TruckTraceDO;
import com.crane.sharding.query.TruckTraceQuery;

import java.util.List;


/**
 * service层接口
 *
 * @author szss
 * @date 2018年09月30日
 */
public interface TruckTraceService {

    /**
     * 保存数据
     *
     * @param
     * @return
     */
    Integer save(TruckTraceDO truckTraceDO);

    /**
     * 修改数据,必须带有ID
     *
     * @param truckTraceDTO
     * @return
     */
    Integer update(TruckTraceDO truckTraceDTO);
}
