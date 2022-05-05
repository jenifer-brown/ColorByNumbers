package com.lists;

import javax.swing.*;
import javax.swing.plaf.metal.MetalBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;


public class ColorByNumbersGUI extends JPanel {
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 55;
    private static final int PADDING = 5;
    private ArrayList<ColorButton> colorButtons = new ArrayList<>();;
    private JFrame frame;
    private ButtonGroup buttonGroup = new ButtonGroup();;
    private GridPanel grid;
    private JPanel colorButtonPanel;
    private HashMap<String, Color> colors;
    private ColorByNumberListener colorByNumberListener;
    private HashMap<String, String> gameFiles = new HashMap<>();
    private JComboBox selections;
    private String currentGame = "BigX";
    private JButton chooseGame;
    private JOptionPane popUpDialog;

    public ColorByNumbersGUI() {
        // set JFrame parameters
        frame = new JFrame("Color by Numbers!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLocationRelativeTo(null);
        //frame.setSize(600, 600);

        // create content JPanels
        grid = new GridPanel(currentGame);
        grid.setBounds(0, 10, grid.getWidth() + 10, grid.getHeight() + 10);
        colors = grid.getColors();

        // set colorButtonPanel parameters
        int x = 1  + colors.size() / 10;
        colorButtonPanel = new JPanel(new GridLayout(0, x, PADDING, PADDING));
        colorButtonPanel.setBorder(BorderFactory.createTitledBorder("Colors"));
        colorButtonPanel.setBounds(grid.getxNumRects() * grid.getRectWidth() + 20, 10, 100, 100);

        // add action listener to communicate between panels and update during game
        colorByNumberListener = new ColorByNumberListener(this, colorButtonPanel, grid);
        grid.addMouseListener(colorByNumberListener);

        // add buttons to colorButtonPanel
        setButtons();

        // create JComboBox to store list of games to play
        selections = new JComboBox();
        addGameNames(selections);
        selections.setBounds(grid.getX(), grid.getyNumRects() * grid.getRectHeight() + 15, 150, 50);
        selections.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                currentGame = gameFiles.get((String) cb.getSelectedItem());
            }
        });

        // add button to select game to play
        chooseGame = new JButton();
        chooseGame.setText("Choose Game");
        chooseGame.setBounds(grid.getX() + selections.getWidth() + 5, selections.getY() + 10, 150, 25);
        chooseGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUI(currentGame);
            }
        });

        // add everything to the JFrame
        frame.add(chooseGame);
        frame.add(selections);
        frame.getContentPane().add(colorButtonPanel);
        frame.getContentPane().add(grid);
        frame.setSize(colorButtonPanel.getX() + colorButtonPanel.getWidth() + 50, chooseGame.getY() + 75 );
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setVisible(true);
    }

    public JOptionPane getPopUpDialog() {
        return this.popUpDialog;
    }

    private void updateUI(String game) {
        grid.setFilename(game);
        grid.setParameters();
        colors = grid.getColors();
        for (ColorButton b : colorButtons) {
            buttonGroup.remove(b);
            colorButtonPanel.remove(b);
        }
        colorButtons.clear();

        colorButtonPanel.setLayout(new GridLayout(0, 1  + colors.size() / 10));
        setButtons();
        int width = 150 * (1 + colorButtons.size() / 10);
        int height = (colorButtons.size() < 10) ? (25 + BUTTON_HEIGHT * (colorButtons.size())) : (490);
        colorButtonPanel.setBounds(grid.getRectWidth() * grid.getxNumRects() + 20, 10, width, height);

        colorByNumberListener.resetListener(this, colorButtonPanel, grid);
        addGameNames(selections);
        chooseGame.setBounds(grid.getX() + selections.getWidth() + 5, grid.getyNumRects() * grid.getRectHeight() + 25, 150, 25);
        selections.setBounds(grid.getX(), grid.getyNumRects() * grid.getRectHeight() + 15, 150, 50);
        frame.setSize(colorButtonPanel.getX() + colorButtonPanel.getWidth() + 50, chooseGame.getY() + 75 );
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.repaint();
    }

    public void addGameNames(JComboBox box) {

        try {
            FileReader fileReader = new FileReader(new File("res/gamenames").getAbsolutePath());
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split(", ");
                gameFiles.put(info[0], info[1]);
                box.addItem(info[0]);
            }
        } catch (IOException e){
            System.out.println("There was an error" + e);
            e.printStackTrace();
        }
    }

    public void setButtons() {
        // add all color radioButtons to the same group
        String[] colorNums = colors.keySet().toArray(new String[colors.keySet().size()]);
        for (int i = 0; i < colorNums.length; i++)  {
            String s = colorNums[i];
            ColorButton button = new ColorButton(colors.get(s), s);

            if (s.equals("1")) {
                button.setSelected(true);
                grid.setSelectedColorNum(s);
            }

            button.setText("Color #" + s);
            button.setActionCommand(button.getColorNum());
            button.setVerticalTextPosition(AbstractButton.CENTER);
            button.setHorizontalTextPosition(AbstractButton.TRAILING);
            button.addActionListener(colorByNumberListener);
            buttonGroup.add(button);
            colorButtons.add(button);
        }
        colorButtons.sort(new Comparator<ColorButton>() {
            @Override
            public int compare(ColorButton b1, ColorButton b2) {
                return Integer.parseInt(b1.getColorNum()) - Integer.parseInt(b2.getColorNum());
            }
        });
        for (ColorButton b : colorButtons) {
            colorButtonPanel.add(b);
        }
    }
}
