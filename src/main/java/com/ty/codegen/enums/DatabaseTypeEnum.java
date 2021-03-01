package com.ty.codegen.enums;

import lombok.Getter;

/**
 * 系统支持的数据库枚举类
 */
@Getter
public enum DatabaseTypeEnum {
    // MySql数据库
    MYSQL("MySql"),
    // Oracle数据库
    ORACLE("Oracle");

    private String type;


    DatabaseTypeEnum(String type){
        this.type = type;
    }

    // 将枚举所有类型转为String数组
    public static String[] toStringArray() {
        DatabaseTypeEnum[] values = values();
        String[] databaseArray = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            databaseArray[i] = values[i].getType();
        }
        return databaseArray;
    }
}
