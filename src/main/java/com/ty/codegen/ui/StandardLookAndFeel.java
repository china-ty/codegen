package com.ty.codegen.ui;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;

import java.text.ParseException;

/**
 * @Project: codegen
 * @ClassName: StandardLookAndFeel
 * @Author: ty
 * @Description: 标准主题风格
 * @Date: 2021/2/3
 * @Version: 1.0
 **/
public class StandardLookAndFeel extends SyntheticaLookAndFeel {

    public StandardLookAndFeel() throws ParseException {
        super("/config/standard/style.xml");
    }

    public String getID() {
        return "StandardLookAndFeel";
    }

    public String getName() {
        return "Standard Look and Feel";
    }
}
