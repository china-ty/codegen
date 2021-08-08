package com.ty.codegen.win;

import com.ty.codegen.enums.DatabaseTypeEnum;
import com.ty.codegen.event.TableFieldEventAdapter;
import com.ty.codegen.event.TableFieldScrollPaneMouseEventAdapter;
import com.ty.codegen.event.TableTreeMouseEventAdapter;
import com.ty.codegen.proxy.ServiceProxy;
import com.ty.codegen.service.TableService;
import com.ty.codegen.service.impl.MysqlTableServiceImpl;
import com.ty.codegen.util.IconUtil;
import com.ty.codegen.util.MysqlDBUtil;
import de.javasoft.swing.JYDockingPort;
import de.javasoft.swing.JYDockingView;
import de.javasoft.swing.SimpleDropDownButton;
import de.javasoft.swing.plaf.jydocking.DefaultFloatAction;
import de.javasoft.swing.plaf.jydocking.DefaultMaximizeAction;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * 主界面窗体
 */
@Slf4j
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
    private TableService tableService;

    public IndexWin() throws Exception {
        // 主窗体组件和数据加载
        loading();
        tableService = ServiceProxy.proxyPowerful(new MysqlTableServiceImpl());
        // 窗体初始化组件
        init();
        // 显示窗体
        this.setVisible(true);
    }

    private void loading() {
        final SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
        }
        // 下面给splash screen画一个边框
        Rectangle bounds = splash.getBounds();
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            System.out.println("g is null");
            return;
        }
        // g.setColor(Color.BLUE);
        //g.drawRect(0, 0, bounds.width - 1, bounds.height - 1);
        // 加载进度提示信息
        final String[] stages = {"正在启动", "正在读取数据", "正在加载相关文档", "启动完成"};
        int stage = 0;
        for(int i = 0; i < 100; i++) {
            String status = stages[stage];
            if (splash != null) {
                // 更新闪屏图像(进度条)
                updateSplash(splash,g,status, i,bounds);
            }
            if (i == 30) {
                stage = 1;
            } else if (i == 70) {
                stage = 2;
            } else if (i == 90) {
                stage = 3;
            }
            try {
                //故意等待
                Thread.sleep(20);
            } catch (Exception e) {
                //异常不做处理
            }
        }
        splash.close();
    }
    protected void updateSplash(SplashScreen splash, Graphics2D g,String status, int progress,Rectangle bounds) {
        if (splash == null || g == null) {
            return;
        }
        //重画splash上面的进度并通知splash更新界面
        drawSplash(g, status, progress,bounds);
        splash.update();
    }
    protected void drawSplash(Graphics2D splashGraphics, String status, int progress,Rectangle bounds) {
        // 进度条长度
        int barWidth = bounds.width;
        splashGraphics.setComposite(AlphaComposite.Clear);
        // 闪屏边框
        splashGraphics.fillRect(1, 10, bounds.width - 2, 20);
        // 模型
        splashGraphics.setPaintMode();
        // 字符串颜色
        splashGraphics.setColor(Color.BLACK);
        // 画当前提示字符串
        splashGraphics.drawString(status, 10, 20);
        // 进度条填充颜色
        splashGraphics.setColor(Color.RED);
        // 进度条当前值
        int width = progress * barWidth / 100;
        // 绘画进度条
        splashGraphics.fillRect(0, bounds.height - 20, width, 15);
    }
    /**
     * 初始化设置
     */
    private void init() throws Exception {
        // 设置窗体标题
        this.setTitle("代码生成器");
        // 得到显示器屏幕的宽高
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        // 设置窗体大小和位置
        this.setSize(600,500);
        this.setLocation((width - this.getWidth()) / 2, (height - this.getHeight()) / 2);
        // 当关闭窗体时同时关闭程序和进程
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 添加菜单栏
        this.setJMenuBar(setupMenuBar());
        // 获取JFrame的容器
        Container indexContainer = this.getContentPane();
        // 设置导航栏(工具栏)
        indexContainer.add(navigationBarPane(),BorderLayout.NORTH);
        // 设置中部表和表字段等面板
        indexContainer.add(createCenterPane(), BorderLayout.CENTER);
        indexContainer.add(createButtons(), BorderLayout.SOUTH);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                log.info("程序关闭中......");
                log.info("关闭相关数据库连接中......");
                MysqlDBUtil.closeAll();
                log.info("程序关闭成功");

            }

        });
    }

    /**
     * 设置菜单栏
     * @return
     */
    public JMenuBar setupMenuBar() {
        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        // 创建菜单
        JMenu fileMenu = new JMenu("文件");

        // 创建文件菜单中的菜单项
        JMenuItem openMenuItem = new JMenuItem("打开");
        JMenuItem saveMenuItem = new JMenuItem("保存");
        JMenuItem exitMenuItem = new JMenuItem("退出");
        // 复选菜单
        JCheckBoxMenuItem checkBoxMenuItem1 = new JCheckBoxMenuItem("复选框子菜单案例1");
        JCheckBoxMenuItem checkBoxMenuItem2 = new JCheckBoxMenuItem("复选框子菜单案例2");
        // 单选菜单
        JRadioButtonMenuItem radioButtonMenuItem01 = new JRadioButtonMenuItem("单选按钮子菜单案例1");
        JRadioButtonMenuItem radioButtonMenuItem02 = new JRadioButtonMenuItem("单选按钮子菜单案例2");

        // 菜单项添加到菜单中
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);

        // 添加分割线(添加到哪里与代码顺序有关)
        fileMenu.addSeparator();

        fileMenu.add(checkBoxMenuItem1);
        fileMenu.add(checkBoxMenuItem2);

        // 添加分割线(添加到哪里与代码顺序有关)
        fileMenu.addSeparator();

        fileMenu.add(radioButtonMenuItem01);
        fileMenu.add(radioButtonMenuItem02);

        // 添加分割线(添加到哪里与代码顺序有关)
        fileMenu.addSeparator();

        fileMenu.add(exitMenuItem);

        // 将两个单选菜单进行绑定
        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(radioButtonMenuItem01);
        radioButtonGroup.add(radioButtonMenuItem02);

        // 将菜单添加到菜单栏中
        menuBar.add(fileMenu);
        return menuBar;
    }

    /**
     * 创建导航栏
     * @return
     */
    public JPanel navigationBarPane() {
        // 创建导航栏面板
        JPanel navigationBarPane = new JPanel();
        // 布局创建一行一列
        navigationBarPane.setLayout(new GridLayout(1,1));
        // 创建工具栏 这里的高就用按图片的大小来决定吧
        JToolBar navigationBar = new JToolBar();
        // 创建连接按钮
        SimpleDropDownButton disconnectedButton = new SimpleDropDownButton("连接");
        disconnectedButton.setIcon(IconUtil.DISCONNECTED);
        JPopupMenu disconnectedPopupMenu = disconnectedButton.getPopupMenu();
        // 获取系统支持的数据库类型
        DatabaseTypeEnum[] databaseTypeArray = DatabaseTypeEnum.values();
        for (int i = 0; i < databaseTypeArray.length; i++) {
            JMenuItem databaseType = new JMenuItem(databaseTypeArray[i].getType());
            // 添加单击监听事件 创建连接窗体
            databaseType.addActionListener(e -> {
                ConnectWin connectWin = ConnectWin.instance(((JMenuItem) e.getSource()).getText(), this.getLocation(), this.getSize());
                // 将窗体设置在最前面(设置为活动窗口)
                connectWin.toFront();
            });
            disconnectedPopupMenu.add(databaseType);
        }
        // 设置小的箭头
        disconnectedButton.setUseSmallArrowIcon(false);
        // 设置下拉监听与图片的距离
        disconnectedButton.setArrowIconSpace(8);
        // 设置文本在图片下方
        // 设置文本水平居中
        disconnectedButton.setHorizontalTextPosition(JButton.CENTER);
        // 设置文本垂直底部
        disconnectedButton.setVerticalTextPosition(JButton.BOTTOM);
        // 连接按钮添加到工具栏中
        navigationBar.add(disconnectedButton);
        // 垂直对齐
        navigationBar.setAlignmentX(0);
        // 添加导航栏面板中
        navigationBarPane.add(navigationBar);
        return navigationBarPane;
    }

    /**
     * 创建对接视图(左 表名的展示 右 表的字段展示)
     * @return {@link JPanel}
     */
    private JPanel createCenterPane() throws SQLException{
        // 设置左边的组件(数据库名和所有表名)
        JYDockingView tableDockingView = createLeftTableNames();
        // 设置右边的组件(表的字段信息)
        JYDockingView tableFieldDockingView = createRightTableFieldInfo();
        // 创建整体面板
        JPanel centerPanel = new JPanel(new BorderLayout(0,0));
        // 将tableDockingView和tableFieldDockingView进行整理合并
        JYDockingPort dockingPort = new JYDockingPort();
        dockingPort.dock(tableFieldDockingView);
        // 将tableDockingView设置在tableFieldDockingView的西部(左边)
        tableFieldDockingView.dock(tableDockingView, "WEST", 0.3F);
        // 设置居中布局
        centerPanel.add(dockingPort, BorderLayout.CENTER);
        return centerPanel;
    }

    /**
     * 创建右部表字段信息对接视图
     * @return {@link JYDockingView}
     */
    private JYDockingView createRightTableFieldInfo() {
        // 表头（列名）
        Object[] columnNames = {"字段名", "db类型", "Java类型", "长度", "小数点", "是否必填", "注释"};
        // 设置头标题
        tableModel.setDataVector(null, columnNames);
        // tableModel.setDataVector(rowData,columnNames);
        // 创建一个表格，指定 所有行数据 和 表头
        JTable tableField = new JTable(tableModel);
        // 设置所有行高
        tableField.setRowHeight(25);
        // 取消垂直线显示
        tableField.setShowVerticalLines(false);
        // 设置下标第5列使用Boolean(复选框) 注意 这一列传入的类型只能是Boolean
        TableColumn column = tableField.getColumnModel().getColumn(5);
        column.setCellEditor(tableField.getDefaultEditor(Boolean.class));
        column.setCellRenderer(tableField.getDefaultRenderer(Boolean.class));
        // 监听数据表格中数据是否改变事件
        tableField.addMouseListener(new TableFieldEventAdapter());
        // 创建对接视图
        JYDockingView tableFieldDockingView = new JYDockingView("tableFieldDockingView", "字段信息", "tableFieldDockingView");
        // 添加滚动面板
        JScrollPane tableFieldScrollPane = new JScrollPane();
        tableFieldScrollPane.setViewportView(tableField);
        // 添加表字段面板鼠标监听
        tableFieldScrollPane.addMouseListener(new TableFieldScrollPaneMouseEventAdapter());
        // 设置边界布局让里面的组件全屏
        tableFieldDockingView.setLayout(new BorderLayout());
        // 将滑动面板添加到表对接视图中
        tableFieldDockingView.add(tableFieldScrollPane);
        tableFieldDockingView.setTerritoryBlocked(BorderLayout.CENTER, true);
        // 添加对接视图放大功能
        tableFieldDockingView.addAction(new DefaultMaximizeAction(tableFieldDockingView));
        return tableFieldDockingView;
    }

    private JPanel createButtons() {
        JPanel jPanel = new JPanel();
        JButton configButton = new JButton("配置");

        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打开配置窗体
                new ConfigWin();
            }
        });
        JButton openGenFilePathButton = new JButton("打开生成路径");
        jPanel.add(configButton);
        jPanel.add(openGenFilePathButton);
        return jPanel;
    }

    /**
     * 创建左部数据库和所有表名对接视图
     * @return {@link JYDockingView}
     */
    private JYDockingView createLeftTableNames() throws SQLException {
        // 获取数据库名
        String databaseName = tableService.databaseName();
        // 设置根节点(数据库名)
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("tableTrees");
        DefaultMutableTreeNode databaseTreeNode = new DefaultMutableTreeNode(databaseName);
        DefaultMutableTreeNode mysql = new DefaultMutableTreeNode("我的配置");
        rootNode.add(databaseTreeNode);
        mysql.add(new DefaultMutableTreeNode("配置2", false));
        rootNode.add(mysql);
        // 获取所有表名
        Map<String,String> tableNameMap = tableService.listTableNames();
        DefaultMutableTreeNode tableNode = null;
        // 遍历所有表 添加到数据库中(主节点rootNode)
        Set<Map.Entry<String, String>> entries = tableNameMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            // 参数二 表示不能有子节点
            tableNode = new DefaultMutableTreeNode(entry.getKey(), false);
            databaseTreeNode.add(tableNode);
        }
        // 创建数据库根节点
        JTree tableTrees = new JTree(rootNode);
        tableTrees.setRootVisible(false);
        //tableTrees.setEnabled(false);
        // 设置对数据表节点的相关鼠标的监听(查询表相关的字段属性等功能)
        tableTrees.addMouseListener(new TableTreeMouseEventAdapter(tableModel));
        // 设置图标
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer(){
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                          boolean sel, boolean expanded, boolean leaf, int row,
                                                          boolean hasFocus) {
                String toolTipText = tableNameMap.get(value.toString());
                toolTipText = (toolTipText == null || toolTipText.length() <=  0) ? value.toString() : toolTipText;
                tree.setToolTipText(toolTipText);
                return super.getTreeCellRendererComponent(tree, value, sel,
                        expanded, leaf, row, hasFocus);
            }
        };
        // 展开时节点图标
        renderer.setOpenIcon(IconUtil.MYSQL);
        // 折叠时节点图标
        renderer.setClosedIcon(IconUtil.MYSQL);
        // 叶子节点图标
        renderer.setLeafIcon(IconUtil.TABLE);
        // 添加到表节点中
        tableTrees.setCellRenderer(renderer);
        // 设置子节点与子节点直接的高度(间隙)
        tableTrees.setRowHeight(22);
        // 添加滚动面板  默认 水平滚动和垂直滚动 都是需要时才显示的
        JScrollPane tablesScrollPanel = new JScrollPane();
        // setViewportView设置滚动显示视图内容组件 这里不能直接使用add
        tablesScrollPanel.setViewportView(tableTrees);
        // 取消水平滚动显示
        tablesScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // 创建表对接视图
        JYDockingView tableDockingView = new JYDockingView("tableDockingView", "数据库信息", "tableDockingView");
        // 设置边界布局让里面的组件全屏
        tableDockingView.setLayout(new BorderLayout());
        // 添加可以脱离主窗体功能
        tableDockingView.addAction(new DefaultFloatAction(tableDockingView));
        // 将滑动面板添加到表对接视图中
        tableDockingView.add(tablesScrollPanel);
        return tableDockingView;
    }

}
