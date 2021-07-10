package com.ty.codegen.util;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;

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
            String[] lafs = new String[]{"com.ty.codegen.ui.StandardLookAndFeel", "de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel"};
            String[] li = new String[]{"Licensee=Jyloo Software", "LicenseRegistrationNumber=------", "Product=Synthetica", "LicenseType=For internal tests only", "ExpireDate=--.--.----", "MaxVersion=2.32.999"};
            UIManager.put("Synthetica.license.info", li);
            UIManager.put("Synthetica.license.key", "23588378-2E48AC21-2654FBA8-E05135A0");
            String[] li2 = new String[]{"Licensee=Jyloo Software", "LicenseRegistrationNumber=------", "Product=SyntheticaAddons", "LicenseType=For internal tests only", "ExpireDate=--.--.----", "MaxVersion=1.13.999"};
            UIManager.put("SyntheticaAddons.license.info", li2);
            UIManager.put("SyntheticaAddons.license.key", "C7D98D0B-FDDD0183-77B7ADAD-E253A0F5");
            UIManager.setLookAndFeel(lafs[1]);
            // 统一设置字体
            SyntheticaLookAndFeel.setFont("新宋体", 14);
            // 设置导航栏(工具栏)最左边是否可拖动的大小
            UIManager.put("Synthetica.toolBar.handle.size",0);
            UIManager.put( "Tree.selectionForeground",Color.RED);
            UIManager.put( "Tree.selectionBackground",Color.RED);
            UIManager.put( "Tree.selectionBorderColor",Color.RED);
            // JYDocking 参数 设置中文工具提示
            UIManager.put("JYDocking.titlebar.minimizeButton.toolTip" , "最小化");
            UIManager.put("JYDocking.titlebar.maximizeButton.toolTip", "最大化");
            UIManager.put("JYDocking.titlebar.floatButton.toolTip", "脱离");
            UIManager.put("JYDocking.titlebar.floatButton.selected.toolTip", "还原");
            UIManager.put("JYDocking.titlebar.maximizeButton.selected.toolTip", "还原");
            // 设置文本输入框字体
            UIManager.put("TextField.font", new Font("新宋体", Font.PLAIN,17));
            // 自定义参数值
            // 如果要设置其它值 例 synth.xml 文件中  <defaultsProperty key="Synthetica.toolBar.background"> key就是 UIManager.put() 的key
            // UIManager.put("Synthetica.toolBar.background","plain/images/internalFrameTitlePaneBackground_selected.png");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.err.println("窗体样式风格初始异常");
            e.printStackTrace();
        }
    }
}
