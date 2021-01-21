package com.ty.codegen.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import sun.swing.ImageIconUIResource;

import javax.swing.*;
import java.io.InputStream;

/**
 * @Project: codegen
 * @ClassName: IconUtil
 * @Author: ty
 * @Description: Icon图标工具类
 * @Date: 2021/1/21
 * @Version: 1.0
 **/
public class IconUtil {

    // 图片文件根路径
    private static final String ROOT_PATH = "picture/";

    private IconUtil() {
    }

    /**
     * 加载图片资源
     * @param path
     * @return
     */
    private static Icon load(String path) {
        // 由于可能会使用打包的方式运行程序,所以使用流的方式获取文件
        InputStream fileStream = ResourceUtil.getStream(ROOT_PATH + path);
        // 将文件流转为byte数组
        byte[] fileByte = IoUtil.readBytes(fileStream);
        return new ImageIconUIResource(fileByte);
    }

    public static final Icon MYSQL = load("mysql.png");
    public static final Icon TABLE = load("table.png");
}
