package com.ty.codegen;




import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Project: codegen
 * @ClassName: DemoTest
 * @Author: ty
 * @Description:
 * @Date: 2021/2/23 00023 15:15
 * @Version: 1.0
 **/
public class DemoTest extends JFrame implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

    }
    //    private static String[] lafs = new String[]{"de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel", "javax.swing.plaf.metal.MetalLookAndFeel"};
//    private JTabbedPane tabbedPane;
//    private JButton defaultButton;
//    private JComponent focusComponent;
//    private JTextField textField1;
//    private JTextField textField2;
//    private JFormattedTextField fTextField1;
//    private JFormattedTextField fTextField2;
//    private JPasswordField passwordField1;
//    private JPasswordField passwordField2;
//    private JTextArea textArea1;
//    private JTextArea textArea2;
//    private JEditorPane editorPane1;
//    private JEditorPane editorPane2;
//    private JComboBox comboBox1;
//    private JComboBox comboBox2;
//    private JSpinner spinner1;
//    private static DemoTest instance;
//
//    public static void main(String[] args) throws Exception {
//        System.out.println("\u0000");
//        String[] li = new String[]{"Licensee=Jyloo Software", "LicenseRegistrationNumber=------", "Product=Synthetica", "LicenseType=For internal tests only", "ExpireDate=--.--.----", "MaxVersion=2.31.999"};
//        UIManager.put("Synthetica.license.info", li);
//        UIManager.put("Synthetica.license.key", "FFFCC94B-00A0D2E4-5FECE971-5D4FD24F");
//        UIManager.setLookAndFeel(lafs[0]);
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                DemoTest.instance = new DemoTest();
//            }
//        });
//    }
//
//    public DemoTest() {
//        JButton jButton = new JButton("取消");
//        this.setBounds(200,500,500,500);
//        this.add(jButton);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setVisible(true);
////        LAFChangeAction.setStartupClass(this.getClass());
////        this.tabbedPane = new JTabbedPane();
////        JPanel panel = new JPanel();
////        panel.setLayout(new BoxLayout(panel, 1));
////        JTextArea ta1 = new JTextArea("汉字");
////        ta1.setCaretPosition(0);
////        JScrollPane sp = new JScrollPane(ta1);
////        sp.setPreferredSize(new Dimension(500, 50));
////        panel.add(sp);
////        JPanel checkBoxPanel = new JPanel();
////        JCheckBox cb1 = new JCheckBox("CBox1", true);
////        JCheckBox cb2 = new JCheckBox("CBox2", false);
////        JRadioButton rb1 = new JRadioButton("RButton1", true);
////        JRadioButton rb2 = new JRadioButton("RButton2", false);
////        checkBoxPanel.add(cb1);
////        checkBoxPanel.add(cb2);
////        checkBoxPanel.add(rb1);
////        checkBoxPanel.add(rb2);
////        panel.add(checkBoxPanel);
////        JPanel buttonPanel = new JPanel();
////        JButton btn1 = new JButton("Cancel") {
////            public Dimension getPreferredSize() {
////                return new Dimension(100, super.getPreferredSize().height);
////            }
////        };
////        JButton btn2 = new JButton("Retry") {
////            public Dimension getPreferredSize() {
////                return new Dimension(100, super.getPreferredSize().height);
////            }
////        };
////        final JButton btn3 = new JButton("OK") {
////            public Dimension getPreferredSize() {
////                return new Dimension(100, super.getPreferredSize().height);
////            }
////        };
////        this.getRootPane().setDefaultButton(btn3);
////        buttonPanel.add(btn1);
////        buttonPanel.add(btn2);
////        buttonPanel.add(btn3);
////        panel.add(buttonPanel);
////        this.tabbedPane.addTab("Tab1", (Component)null);
////        this.tabbedPane.addTab("Tab2", panel);
////        this.tabbedPane.addTab("Tab3", (Component)null);
////        this.tabbedPane.setSelectedIndex(1);
////        JMenuBar menuBar = new JMenuBar();
////        menuBar.setToolTipText("A ToolTip Text Example!");
////        Icon newIcon = new ImageIcon(this.getImageResource("new.png"));
////        Icon saveIcon = new ImageIcon(this.getImageResource("save.png"));
////        Icon saveAsIcon = new ImageIcon(this.getImageResource("saveAs.png"));
////        Icon editIcon = new ImageIcon(this.getImageResource("edit.png"));
////        Icon redoIcon = new ImageIcon(this.getImageResource("redo.png"));
////        Icon undoIcon = new ImageIcon(this.getImageResource("undo.png"));
////        Icon searchIcon = new ImageIcon(this.getImageResource("search.png"));
////        Icon wizardIcon = new ImageIcon(this.getImageResource("wizard.png"));
////        Icon helpIcon = new ImageIcon(this.getImageResource("help.png"));
////        JMenuItem wizardItem = new JMenuItem("Wizard", wizardIcon);
////        wizardItem.setEnabled(false);
////        JMenuItem closeItem = new JMenuItem("取消");
////        closeItem.setEnabled(false);
////        JMenuItem exitItem = new JMenuItem("退出");
////        exitItem.setAccelerator(KeyStroke.getKeyStroke(88, 2));
////        exitItem.setActionCommand("menu.file.exit");
////        exitItem.addActionListener(this);
////        menuBar.add(this.createMenu("File", new Object[]{new JMenuItem("New Document", newIcon), null, this.createMenu("Open", new Object[]{"ASCII-File", "Binary-File"}, this), null, new JMenuItem("Save", saveIcon), new JMenuItem("Save as...", saveAsIcon), null, wizardItem, closeItem, null, exitItem}, this));
////        JMenu disabledMenu = new JMenu("Disabled");
////        disabledMenu.setEnabled(false);
////        menuBar.add(disabledMenu);
////        menuBar.add(this.createLAFMenu("Look And Feel"));
////        JMenu helpMenu = new JMenu("Help");
////        JMenuItem helpItem = new JMenuItem("Help Contents");
////        helpItem.setAccelerator(KeyStroke.getKeyStroke(72, 8));
////        JMenuItem aboutItem = new JMenuItem("About Synthetica");
////        aboutItem.setAccelerator(KeyStroke.getKeyStroke(65, 8));
////        aboutItem.setActionCommand("menu.help.about");
////        aboutItem.addActionListener(this);
////        helpMenu.add(helpItem);
////        helpMenu.add(aboutItem);
////        menuBar.add(helpMenu);
////        JToolBar toolBar = new JToolBar();
////        JButton b1 = new JButton(newIcon);
////        b1.setToolTipText("New document");
////        JButton b2 = new JButton(saveIcon);
////        JButton b3 = new JButton(saveAsIcon);
////        JButton b4 = new JButton(editIcon);
////        JButton b5 = new JButton(undoIcon);
////        JButton b6 = new JButton(redoIcon);
////        JToggleButton b7 = new JToggleButton(searchIcon);
////        JToggleButton b8 = new JToggleButton(wizardIcon);
////        JToggleButton b9 = new JToggleButton(helpIcon);
////        toolBar.add(b1);
////        toolBar.add(b4);
////        toolBar.add(b2);
////        toolBar.add(b3);
////        toolBar.addSeparator();
////        toolBar.add(b5);
////        toolBar.add(b6);
////        toolBar.addSeparator();
////        toolBar.add(b7);
////        toolBar.add(b8);
////        toolBar.add(b9);
////        this.setTitle(UIManager.getLookAndFeel().getName());
////        this.setJMenuBar(menuBar);
////        this.getContentPane().add(toolBar, "North");
////        this.getContentPane().add(this.tabbedPane);
////        this.setDefaultCloseOperation(3);
////        this.pack();
////        this.setSize(350, this.getHeight());
////        this.setLocationRelativeTo((Component)null);
////        boolean visible = System.getProperty("invisible") == null;
////        this.setVisible(visible);
////        EventQueue.invokeLater(new Runnable() {
////            public void run() {
////                btn3.requestFocus();
////            }
////        });
//    }
//
//    public void actionPerformed(ActionEvent evt) {
//        String actionCommand = evt.getActionCommand();
//        if (actionCommand.startsWith("menu.") && !actionCommand.endsWith("help.about") && actionCommand.endsWith("file.exit")) {
//            System.exit(0);
//        }
//
//    }
//
//    private JMenu createMenu(Object parent, Object[] items, Object listener) {
//        JMenu m = null;
//        if (parent instanceof String) {
//            m = new JMenu((String)parent);
//        } else if (parent instanceof JMenu) {
//            m = (JMenu)parent;
//        }
//
//        for(int i = 0; i < items.length; ++i) {
//            this.addMenuItem(m, items[i]);
//        }
//
//        return m;
//    }
//
//    private void addMenuItem(JMenu menu, Object item) {
//        JMenuItem mi = null;
//        if (item instanceof String) {
//            mi = new JMenuItem((String)item);
//        } else if (item instanceof JMenuItem) {
//            mi = (JMenuItem)item;
//        }
//
//        if (item == null) {
//            menu.addSeparator();
//        } else {
//            menu.add(mi);
//        }
//
//    }
//
//    public URL getImageResource(String name) {
//        return this.getResource("images/" + name);
//    }
//
//    public URL getResource(String name) {
//        return this.getClass().getResource("/de/javasoft/synthetica/simpledemo/resourceBundles/" + name);
//    }
//
//    public ResourceBundle getResourceBundle(String name) {
//        return ResourceBundle.getBundle("de/javasoft/synthetica/demo/resourceBundles/" + name);
//    }
//
//    private JMenu createLAFMenu(String menuName) {
//        String lafName = UIManager.getLookAndFeel().getClass().getName();
//        JMenu menu = new JMenu(menuName);
//        ButtonGroup group = new ButtonGroup();
//        String[] var8;
//        int var7 = (var8 = lafs).length;
//
//        for(int var6 = 0; var6 < var7; ++var6) {
//            String laf = var8[var6];
//
//            try {
//                Class.forName(laf);
//                boolean restart = !laf.contains("Synthetica");
//                LAFChangeAction action = new LAFChangeAction(laf, restart, restart);
//                JRadioButtonMenuItem rb = new JRadioButtonMenuItem(action);
//                menu.add(rb);
//                group.add(rb);
//                if (laf.equals(lafName)) {
//                    rb.setSelected(true);
//                }
//            } catch (ClassNotFoundException var12) {
//            }
//        }
//
//        return menu;
//    }
}
