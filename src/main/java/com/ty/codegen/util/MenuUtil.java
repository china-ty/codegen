package com.ty.codegen.util;

import cn.hutool.core.collection.CollectionUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

/**
 * @Project: codegen
 * @ClassName: MenuUtil
 * @Author: ty
 * @Description: 窗体组件菜单生成工具类
 * @Date: 2021/2/3
 * @Version: 1.0
 **/
public class MenuUtil {
    private MenuUtil(){}

    /**
     * 创建默认鼠标右键快捷菜单
     * @param mouseEvent 鼠标事件
     * @param component 目标组件
     */
    public static List<JMenuItem> createDefaultMouseRightShortcutMenuButton(MouseEvent mouseEvent, Component component){
        JMenuItem refreshMenItem = new JMenuItem();
        // TODO 更改刷新图标
        refreshMenItem.setIcon(IconUtil.TABLE);
        refreshMenItem.setFont(new Font("新宋体", Font.PLAIN, 10));
        refreshMenItem.setText("刷新");
        List<JMenuItem> menuItemList = Arrays.asList(refreshMenItem);
        createMouseRightShortcutMenuButton(mouseEvent,component,menuItemList);
        return menuItemList;
    }

    /**
     * 创建鼠标右键快捷菜单
     * @param mouseEvent 鼠标事件
     * @param component 目标组件
     * @param menuItemList 菜单列表选项
     */
    public static void createMouseRightShortcutMenuButton(MouseEvent mouseEvent, Component component, List<JMenuItem> menuItemList){
        // 判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
        if (mouseEvent.getButton() != MouseEvent.BUTTON3) {
            return;
        }
        if (CollectionUtil.isEmpty(menuItemList)) {
            return;
        }
        JPopupMenu popupMenu = new JPopupMenu();
        for (int i = 0; i < menuItemList.size(); i++) {
            popupMenu.add(menuItemList.get(i));
        }
        // 弹出菜单
        popupMenu.show(component, mouseEvent.getX(), mouseEvent.getY());
    }
}