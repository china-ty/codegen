package com.ty.codegen.win;

import com.ty.codegen.util.IconUtil;
import de.javasoft.synthetica.panels.SourcePane;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Project: codegen
 * @ClassName: CodePreviewWin
 * @Author: ty
 * @Description: 代码预览
 * @Date: 2021/2/2
 * @Version: 1.0
 **/
public class CodePreviewWin extends JFrame {
    private static Map<String, CodePreviewWin> cacheCodePreviewWin = new HashMap<>();

    public CodePreviewWin(String title) {
        if (title != null) {
            this.setTitle(title + " - 代码预览");
        } else {
            this.setTitle("代码预览");
        }
        // 初始化代码预览窗体
        this.init();
        // 初始化组件
        this.setupComponent();
        // 显示窗体
        this.setVisible(true);
        cacheCodePreviewWin.put(title, this);
    }

    /**
     * 初始化窗体
     */
    private void init() {
        // 得到显示器屏幕的宽高
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        // 设置窗体大小和位置
        this.setSize(900, 600);
        this.setLocation((width - this.getWidth()) / 2, (height - this.getHeight()) / 2);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * 设置代码预览组件
     */
    private void setupComponent() {
        // 创建选项卡面板
        JTabbedPane codeTabbedPanes = new JTabbedPane();
        String code = getCode();
        // Controller层代码预览
        codeTabbedPanes.addTab("Controller", IconUtil.CODE, createCodeScrollPane(code), "这是Java代码");
        // Service层代码预览
        codeTabbedPanes.addTab("Service", IconUtil.CODE, createTextPanel("该页面正在开发中,敬请期待..."));
        // Mapper层代码预览
        codeTabbedPanes.addTab("Mapper", IconUtil.CODE, createTextPanel("该页面正在开发中,敬请期待..."));
        // Entity层代码预览
        codeTabbedPanes.addTab("Entity", IconUtil.CODE, createTextPanel("该页面正在开发中,敬请期待..."));
        // 设置默认选中的选项卡
        codeTabbedPanes.setSelectedIndex(0);
        this.getContentPane().add(codeTabbedPanes);
    }

    private String getCode() {
        String source = "package com.ty.codegen.ui;\n" +
                "\n" +
                "import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;\n" +
                "\n" +
                "import java.text.ParseException;\n" +
                "\n" +
                "/**\n" +
                " * @Project: codegen\n" +
                " * @ClassName: PlainLookAndFeel\n" +
                " * @Author: ty\n" +
                " * @Description: 白色主题风格(重写) 移除\n" +
                " * @Date: 2021/2/3\n" +
                " * @Version: 1.0\n" +
                " **/\n" +
                "@Deprecated\n" +
                "public class PlainLookAndFeel extends SyntheticaLookAndFeel {\n" +
                "\n" +
                "\n" +
                "    public PlainLookAndFeel() throws ParseException {\n" +
                "        super(\"/config/plain/style.xml\");\n" +
                "    }\n" +
                "\n" +
                "    public String getID() {\n" +
                "        return \"PlainLookAndFeel\";\n" +
                "    }\n" +
                "\n" +
                "    public String getName() {\n" +
                "        return \"Plain Look and Feel\";\n" +
                "    }\n" +
                "}\n";
        return source;
    }

    /**
     * 代码高亮文本面板
     *
     * @param codeText 代码
     * @return
     */
    private JScrollPane createCodeScrollPane(String codeText) {
        JScrollPane codeScrollPane = new JScrollPane();
        if (codeText == null) {
            return codeScrollPane;
        }
        // 可代码高亮文本面板
        SourcePane sourcePane = new SourcePane();
        // 禁止编辑
        sourcePane.setEditable(false);
        // 设置文本内容
        sourcePane.setSourceText(codeText);
        sourcePane.addMouseWheelListener(e -> {
            if (e.getWheelRotation() != -1 && e.getWheelRotation() != 1) {
                return;
            }
            // 按住Ctrl进行放大
            if (e.getWheelRotation() == -1 && e.isControlDown()) {
                SourcePane source = (SourcePane) e.getSource();
                Font font = source.getFont();
                if (font.getSize() >= 30) {
                    return;
                }
                source.setFont(new Font(font.getFontName(), font.getStyle(), source.getFont().getSize() + 1));
                // 按住Ctrl进行缩小
            } else if (e.getWheelRotation() == 1 && e.isControlDown()) {
                SourcePane source = (SourcePane) e.getSource();
                Font font = source.getFont();
                if (font.getSize() <= 10) {
                    return;
                }
                source.setFont(new Font(font.getFontName(), font.getStyle(), source.getFont().getSize() - 1));
            } else {
                // 未按住Ctrl时执行垂直滑动条进行滑动
                JScrollPane scrollPane = (JScrollPane) e.getComponent().getParent().getParent();
                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                // 向上滑动
                if (e.getWheelRotation() == -1) {
                    verticalScrollBar.setValue(verticalScrollBar.getValue() - 35);
                    // 向下滑动
                } else if (e.getWheelRotation() == 1) {
                    verticalScrollBar.setValue(verticalScrollBar.getValue() + 35);
                }
            }
        });
        codeScrollPane.setViewportView(sourcePane);
        return codeScrollPane;
    }

    /**
     * 获取光标在几行
     *
     * @param offset   {@link javax.swing.text.Caret#getDot()}
     * @param document {@link Document}
     * @return
     * @throws BadLocationException
     */
    @Deprecated // 暂时不用
    public int getLineOfOffset(int offset, Document document) throws BadLocationException {
        Document doc = document;
        if (offset < 0) {
            throw new BadLocationException("Can't translate offset to line", -1);
        } else if (offset > doc.getLength()) {
            throw new BadLocationException("Can't translate offset to line", doc.getLength() + 1);
        } else {
            Element map = doc.getDefaultRootElement();
            return map.getElementIndex(offset);
        }
    }

    /**
     * 获取光标在几列 调用该方法后获取返回值 然后用光标位置{@link javax.swing.text.Caret#getDot()} 值减去就得到当前列
     *
     * @param line     第几行当前光标
     * @param document {@link Document}
     * @return
     * @throws BadLocationException
     */
    @Deprecated // 暂时不用
    public int getLineStartOffset(int line, Document document) throws BadLocationException {
        Element map = document.getDefaultRootElement();
        if (line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if (line >= map.getElementCount()) {
            throw new BadLocationException("No such line", document.getLength() + 1);
        } else {
            Element lineElem = map.getElement(line);
            return lineElem.getStartOffset();
        }
    }

    /**
     * 去除换行符table键的特殊字符
     *
     * @param str
     * @return
     */
    public String replaceBlank(String str) {
        String dest = "";

        if (str != null) {

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");

            Matcher m = p.matcher(str);

            dest = m.replaceAll("");
            dest = dest.replace(";", "");

        }

        return dest;

    }

    /**
     * 创建一个面板，面板中心显示一个标签，用于表示某个选项卡需要显示的内容
     */
    private static JComponent createTextPanel(String text) {
        // 创建面板, 使用一个 1 行 1 列的网格布局（为了让标签的宽高自动撑满面板）
        JPanel panel = new JPanel(new GridLayout(1, 1));

        // 创建标签
        JLabel label = new JLabel(text);
        label.setFont(new Font(null, Font.PLAIN, 50));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // 添加标签到面板
        panel.add(label);

        return panel;
    }

    public static Map<String, CodePreviewWin> getCacheCodePreviewWin() {
        return cacheCodePreviewWin;
    }
}
