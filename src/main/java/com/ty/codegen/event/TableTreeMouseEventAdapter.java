package com.ty.codegen.event;

import com.ty.codegen.entity.TableField;
import com.ty.codegen.service.TableService;
import com.ty.codegen.service.impl.MysqlTableServiceImpl;
import com.ty.codegen.util.IconUtil;
import com.ty.codegen.util.MenuUtil;
import com.ty.codegen.win.CodePreviewWin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    private DefaultTableModel tableModel;

    public TableTreeMouseEventAdapter(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int clickCount = e.getClickCount();
        // 点击的数量等于2表示双击
        if (clickCount == 2) {
            JTree tableTrees = (JTree) e.getComponent();
            this.clear(tableModel);
            // 获取选中的节点的名字
            String selectNodeName = tableTrees.getSelectionPath().getLastPathComponent().toString();
            try {
                List<TableField> tableFields = tableService.getTableFields(selectNodeName);
                for (TableField tableField : tableFields) {
                    Object[] objects = new Object[7];
                    objects[0] = tableField.getName();
                    objects[1] = tableField.getDbType();
                    objects[2] = tableField.getEntityType();
                    objects[3] = tableField.getLength();
                    objects[4] = tableField.getDecimal();
                    objects[5] = tableField.getRequired();
                    objects[6] = tableField.getRemarks();
                    tableModel.addRow(objects);
                }
            } catch (Exception exception) {
                System.out.println("数据库异常");
                JOptionPane.showMessageDialog(null, "数据库链接错误!", "提示",JOptionPane.ERROR_MESSAGE);
                // exception.printStackTrace();
            }
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
