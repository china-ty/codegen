package com.ty.codegen.util;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**
 * @Project: codegen
 * @ClassName: WinGlobalSettingUtil
 * @Author: ty
 * @Description: 全局设置窗体工具类
 * @Date: 2021/1/16
 * @Version: 1.0
 **/
public class WinGlobalSettingUtil {

    private WinGlobalSettingUtil(){}

    /**
     * 在创建窗体(new JFrame())之前进行初始化
     * 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体
     */
    public static void initGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
    /**
     * 在创建窗体(new JFrame())之前进行初始化
     * 统一设置窗体样式风格,
     */
    public static void initWinStyle() {
        try {
           // String[] lafs = new String[]{"de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel", "javax.swing.plaf.metal.MetalLookAndFeel"};
            String[] lafs = new String[]{"de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel", "de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel"};
            String[] li = new String[]{"Licensee=Jyloo Software", "LicenseRegistrationNumber=------", "Product=Synthetica", "LicenseType=For internal tests only", "ExpireDate=--.--.----", "MaxVersion=2.31.999"};
            UIManager.put("Synthetica.license.info", li);
            UIManager.put("Synthetica.license.key", "FFFCC94B-00A0D2E4-5FECE971-5D4FD24F");
            UIManager.setLookAndFeel(lafs[1]);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println("窗体样式风格初始异常");
            e.printStackTrace();
        }
    }
}
