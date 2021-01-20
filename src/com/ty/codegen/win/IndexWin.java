package com.ty.codegen.win;

import com.ty.codegen.event.TableModelEventAdapter;
import com.ty.codegen.event.TableTreeMouseEventAdapter;
import com.ty.codegen.proxy.ServiceProxy;
import com.ty.codegen.service.TableService;
import com.ty.codegen.service.impl.TableServiceImpl;
import com.ty.codegen.util.MysqlDBUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        indexContainer.add(createTopContentSplitPane(), BorderLayout.CENTER);
        indexContainer.add(createButtons(), BorderLayout.SOUTH);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("程序关闭中......");
                System.out.println("关闭相关数据库连接中......");
                MysqlDBUtil.closeAll();
                System.out.println("程序关闭成功");

            }

        });
        this.addComponentListener(new ComponentAdapter(){
            @Override public void componentResized(ComponentEvent e){
                // write you code here
            }});
        // 显示窗体
        this.setVisible(true);
    }

    /**
     * 创建顶部分割面板(左 表名的展示 右 表的字段展示)
     * @return
     */
    private JSplitPane createTopContentSplitPane() {
        // 创建一个分割容器面板(左右布局)
        JSplitPane contentSplitPane = new JSplitPane();
        // 让分割线禁用出箭头
        contentSplitPane.setOneTouchExpandable(false);
        // 操作分割箭头,重绘图形(这样流畅一些)
        contentSplitPane.setContinuousLayout(true);
        // 设置分割线的粗细
        contentSplitPane.setDividerSize(2);
        // 设置分割线方向 左右分布(默认)  JSplitPane.VERTICAL_SPLIT:上下分布
        contentSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        // 设置分割线位于窗体的位置
        contentSplitPane.setDividerLocation(170);
        // 设置左边的组件(数据库名和所有表名)
        contentSplitPane.setLeftComponent(createLeftTableNames(this.getY()));
        // 设置右边的组件(表的字段信息)
        contentSplitPane.setRightComponent(createPane());
        return contentSplitPane;
    }

    private JScrollPane createPane() {
        JScrollPane tableFieldScrollPane = new JScrollPane();
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
        // 监听数据表格中数据是否改变事件
        table.addMouseListener(new TableModelEventAdapter(tableModel));
        tableFieldScrollPane.setViewportView(table);
        return tableFieldScrollPane;
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
     *
     * @param height 设置面板高度
     * @return JScrollPane
     */
    private JScrollPane createLeftTableNames(final int height) {
        // 添加滚动面板  默认 水平滚动和垂直滚动 都是需要时才显示的
        JScrollPane tablesScrollPanel = new JScrollPane();
        // 取消水平滚动显示
        tablesScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //tablesPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        // 设置滚动面板的宽度
        final int tablesPanelWidth = 170;
        // 设置滚动面板的大小
        tablesScrollPanel.setPreferredSize(new Dimension(tablesPanelWidth, height));
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
        // 创建数据库根节点
        JTree tableTrees = new JTree(rootNode);
        // tableTrees.setEnabled(false);
        // 设置对数据表节点的相关鼠标的监听(查询表相关的字段属性等功能)
        tableTrees.addMouseListener(new TableTreeMouseEventAdapter(tableTrees, tableModel));
        // 设置图标
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer(){
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                          boolean sel, boolean expanded, boolean leaf, int row,
                                                          boolean hasFocus) {
                tree.setToolTipText("这是工具提示: " + value);
                return super.getTreeCellRendererComponent(tree, value, sel,
                        expanded, leaf, row, hasFocus);
            }
        };
        // 展开时节点图标
        renderer.setOpenIcon(new ImageIcon("src/com/ty/codegen/img/mysql.png"));
        // 折叠时节点图标
        renderer.setClosedIcon(new ImageIcon("src/com/ty/codegen/img/mysql.png"));
        // 叶子节点图标
        renderer.setLeafIcon(new ImageIcon("src/com/ty/codegen/img/table.png"));
        // 添加到表节点中
        tableTrees.setCellRenderer(renderer);
        // 设置子节点与子节点直接的高度(间隙)
        tableTrees.setRowHeight(22);
        // setViewportView设置滚动显示视图内容组件 这里不能直接使用add
        tablesScrollPanel.setViewportView(tableTrees);
        return tablesScrollPanel;
    }


}
