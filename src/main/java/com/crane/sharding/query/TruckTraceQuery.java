package com.crane.sharding.query;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *
 *  查询条件
 *
 * @author szss
 * @date 2018年10月09日
 */
@Data
public class TruckTraceQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 物流跟踪单号
     */
    private String truckTraceCode;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 货车跟踪单状态 1初始化 、2运输中 、3已到货
     */
    private Integer status;
    /**
     * 扩展字段
     */
    private String extAtt;
}