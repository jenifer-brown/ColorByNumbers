package com.lists;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;


public class ColorByNumberListener implements ActionListener, MouseListener {
    private JPanel colorButtonPanel;
    private GridPanel gridPanel;
    private ColorByNumbersGUI gui;

    public ColorByNumberListener(ColorByNumbersGUI gui, JPanel colorButtonPanel, GridPanel gridPanel) {
        this.colorButtonPanel = colorButtonPanel;
        this.gridPanel = gridPanel;
        this.gui = gui;
    }

    public void resetListener(ColorByNumbersGUI gui, JPanel colorButtonPanel, GridPanel gridPanel) {
        this.colorButtonPanel = colorButtonPanel;
        this.gridPanel = gridPanel;
        this.gui = gui;
        gridPanel.setIsComplete(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object clicked = e.getSource();
        if (clicked instanceof ColorButton) {
            gridPanel.setSelectedColorNum(((ColorButton) clicked).getColorNum());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == gridPanel && gridPanel.getIsComplete()) {
            JLabel completeMessage = new JLabel("Yay!!! You've completed the picture!!");
            completeMessage.setHorizontalTextPosition(SwingConstants.LEFT);
            String img = new File("res/finished.png").getAbsolutePath();
            ImageIcon icon = new ImageIcon(img, "a celebratory icon");
            JOptionPane.showMessageDialog(gui,
                    completeMessage,
                    "Congratulations!",
                    JOptionPane.PLAIN_MESSAGE, icon);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
