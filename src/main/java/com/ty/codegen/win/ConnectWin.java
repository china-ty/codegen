package com.ty.codegen.win;

import com.ty.codegen.enums.TextFieldTypeEnum;
import com.ty.codegen.event.TextFieldKeyAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Project: codegen
 * @ClassName: ConnectWin
 * @Author: ty
 * @Description: 配置连接数据库窗体
 * @Date: 2021/2/3
 * @Version: 1.0
 **/
public class ConnectWin extends JDialog {
    private static ConnectWin connectWin;
    // 主程序的位置坐标
    private static Point indexWinPoint;
    // 主程序的屏幕大小
    private static Dimension indexWinDimension;
    // 当前数据库类型
    private static String databaseType;

    /**
     * 创建新建连接窗体
     *
     * @param indexWinDimension
     * @return
     */
    public static ConnectWin instance(String databaseType,Point indexWinPoint, Dimension indexWinDimension) {
        if (ConnectWin.connectWin == null) {
            ConnectWin.indexWinDimension = indexWinDimension;
            ConnectWin.indexWinPoint = indexWinPoint;
            ConnectWin.databaseType = databaseType;
            ConnectWin.connectWin = new ConnectWin(databaseType);
            return ConnectWin.connectWin;
        } else {
            return ConnectWin.connectWin;
        }

    }

    private ConnectWin(String databaseType) {
        // 初始化创建新连接窗体
        this.setTitle(databaseType + " - 新建连接");
        this.init();
        // 初始化组件
        this.setupComponent();
        // 显示窗体
        this.setVisible(true);
    }

    private void init() {
        // 获取主程序坐标
        int x = (int) ConnectWin.indexWinPoint.getX();
        int y = (int) ConnectWin.indexWinPoint.getY();
        // 获取主程序大小
        int width = ConnectWin.indexWinDimension.width;
        int height = ConnectWin.indexWinDimension.height;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 清空窗体所有数据
                ConnectWin.connectWin = null;
            }
        });
        // 设置窗体大小
        this.setSize(300, 400);
        // 设置窗体位置
        this.setLocation((x + (width / 2)) - (this.getWidth() / 2), (y + (height / 2)) - (this.getHeight() / 2));
        // 禁止调整窗体大小
        this.setResizable(false);
    }

    private void setupComponent() {
        // 设置布局为空(绝对布局)
        this.setLayout(null);
        // 获取窗体的面板
        Container contentPane = this.getContentPane();
        // 标签的X坐标
        int labelX = 15;
        // 标签和输入框的Y坐标
        int Y = 30;
        // 标签的宽度
        int labelWidth = 55;
        // 标签的高度
        int labelHeight = 35;
        // 与上一行直接的间距
        int spacing = 10;
        // 输入框的X坐标
        int textX = 105;
        // 输入框的宽度
        int textWidth = 175;
        // 输入框的高度
        int textHeight = labelHeight;

        // 连接名
        JLabel linkNameLabel = new JLabel("连接名:");
        linkNameLabel.setBounds(labelX, Y, labelWidth, labelHeight);
        JTextField linkNameText = new JTextField();
        linkNameText.setBounds(textX, Y, textWidth, textHeight);
        linkNameText.addKeyListener(new TextFieldKeyAdapter(25));
        // 计算下一行的Y坐标
        Y = Y + labelHeight + spacing;
        // 主机
        JLabel hostAddressLabel = new JLabel("主机:");
        hostAddressLabel.setBounds(labelX, Y, labelWidth, labelHeight);
        JTextField hostAddressText = new JTextField();
        hostAddressText.setBounds(textX, Y, textWidth, textHeight);
        hostAddressText.addKeyListener(new TextFieldKeyAdapter(15));

        Y = Y + labelHeight + spacing;
        // 端口号
        JLabel portLabel = new JLabel("端口号:");
        portLabel.setBounds(labelX, Y, labelWidth, labelHeight);
        JTextField portText = new JTextField("3306");
        portText.setBounds(textX, Y, textWidth, textHeight);
        portText.addKeyListener(new TextFieldKeyAdapter(TextFieldTypeEnum.NUMBER, 8));

        Y = Y + labelHeight + spacing;
        // 用户名
        JLabel userNameLabel = new JLabel("用户名:");
        userNameLabel.setBounds(labelX, Y, labelWidth, labelHeight);
        JTextField userNameText = new JTextField();
        userNameText.setBounds(textX, Y, textWidth, textHeight);
        userNameText.addKeyListener(new TextFieldKeyAdapter(20));

        Y = Y + labelHeight + spacing;
        // 密码
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(labelX, Y, labelWidth, labelHeight);
        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(textX, Y, textWidth, textHeight);
        userNameText.addKeyListener(new TextFieldKeyAdapter(20));

        // 将这些组件添加到窗体面板中
        contentPane.add(linkNameLabel);
        contentPane.add(linkNameText);

        contentPane.add(hostAddressLabel);
        contentPane.add(hostAddressText);

        contentPane.add(portLabel);
        contentPane.add(portText);

        contentPane.add(userNameLabel);
        contentPane.add(userNameText);

        contentPane.add(passwordLabel);
        contentPane.add(passwordText);
    }
}
