package com.crane.sharding.DO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *
 *  数据库实体
 *
 * @author szss
 * @date 2018年09月30日
 */
@Data
public class TruckTraceDO {

    private Long id;

    /**
     * 货车跟踪单号
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