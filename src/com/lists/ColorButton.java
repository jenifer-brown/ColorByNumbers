package com.lists;

import javax.swing.*;
import java.awt.*;

public class ColorButton extends JRadioButton {
    private Color color;
    private String colorNum;
    private static final Dimension PREFERRED_SIZE = new Dimension(100, 55);

    ColorButton(Color color, String colorNum) {
        this.color = color;
        this.colorNum = colorNum;
    }

    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getColorNum() {
        return colorNum;
    }

    public void setColorNum(String colorNum) {
        this.colorNum = colorNum;
    }


}
