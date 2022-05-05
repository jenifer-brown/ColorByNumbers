package com.lists;

import java.awt.*;


public class GridSquares extends Rectangle {

    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private Color assignedColor;
    private Color fillColor = Color.white;
    private Color borderColor = Color.black;
    private boolean filled = false;
    private String colorNumber;

    public GridSquares(int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height);
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    public void setColorNumber(String num) {
        this.colorNumber = num;
    }

    public String getColorNumber() {
        return this.colorNumber;
    }

    public Color getAssignedColor() { return this.assignedColor; }

    public void setAssignedColor(Color assignedColor) {
        this.assignedColor = assignedColor;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public boolean isFilled() {
        return filled;
    }

    public int getXPos() {
        return xPos;
    }

    public void setYPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }


    public void paintGridSquare(Graphics g) {
        g.setColor(fillColor);
        g.fillRect(xPos, yPos, width, height);
        g.setColor(borderColor);
        g.drawRect(xPos, yPos, width, height);
    }
}
