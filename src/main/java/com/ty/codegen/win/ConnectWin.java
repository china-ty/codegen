package com.ty.codegen.win;

import com.ty.codegen.enums.DatabaseTypeEnum;
import com.ty.codegen.enums.TextFieldTypeEnum;
import com.ty.codegen.event.TextFieldKeyAdapter;

import javax.swing.*;
import java.awt.*;

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

    public static ConnectWin instance(){
        if ( ConnectWin.connectWin != null ) {
            ConnectWin.connectWin.setVisible(true);
        } else {
            ConnectWin connectWin = new ConnectWin();
            ConnectWin.connectWin =  connectWin;
            ConnectWin.connectWin.setVisible(true);
        }
        return ConnectWin.connectWin;
    }

    private ConnectWin()  {
        // 初始化创建新连接窗体
        this.init();
        // 初始化组件
        this.setupComponent();
        // 显示窗体
        this.setVisible(true);
    }

    private void setupComponent() {
        // 设置布局为空(绝对布局)
        this.setLayout(null);
        // 获取窗体的面板
        Container contentPane = this.getContentPane();
        // 标签的X坐标
        int labelX = 35;
        // 标签和输入框的Y坐标
        int Y = 30;
        // 标签的宽度
        int labelWidth = 70;
        // 标签的高度
        int labelHeight = 25;
        // 与上一行直接的间距
        int spacing = 10;
        // 输入框的X坐标
        int textX = 120;
        // 输入框的宽度
        int textWidth = 130;
        // 输入框的高度
        int textHeight = labelHeight;
        JLabel databaseLabel = new JLabel("数据库:");
        databaseLabel.setBounds(labelX,Y,labelWidth,labelHeight);
        // 获取系统支持的所有数据库集合
        String[] databaseList = DatabaseTypeEnum.toStringArray();
        // 创建一个下拉列表框
        JComboBox<String> databaseComboBox = new JComboBox<String>(databaseList);
        databaseComboBox.setBounds(textX,Y,textWidth,textHeight);
        // 默认选中下标为0的项
        databaseComboBox.setSelectedItem(0);
        // 计算下一行的Y坐标
        Y = Y + labelHeight + spacing;
        // 连接名
        JLabel linkNameLabel = new JLabel("连接名:");
        linkNameLabel.setBounds(labelX,Y,labelWidth,labelHeight);
        JTextField linkNameText = new JTextField();
        linkNameText.setBounds(textX,Y,textWidth,textHeight);
        linkNameText.addKeyListener(new TextFieldKeyAdapter(5));
        // 计算下一行的Y坐标
        Y = Y + labelHeight + spacing;
        // 主机
        JLabel hostAddressLabel = new JLabel("主机:");
        hostAddressLabel.setBounds(labelX,Y,labelWidth,labelHeight);
        JTextField hostAddressText = new JTextField();
        hostAddressText.setBounds(textX,Y,textWidth,textHeight);
        hostAddressText.addKeyListener(new TextFieldKeyAdapter(15));

        Y = Y + labelHeight + spacing;
        // 端口号
        JLabel portLabel = new JLabel("端口号:");
        portLabel.setBounds(labelX,Y,labelWidth,labelHeight);
        JTextField portText = new JTextField("3306");
        portText.setBounds(textX,Y,textWidth,textHeight);
        portText.addKeyListener(new TextFieldKeyAdapter(TextFieldTypeEnum.NUMBER,8));

        Y = Y + labelHeight + spacing;
        // 用户名
        JLabel userNameLabel = new JLabel("用户名:");
        userNameLabel.setBounds(labelX,Y,labelWidth,labelHeight);
        JTextField userNameText = new JTextField();
        userNameText.setBounds(textX,Y,textWidth,textHeight);
        userNameText.addKeyListener(new TextFieldKeyAdapter(20));

        Y = Y + labelHeight + spacing;
        // 密码
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(labelX,Y,labelWidth,labelHeight);
        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(textX,Y,textWidth,textHeight);
        userNameText.addKeyListener(new TextFieldKeyAdapter(20));

        // 将这些组件添加到窗体面板中
        contentPane.add(databaseLabel);
        contentPane.add(databaseComboBox);

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

    private void init() {
        this.setTitle("新建连接");
        this.setBounds(300,100,300,400);
        // 禁止调整窗体大小
        this.setResizable(false);
    }
}
