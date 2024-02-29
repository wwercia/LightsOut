package com.example.lightsout;

import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Map {

    private Field[][] map;
    private ArrayList<Field[][]> maps = new ArrayList<>();

    public Field[][] getNewMap(){
        Random random = new Random();
        int number = random.nextInt(maps.size());
        map = maps.get(number);
        return map;
    }

    // czytuje wszystkie mapy aby potem mozna było wylosować jakąś
    public void loadMaps() throws IOException {

        InputStream inputStream = getClass().getResourceAsStream("/com/example/lightsout/maps.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        ArrayList<String> areOn = new ArrayList<>();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null)
                break;
            String[] splitted = line.split("");
            areOn.addAll(Arrays.asList(splitted));
            if (line.equals("----")) {
                Field[][] map = new Field[5][5];
                int areOnIndex = 0;
                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[i].length; j++) {
                        boolean isOn = areOn.get(areOnIndex).equals("1");
                        Button button = new Button();
                        map[i][j] = new Field(i, j, isOn, button);
                        areOnIndex++;
                    }
                }
                maps.add(map);
                areOn.clear();
            }
        }
    }

    public void displayMap(Field[][] mapp) {
        for (int i = 0; i < mapp.length; i++) {
            for (int j = 0; j < mapp[i].length; j++) {
                String ch = mapp[i][j].isLightOn() ? "1" : "0";
                System.out.print(ch);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void setMap(Field[][] map) {
        this.map = map;
    }

    public ArrayList<Field[][]> getMaps() {
        return maps;
    }

}
