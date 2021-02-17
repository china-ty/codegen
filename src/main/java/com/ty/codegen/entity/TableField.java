package com.ty.codegen.entity;

import lombok.Data;

/**
 * @Project: codegen
 * @ClassName: TableField
 * @Author: ty
 * @Description: 表字段信息
 * @Date: 2021/1/21
 * @Version: 1.0
 **/
@Data
public class TableField {
    /**
     * 字段名
     */
    private String name;
    /**
     * 数据库类型
     */
    private String dbType;
    /**
     * 对象类型
     */
    private String entityType;
    /**
     * 字段长度
     */
    private Integer length;
    /**
     * 小数位数
     */
    private Integer decimal;
    /**
     * 是否必填
     */
    private Boolean required;
    /**
     * 注释
     */
    private String remarks;
}
