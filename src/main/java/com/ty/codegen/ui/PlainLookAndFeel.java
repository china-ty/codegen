package com.ty.codegen.ui;

import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;

import java.text.ParseException;

/**
 * @Project: codegen
 * @ClassName: PlainLookAndFeel
 * @Author: ty
 * @Description: 白色主题风格
 * @Date: 2021/2/3
 * @Version: 1.0
 **/
public class PlainLookAndFeel extends SyntheticaLookAndFeel {


    public PlainLookAndFeel() throws ParseException {
        super("/config/plain/style.xml");
    }

    public String getID() {
        return "PlainLookAndFeel";
    }

    public String getName() {
        return "Plain Look and Feel";
    }
}
