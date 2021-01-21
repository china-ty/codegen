package com.ty.codegen.event;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 主窗体 左部 树型表 鼠标 事件 监听 适配器
 */
public class TableTreeMouseEventAdapter extends MouseAdapter {

    private JTree tableTrees;

    private DefaultTableModel tableModel;

    public TableTreeMouseEventAdapter(JTree jTree, DefaultTableModel tableModel) {
        this.tableTrees = jTree;
        this.tableModel = tableModel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int clickCount = e.getClickCount();
        // 点击的数量等于2表示双击
        if (clickCount == 2) {
            this.clear(tableModel);
            // 获取选中的节点的名字
            String selectNodeName = tableTrees.getSelectionPath().getLastPathComponent().toString();
            System.out.println("当前选中的表是: " + selectNodeName);



            if ("bee_add_service".equalsIgnoreCase(selectNodeName)) {
                // 设置行数据
                Object[] data1 = {"Person1", 80, 80, 80, 240, false, 99};
                Object[] data2 = {"Person2", 80, 80, 80, 240, false, 99};
                Object[] data3 = {"Person3", 80, 80, 80, 240, true, 99};
                tableModel.addRow(data1);
                tableModel.addRow(data2);
                tableModel.addRow(data3);
            }
            if ("bee_commission_log".equalsIgnoreCase(selectNodeName)) {
                // 设置行数据
                Object[] data = {"Student", 80, 80, 80, 240, true, 99};
                tableModel.addRow(data);
            }
        }
    }

    private void clear(DefaultTableModel tableModel) {
        // 获取table中的所有数据
//        for (int i = 0; i < tableModel.getRowCount(); i++) {
//            for (int j = 0; j < tableModel.getColumnCount(); j++) {
//                System.out.print(tableModel.getValueAt(i, j) +"--");
//            }
//            System.out.println();
//        }
        // int i = 0; i <= tableModel.getRowCount() ; i++ 错误逻辑 因为
        // 当每 tableModel.removeRow(i);一次tableModel.getRowCount() 是会变的
        // 正确是获取总数减一(最大下标) 一直到最小下标0
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            //System.out.println(i);
            tableModel.removeRow(i);
        }
    }
}
