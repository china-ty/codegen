package com.ty.codegen.util;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Project: codegen
 * @ClassName: IconUtil
 * @Author: ty
 * @Description: Icon图标工具类
 * @Date: 2021/1/21
 * @Version: 1.0
 **/
@Slf4j
public class IconUtil {

    // 图片文件根路径
    private static final String ROOT_PATH = "/picture/";

    private IconUtil() {
    }

    public static final Icon MYSQL = load("mysql.png");
    public static final Icon TABLE = load("table.png");
    public static final Icon REFRESH = load("refresh.png");
    public static final Icon DISCONNECTED = load("disconnected.png");
    public static final Icon CODE = load("code.png");
    public static final Icon PREVIEW = load("preview.png");
    /**
     * 加载图片资源
     *
     * @param fileName 文件名包含扩展名
     * @return
     */
    private static Icon load(String fileName) {
        ImageIcon imageIcon = null;
        try (
                // JVM会自动关闭流,但还是手动关闭一下
                // 由于可能会使用打包的方式运行程序,所以使用流的方式获取文件
                InputStream fileInputStream = IconUtil.class.getResource(ROOT_PATH + fileName).openStream();
        ) {
            // 将文件流转为byte数组
            byte[] fileByte = IconUtil.readBytes(fileInputStream);
            imageIcon = new ImageIcon(fileByte);
            if (fileInputStream != null) {
                // 自己手动关闭
                fileInputStream.close();
            }
        } catch (IOException e) {
            log.error("加载系统图标资源失败!");
            return imageIcon;
        }
        return imageIcon;
    }

    /**
     * 将输入流转为Byte数组
     *
     * @param inputStream 输入流 不会关闭传入的流
     * @return byte[]
     * @throws IOException
     */
    public static byte[] readBytes(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return new byte[0];
        }
        // 流的长度
        int length = inputStream.available();
        byte[] fileByte = new byte[length];
        inputStream.read(fileByte, 0, length);
        return fileByte;
    }
}
