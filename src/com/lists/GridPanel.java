package com.lists;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class GridPanel extends JPanel {
    protected static int X_NUM_RECTS = 14;
    protected static int Y_NUM_RECTS = 14;
    protected static final int RECT_WIDTH = 50;
    protected static final int RECT_HEIGHT = 50;
    protected static final int PADDING = 5;
    private HashMap<String, Color> colors = new HashMap<>();
    private String[][] gridLayout;
    private ArrayList<GridSquares> squares;
    private Graphics2D g2;
    private String filename;
    private InputReader input;
    private String selectedColorNum = "1";
    private int numColoredBoxes;
    private boolean isComplete = false;

    GridPanel(String fileName) {
        setLayout(new BorderLayout());
        // set parameters according to the grid being created
        this.filename = fileName;
        setParameters();

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { updateSquare(e.getX(), e.getY()); }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

    }

    public void setParameters() {
        input = new InputReader(filename);
        input.readInputFile();
        X_NUM_RECTS = input.getGridX();
        Y_NUM_RECTS = input.getGridY();
        gridLayout = new String[X_NUM_RECTS * Y_NUM_RECTS][1];
        gridLayout = input.getGridLayout();
        colors = input.getGridColors();
        createGridSquares();
    }

    public HashMap<String, Color> getColors() {
        return this.colors;
    }

    public String getFilename() {
        return this.filename;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean complete) {
        this.isComplete = complete;
    }

    private void updateSquare(int x, int y) {
        for (GridSquares s : squares) {
            if (s.contains(x, y) && selectedColorNum.equals(s.getColorNumber())) {
                s.setFillColor(s.getAssignedColor());
                fillSquare(s, x, y);
                if (!s.isFilled()) {
                    s.setFilled(true);
                    numColoredBoxes++;
                }
                if (numColoredBoxes == X_NUM_RECTS * Y_NUM_RECTS) {
                    setIsComplete(true);
                }
                break;
            }
        }
    }

    public void createGridSquares() {
        squares = new ArrayList<>();
        numColoredBoxes = 0;
        for (int i = 0; i < X_NUM_RECTS * Y_NUM_RECTS; i++) {
            int xPos = PADDING + RECT_WIDTH * Integer.parseInt(gridLayout[i][0]);
            int yPos = PADDING + RECT_HEIGHT * Integer.parseInt(gridLayout[i][1]);
            GridSquares sq = new GridSquares(xPos, yPos, RECT_WIDTH, RECT_HEIGHT);
            sq.setAssignedColor(input.getInputColor(gridLayout[i][2]));
            sq.setColorNumber(gridLayout[i][3]);
            squares.add(sq);
        }
    }

    public ArrayList<GridSquares> getGridSquares() { return squares; }

    public void fillSquare(GridSquares sq, int xPos, int yPos) {
        sq.setFillColor(sq.getAssignedColor());
        repaint(sq);
    }

    public void setSelectedColorNum(String str) {
        this.selectedColorNum = str;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        int i = 0;
        for (GridSquares s : squares) {
            s.paintGridSquare(g2);
            g2.drawString(s.getColorNumber(),s.getXPos() + (RECT_WIDTH / 2) - PADDING,
                                            s.getYPos() + (RECT_HEIGHT / 2) + PADDING);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(RECT_WIDTH + X_NUM_RECTS * RECT_WIDTH,
                            RECT_HEIGHT + Y_NUM_RECTS * RECT_HEIGHT);
    }

    public void setFilename(String str) {
        this.filename = str;
    }

    public int getxNumRects() {
        return X_NUM_RECTS;
    }

    public int getyNumRects() {
        return Y_NUM_RECTS;
    }

    public int getRectWidth() {
        return RECT_WIDTH;
    }

    public int getRectHeight() {
        return RECT_HEIGHT;
    }

    public static int getPadding() {
        return PADDING;
    }

}
