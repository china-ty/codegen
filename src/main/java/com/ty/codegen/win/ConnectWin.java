package com.ty.codegen.win;

import javax.swing.*;
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
public class ConnectWin extends JFrame {
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

    public ConnectWin()  {
        init();
        this.setVisible(true);
    }

    private void init() {
        this.setTitle("新建连接");
        this.setBounds(300,100,450,600);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                e.getWindow().dispose();
            }
        });
    }
}
