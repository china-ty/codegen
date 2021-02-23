package com.ty.codegen;

import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.JRibbonFrame;
import org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.IconRibbonBandResizePolicy;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * @Project: codegen
 * @ClassName: MainFrame
 * @Author: ty
 * @Description:
 * @Date: 2021/2/23 00023 11:11
 * @Version: 1.0
 **/
public class MainFrame extends JRibbonFrame {
    static {  //*****
        // 最原始
        //https://blog.csdn.net/Rong_Toa/article/details/77885966?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_title-0&spm=1001.2101.3001.4242
        // 设置界面风格：获取系统样式
        try {
//            javax.swing.UIManager.setLookAndFeel(
//                    "org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel");
//            WebLookAndFeel.install(FlatSkin.class );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //根据图片的地址获取该图片，返回ResizableIcon
    public static ResizableIcon getResizableIconFromResource(String resource) {
        return ImageWrapperResizableIcon.getIcon(
                MainFrame.class.getClassLoader().getResource(resource),
                new Dimension(508, 548));
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            @Override
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.setSize(500,500);
                frame.setVisible(true);
                JRibbonBand band1 = new JRibbonBand("Hello", null); //新建一个Band
                band1.setResizePolicies( Arrays.asList(new CoreRibbonResizePolicies.None(band1.getControlPanel()), new IconRibbonBandResizePolicy(band1.getControlPanel())));
                JCommandButton button1 =
                        new JCommandButton("Square",
                                getResizableIconFromResource("picture/table.png"));
                JCommandButton button2 =
                        new JCommandButton("Circle",
                                getResizableIconFromResource("picture/table.png"));
                JCommandButton button3 =
                        new JCommandButton("Triangle",
                                getResizableIconFromResource("picture/table.png"));
                JCommandButton button4 =
                        new JCommandButton("Star",
                                getResizableIconFromResource("picture/table.png"));
                band1.addCommandButton(button1, RibbonElementPriority.TOP);
                band1.addCommandButton(button2, RibbonElementPriority.MEDIUM);
                band1.addCommandButton(button3, RibbonElementPriority.MEDIUM);
                band1.addCommandButton(button4, RibbonElementPriority.MEDIUM);
                //新建一个Task，并将Band添加到该Task中去
                RibbonTask task1 = new RibbonTask("One", band1);
                //先获取Ribbon，然后在Ribbon上添加一个Task
                frame.getRibbon().addTask(task1);
            }
        });
    }
}