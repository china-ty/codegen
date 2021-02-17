package com.ty.codegen.event;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableModelEventAdapter extends MouseAdapter {

    private TableModel tableModel;

    public TableModelEventAdapter(TableModel tableModel) {
        this.tableModel = tableModel;
    }

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
        mouseRightButtonClick(e);
    }

    //鼠标右键点击事件
    private void mouseRightButtonClick(MouseEvent evt) {
        //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            JTable jTable1 = (JTable) evt.getComponent();
            //通过点击位置找到点击为表格中的行
            int focusedRowIndex = jTable1.rowAtPoint(evt.getPoint());
            if (focusedRowIndex == -1) {
                return;
            }
            //将表格所选项设为当前右键点击的行
            jTable1.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
            JPopupMenu m_popupMenu = new JPopupMenu();
            JMenuItem delMenItem = new JMenuItem();
            delMenItem.setFont(new Font("新宋体", Font.PLAIN, 10));
            delMenItem.setText("刷新");
            delMenItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    //该操作需要做的事
                }
            });
            m_popupMenu.add(delMenItem);
            //弹出菜单
            m_popupMenu.show(jTable1, evt.getX(), evt.getY());
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
