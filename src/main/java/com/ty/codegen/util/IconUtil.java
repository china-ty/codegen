package com.ty.codegen.util;

import javax.swing.*;

/**
 * @Project: codegen
 * @ClassName: IconUtil
 * @Author: ty
 * @Description: Icon图标工具类
 * @Date: 2021/1/21
 * @Version: 1.0
 **/
public class IconUtil {

    private IconUtil(){}

    private static Icon load(String path) {
        return new ImageIcon(path);
    }

    public static final Icon MYSQL = load("src/com/ty/codegen/img/mysql.png");
    public static final Icon TABLE = load("src/com/ty/codegen/img/table.png");
}
