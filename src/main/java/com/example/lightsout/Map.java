package com.example.lightsout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Map {

    private Field[][] map;

    public void loadMaps() throws IOException {

        InputStream inputStream = getClass().getResourceAsStream("/com/example/lightsout/maps.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        ArrayList<String> areOn = new ArrayList<>();
        while (true){
            String line = bufferedReader.readLine();
            if(line.equals("----"))
                break;
            String[] splitted = line.split("");
            areOn.addAll(Arrays.asList(splitted));
        }
        System.out.println(areOn.toString());

        map = new Field[5][5];
        int areOnIndex = 0;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                boolean isOn = areOn.get(areOnIndex).equals("1");
                map[i][j] = new Field(i, j, isOn);
                areOnIndex++;
            }
        }


        System.out.println(map);
    }

    public static void main(String[] args) throws IOException {
        Map map = new Map();
        map.loadMaps();
        for(int i = 0; i < map.map.length; i ++){
            for(int j = 0; j < map.map[i].length; j++){
                String ch = map.map[i][j].isOn() ? "1" : "0";
                System.out.print(ch);
            }
            System.out.println();
        }
    }


}
