package com.ty.codegen.win;

import javax.swing.*;
import java.awt.*;

/**
 * @Project: codegen
 * @ClassName: ConfigWin
 * @Author: ty
 * @Description: 配置窗体
 * @Date: 2021/1/21
 * @Version: 1.0
 **/
public class ConfigWin extends JFrame {
    public ConfigWin()  {
        // 主窗体组件和数据初始化
        this.init();
    }

    private void init() {
        // 设置窗体标题
        this.setTitle("配置");
        // this.setLayout(new BorderLayout(0,0));
        // 设置窗体大小和位置
        this.setBounds(500, 300, 600, 500);
        // 获取JFrame的容器
        Container indexContainer = this.getContentPane();
        // 显示窗体
        this.setVisible(true);
    }
}
