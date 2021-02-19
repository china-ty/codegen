package com.ty.codegen.event;

import com.ty.codegen.util.MenuUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @Project: codegen
 * @ClassName: TableFieldScrollPaneMouseEventAdapter
 * @Author: ty
 * @Description: 表字段面板鼠标事件
 * @Date: 2021/2/3
 * @Version: 1.0
 **/
public class TableFieldScrollPaneMouseEventAdapter extends MouseAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
        Component component = e.getComponent();
        List<JMenuItem> menuItemList = MenuUtil.createDefaultMouseRightShortcutMenuButton(e, component);
        // 添加事件
        for (int i = 0; i < menuItemList.size(); i++) {
            // 添加的对象生命周期是跟方法的,不用担心重复添加相同
            menuItemList.get(i).addActionListener(item -> {
                System.out.println("执行了鼠标右键的快捷菜单按钮-TableFieldScrollPaneMouseEventAdapter");
            });
        }
    }
}
