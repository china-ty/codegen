package com.ty.codegen.util;

import com.mysql.cj.util.StringUtils;
import org.dtools.ini.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * .ini 文件工具类
 */
public class IniUtil {

    private static final String SYSTEM_CONFIG_FILE = "config/config.ini";
    private static IniFileReader fileReader = null;
    private static IniFile iniFile = null;

    private IniUtil() {
    }

    /**
     * 获取扩展名为.ini配置文件
     * @return
     */
    private static IniFile getIniFile() {
        // 判断系统是否有缓存对象
        if (IniUtil.iniFile != null && fileReader != null) {
            try {
                // 再一次读取,获取最新配置信息
                fileReader.read();
            } catch (IOException e) {
                System.out.println("读取系统文件失败" + e.getMessage());
                e.printStackTrace();
                return null;
            }
            return IniUtil.iniFile;
        }
        // 以下代码 实现 无缓存对象,进行创建配置对象
        IniFile iniFile = new BasicIniFile();
        File systemConfigFile =  getSystemConfigFile();
        if (systemConfigFile == null) {
            return null;
        }
        IniFileReader iniFileReader = new IniFileReader(iniFile, systemConfigFile);
        try {
            iniFileReader.read();
        } catch (IOException e) {
            System.out.println("读取系统文件失败" + e.getMessage());
            e.printStackTrace();
            return null;
        }
        IniUtil.fileReader = iniFileReader;
        IniUtil.iniFile = iniFile;
        return iniFile;
    }

    /**
     * 获取系统配置文件 如果不存在系统将自动创建配置文件
     * 创建 失败 将 返回null
     * @return
     */
    private static File getSystemConfigFile() {
        File file = new File(SYSTEM_CONFIG_FILE);
        // 判断配置文件是否存在
        if (!file.exists()) {
            try {
                // 不存在 进行 创建
                boolean isCreate = file.createNewFile();
                if (!isCreate) {
                    System.out.println("创建系统配置文件失败");
                    return null;
                }
            } catch (IOException e) {
                System.out.println("创建系统配置文件失败" + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }

    /**
     * 读取配置信息
     * @param sectionName 必传 ,必须是已存在的节点
     * @param key 必传 必须是已存在的节点
     * @return
     */
    public static String readProperties(String sectionName,String key) {
        // 参数验证
        if (StringUtils.isNullOrEmpty(sectionName) || StringUtils.isNullOrEmpty(key)) {
            return null;
        }
        // 获取Ini文件
        IniFile iniFile = getIniFile();
        if (iniFile == null) {
            return null;
        }
        // 获取所有section
        List<String> sectionNameList = iniFile.getSections().stream()
                .map(IniSection::getName).collect(Collectors.toList());
        // 判断传入的section是否存在文件中
        boolean isContains = sectionNameList.contains(sectionName);
        if (!isContains) {
            return null;
        }
        // 获取参数
        IniSection iniFileSection = iniFile.getSection(sectionName);
        IniItem iniItem = iniFileSection.getItem(key);
        return iniItem.getValue();
    }

    /**
     * 添加
     * @param sectionName 必传 当section不存在文件中 会自动创建section节点
     * @param key 必传 当key不存在文件中 会自动创建key节点, 如果存在 那么将根据value进行修改
     * @param value 必传 添加值
     * @return
     */
    public static boolean writerProperties(String sectionName,String key,String value) {
        // 参数验证
        if (StringUtils.isNullOrEmpty(sectionName) || StringUtils
                .isNullOrEmpty(key) || StringUtils.isNullOrEmpty(value)) {
            return false;
        }
        // 获取Ini文件
        IniFile iniFile = getIniFile();
        if (iniFile == null) {
            return false;
        }
        // 获取参数
        IniSection iniFileSection = iniFile.getSection(sectionName);
        // 获取不到,将自动创建section节点
        if (iniFileSection == null) {
            iniFileSection = iniFile.addSection(sectionName);
        }
        IniItem iniItem = iniFileSection.addItem(key);
        // 添加失败 说明已经存在,改为获取Item
        if (iniItem == null) {
            iniItem = iniFileSection.getItem(key);
        }
        // 设置值
        iniItem.setValue(value);
        // 获取配置文件 如果不存在系统将自动创建配置文件
        File systemConfigFile = getSystemConfigFile();
        if (systemConfigFile == null) {
            return false;
        }
        IniFileWriter iniFileWriter = new IniFileWriter(iniFile, systemConfigFile);
        try {
            // 写入配置文件
            iniFileWriter.write();
        } catch (IOException e) {
            System.out.println("写入配置文件失败" + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
