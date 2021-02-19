package com.ty.codegen.event;

import com.ty.codegen.util.MenuUtil;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TableFieldEventAdapter extends MouseAdapter {

    private TableModel tableModel;

    public TableFieldEventAdapter(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    /**
     * 鼠标选中事件
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("我被选中了");
        // 获得源对象
        JTable table = (JTable) e.getSource();
        ListSelectionModel selectionModel = table.getSelectionModel();
        if (selectionModel.isSelectionEmpty()) {
            JOptionPane.showMessageDialog(null, "未进行任何修改！");
            return;
            //System.out.println("我修改了");
        }
        //得到选中的行列的索引值
        int rowIndex = table.getSelectedRow();
        int columnIndex = table.getSelectedColumn();
        //得到选中的单元格的值，表格中都是字符串
        Object value = table.getValueAt(rowIndex, columnIndex);
        String info = rowIndex + "行" + columnIndex + "列值 : " + value.toString();
        System.out.println("info = " + info);

    }
    @Override
    public void mousePressed(MouseEvent e) {
        JTable table = (JTable) e.getComponent();
        // 鼠标右键快捷菜单
        // 添加的对象生命周期是跟方法的,不用担心重复添加相同
        List<JMenuItem> menuItemList = MenuUtil.createDefaultMouseRightShortcutMenuButton(e, table);
        // 添加事件
        for (int i = 0; i < menuItemList.size(); i++) {
            // 添加的对象生命周期是跟方法的,不用担心重复添加相同
            menuItemList.get(i).addActionListener(item -> {
                System.out.println("执行了鼠标右键的快捷菜单按钮-TableModelEventAdapter");
            });
        }
    }
    //@Override
    public void tableChanged(TableModelEvent e) {
        if (TableModelEvent.UPDATE == e.getType()) {
            System.out.println(e.getColumn());
            System.out.println(e.getLastRow());
            System.out.println(e.getFirstRow());
            System.out.println("数据改变了");
        }
    }
}
