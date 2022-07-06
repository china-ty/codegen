package com.ty.codegen.event;

import com.ty.codegen.entity.TableField;
import com.ty.codegen.service.TableService;
import com.ty.codegen.service.impl.MysqlTableServiceImpl;
import com.ty.codegen.util.IconUtil;
import com.ty.codegen.util.MenuUtil;
import com.ty.codegen.util.TableModelUtil;
import com.ty.codegen.win.CodePreviewWin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 主窗体 左部 树型表 鼠标 事件 监听 适配器
 */
public class TableTreeMouseEventAdapter extends MouseAdapter {

    private TableService tableService = new MysqlTableServiceImpl();

    private JTable tableField;

    public TableTreeMouseEventAdapter(JTable tableField) {
        this.tableField = tableField;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int clickCount = e.getClickCount();
        // 点击的数量等于2表示双击
        if (clickCount != 2) {
            return;
        }
        JTree tableTrees = (JTree) e.getComponent();
        DefaultTableModel tableModel = TableModelUtil.getDefaultTableModel();
        // 获取选中的节点的名字
        String selectNodeName = tableTrees.getSelectionPath().getLastPathComponent().toString();
        try {
            List<TableField> tableFields = tableService.getTableFields(selectNodeName);
            for (TableField tableField : tableFields) {
                Object[] dataArray = new Object[7];
                dataArray[0] = tableField.getName();
                dataArray[1] = tableField.getDbType();
                dataArray[2] = tableField.getEntityType();
                dataArray[3] = tableField.getLength();
                dataArray[4] = tableField.getDecimal();
                dataArray[5] = tableField.getRequired();
                dataArray[6] = tableField.getRemarks();
                tableModel.addRow(dataArray);
            }
            tableField.setModel(tableModel);
            // 设置下标第5列使用Boolean(复选框) 注意 这一列传入的类型只能是Boolean
            TableColumn column = tableField.getColumnModel().getColumn(5);
            column.setCellEditor(tableField.getDefaultEditor(Boolean.class));
            column.setCellRenderer(tableField.getDefaultRenderer(Boolean.class));
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "获取数据异常!", "提示", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 鼠标按下事件
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        JTree tableTrees = (JTree) e.getComponent();
        TreePath selectionPath = tableTrees.getSelectionPath();
        // 未选中节点
        if (selectionPath == null) {
            return;
        }
        int selectionCount = tableTrees.getSelectionCount();
        // 选择多个返回
        if (selectionCount > 1) {
            return;
        }
        String selectNodeName = selectionPath.getLastPathComponent().toString();
        JMenuItem previewMenItem = new JMenuItem();
        previewMenItem.setIcon(IconUtil.PREVIEW);
        previewMenItem.setText("代码预览");
        previewMenItem.addActionListener(item -> {
            Map<String, CodePreviewWin> codePreviewWinCacheMap = CodePreviewWin.getCacheCodePreviewWin();
            CodePreviewWin codePreviewWinCache = codePreviewWinCacheMap.get(selectNodeName);
            if (codePreviewWinCache == null) {
                CodePreviewWin codePreviewWin = new CodePreviewWin(selectNodeName);
                codePreviewWin.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        codePreviewWinCacheMap.remove(selectNodeName);
                    }
                });
            } else {
                // 将窗体设置在最前面(设置为活动窗口)
                codePreviewWinCache.toFront();
            }

        });
        List<JMenuItem> menuItemList = Arrays.asList(previewMenItem);
        MenuUtil.createMouseRightShortcutMenuButton(e,tableTrees,menuItemList);
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
