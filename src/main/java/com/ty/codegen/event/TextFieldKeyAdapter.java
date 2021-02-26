package com.ty.codegen.event;

import com.ty.codegen.enums.TextFieldTypeEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @Project: codegen
 * @ClassName: TextFieldKeyAdapter
 * @Author: ty
 * @Description: 输入框键盘监听适配 主要验证输入输入框时是否合法字符串
 * @Date: 2021/2/2
 * @Version: 1.0
 **/
@Slf4j
@Getter
public class TextFieldKeyAdapter extends KeyAdapter {

    // 验证输入框类型
    private TextFieldTypeEnum textFieldType;
    // 最大可输入字符长度
    private Integer maxCharLength;

    public TextFieldKeyAdapter(TextFieldTypeEnum textFieldType) {
        this.textFieldType = textFieldType;
    }

    public TextFieldKeyAdapter(int maxCharLength) {
        this(null, maxCharLength);
    }

    public TextFieldKeyAdapter(TextFieldTypeEnum textFieldType, int maxCharLength) {
        this.textFieldType = textFieldType;
        this.maxCharLength = maxCharLength;
    }

    /**
     * 键盘输入时触发的事件
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // 验证最大输入长度
        checkMaxCharLength(e);
        // 为null表示不用校验
        if (textFieldType == null) {
            return;
        }
        switch (textFieldType) {
            case NUMBER:
                // 验证是否是数字
                checkNumber(e);
                break;
            case IP:
                break;
            case EMAIL:
                break;
            case PHONE:
                break;
            case ID_CARD:
                break;
            default:

        }

    }

    /**
     * 键盘按下时触发的事件
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // 按下复制快捷键Ctrl+V
        if (KeyEvent.VK_V == e.getKeyCode() && e.isControlDown()) {
            // 获取系统剪贴板
            String clipboardString = getClipboardString();
            JTextField inputTextField = (JTextField) e.getSource();
            // 将原本输入的字符串和剪切板中的字符串拼接生成最终字符串
            inputTextField.setText(inputTextField.getText() + clipboardString);
            // 再次验证是否满足长度条件
            checkMaxCharLength(e);
        }
    }

    /**
     * 从剪贴板中获取文本（粘贴）
     */
    private  String getClipboardString() {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 获取剪贴板中的内容
        Transferable trans = clipboard.getContents(null);
        if (trans != null) {
            // 判断剪贴板中的内容是否支持文本
            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 获取剪贴板中的文本内容
                    String text = (String) trans.getTransferData(DataFlavor.stringFlavor);
                    return text;
                } catch (Exception e) {
                    log.error("获取操作系统剪切板中字符串异常 e : {}",e);
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 验证输入的字符是否满足最大长度
     *
     * @param e
     */
    private void checkMaxCharLength(KeyEvent e) {
        // 验证输入框输入的字符的长度必须满足条件
        if (this.maxCharLength == null || this.maxCharLength <= 0) {
            return;
        }
        JTextField inputTextField = (JTextField) e.getSource();
        String text = inputTextField.getText();
        // 验证输入框输入的字符的长度必须满足条件
        if (text == null) {
            return;
        }
        if (text.length() >= this.maxCharLength) {
            inputTextField.setText(text.substring(0,this.maxCharLength));
            // 取消输入
            e.consume();
        }
    }

    /**
     * 校验输入是否是数字
     *
     * @param e
     */
    private void checkNumber(KeyEvent e) {
        String key = "0123456789" + (char) 8;
        if (key.indexOf(e.getKeyChar()) < 0) {
            // 如果不是数字则取消
            e.consume();
        }
    }

}
