package com.ty.codegen.event;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;
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
