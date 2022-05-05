package com.lists;

import java.awt.*;
import java.io.*;
import java.util.HashMap;

public class InputReader {
    private HashMap<String, Color> colors = new HashMap<>();
    private int num_x;
    private int num_y;
    private String[][] gridLayout;
    private String gridName;
    private String fileName;

    public InputReader(String fileName) {
        this.fileName = fileName;
    }

    public void readInputFile() {
        String file = new File("res/" + fileName + ".txt").getAbsolutePath();
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (i == 0) {// set the basic parameters of the image grid
                    String[] parameters = line.split(", ");
                    num_x = Integer.parseInt(parameters[0]);
                    num_y = Integer.parseInt(parameters[1]);
                    gridLayout = new String[num_x * num_y][1];
                    gridName = parameters[3];
                    i++;
                    continue;
                }// assign x and y position, color, and color number for each square in the grid
                gridLayout[i - 1] = line.split(", ");
                // convert 3rd element in gridLayout to color
                Color sqColor = getInputColor(gridLayout[i - 1][2]);
                // add color with keyed number string to link color with number
                colors.put(gridLayout[i - 1][3], sqColor);
                i++;
            }
        } catch (IOException e) {
            System.out.println("There was an error: " + e);
            e.printStackTrace();
        }
    }

    public Color getInputColor(String unparsed) {
        int r = Integer.parseInt(unparsed.substring(0, 3));
        int g = Integer.parseInt(unparsed.substring(3, 6));
        int b = Integer.parseInt(unparsed.substring(6));
        return new Color(r, g, b);
    }

    public int getGridX() {
        return this.num_x;
    }

    public int getGridY() {
        return this.num_y;
    }

    public HashMap<String, Color> getGridColors() {
        return this.colors;
    }

    public String[][] getGridLayout() {
        return this.gridLayout;
    }

}
