package com.ty.codegen.win;

import com.ty.codegen.event.TableModelEventAdapter;
import com.ty.codegen.event.TableTreeMouseEventAdapter;
import com.ty.codegen.proxy.ServiceProxy;
import com.ty.codegen.service.TableService;
import com.ty.codegen.service.impl.TableServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.List;

/**
 * 主界面窗体
 */
public class IndexWin extends JFrame {

    private DefaultTableModel tableModel = new DefaultTableModel() {
        // 禁用数据表格 当返回false为禁用true为可编辑
        @Override
        public boolean isCellEditable(int row, int column) {
            // 禁用下标为0的第一列
            if (column == 0) {
                return false;
            }
            return true;
        }
    };
    private TableService tableService = ServiceProxy.proxyPowerful(new TableServiceImpl());

    public IndexWin() throws Exception {
        // 主窗体组件和数据初始化
        init();
    }

    /**
     * 初始化设置
     */
    private void init() throws Exception {
        // 设置窗体标题
        this.setTitle("代码生成器");
        // this.setLayout(new BorderLayout(0,0));
        // 设置窗体大小和位置
        this.setBounds(400, 150, 600, 500);
        // 当关闭窗体时同时关闭程序和进程
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 获取JFrame的容器
        Container indexContainer = this.getContentPane();
        //indexContainer.setBackground(Color.RED);
        // 添加面板到主面板的西部(左边)
        indexContainer.add(createLeftTableNames(this.getY()), BorderLayout.WEST);
        // 将面板设置在中部 该窗体不需要东部(右边)
        indexContainer.add(createPane(), BorderLayout.CENTER);
        indexContainer.add(createButtons(), BorderLayout.SOUTH);
        // 显示窗体
        this.setVisible(true);
    }

    private JScrollPane createPane() throws Exception {
        tableService.getTableFields(null);
        JScrollPane jScrollPane = new JScrollPane();
        // 表头（列名）
        Object[] columnNames = {"字段名", "db类型", "Java类型", "长度", "小数点", "是否必填", "注释"};
        // 设置头标题
        tableModel.setDataVector(null, columnNames);
        // tableModel.setDataVector(rowData,columnNames);
        // 创建一个表格，指定 所有行数据 和 表头
        JTable table = new JTable(tableModel);
        // 设置下标第5列使用Boolean(复选框) 注意 这一列传入的类型只能是Boolean
        TableColumn column = table.getColumnModel().getColumn(5);
        column.setCellEditor(table.getDefaultEditor(Boolean.class));
        column.setCellRenderer(table.getDefaultRenderer(Boolean.class));
        // 监听数据表格中数据是否改变
//        table.getModel().addTableModelListener(new TableModelListener(){
//            @Override
//            public void tableChanged(TableModelEvent e) {
//                if(e.getType() == TableModelEvent.UPDATE){
//                    //业务逻辑
//                    if(e.getColumn() == 1){
//                        System.out.println("==>"+e.getFirstRow()+"-->"+e.getLastRow());
//                        String newvalue = table.getValueAt(e.getLastRow(),e.getColumn()).toString();
//                       // if(!editValue.equals(newvalue.trim())){
//                            System.out.println(newvalue);
//                        //}
//                    }
//                }
//
//            }
//        });
        table.addMouseListener(new TableModelEventAdapter(tableModel));
        jScrollPane.setViewportView(table);
        return jScrollPane;
    }

    private JPanel createButtons() {
        JPanel jPanel = new JPanel();
        JButton ok = new JButton("生成");
        jPanel.add(ok);
        return jPanel;
    }

    /**
     * 创建左部数据库和所有表名树形节点
     *
     * @param height 设置面板高度
     * @return JScrollPane
     */
    private JScrollPane createLeftTableNames(final int height) {
        // 添加滚动面板  默认 水平滚动和垂直滚动 都是需要时才显示的
        JScrollPane tablesPanel = new JScrollPane();
        // 取消水平滚动显示
        // tablesPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // 设置滚动面板的宽度
        final int tablesPanelWidth = 150;
        // 设置滚动面板的大小
        tablesPanel.setPreferredSize(new Dimension(tablesPanelWidth, height));
        // 获取数据库名
        String databaseName = tableService.databaseName();
        // 设置根节点(数据库名)
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(databaseName);
        // 获取所有表名
        List<String> tableList = tableService.listTableNames();
        DefaultMutableTreeNode tableNode = null;
        // 遍历所有表 添加到数据库中(主节点rootNode)
        for (int i = 0; i < tableList.size(); i++) {
            // 参数二 表示不能有子节点
            tableNode = new DefaultMutableTreeNode(tableList.get(i), false);
            rootNode.add(tableNode);
        }
        JTree tableTrees = new JTree(rootNode);
        //tableTrees.setEnabled(false);
        tableTrees.addMouseListener(new TableTreeMouseEventAdapter(tableTrees, tableModel));
        // 设置图标
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setOpenIcon(new ImageIcon("src/com/ty/codegen/img/mysql.png"));    //展开时节点图标
        renderer.setClosedIcon(new ImageIcon("src/com/ty/codegen/img/mysql.png"));  //折叠时节点图标
        renderer.setLeafIcon(new ImageIcon("src/com/ty/codegen/img/table.png"));   //叶子节点图标
        tableTrees.setCellRenderer(renderer);
        // 设置子节点与子节点直接的高度(间隙)
        tableTrees.setRowHeight(22);
        // setViewportView设置滚动显示视图内容组件 这里不能直接使用add
        tablesPanel.setViewportView(tableTrees);
        return tablesPanel;
    }


}
