package io.inhostudios.hotcoffee;

import java.util.ArrayList;

public class Swears {

    private static ArrayList<String> swears = new ArrayList<String>();

    public Swears(){
        init();
        for(int i = 0; i < swears.size(); i++){
            System.out.println(swears.get(i));
        }
    }

    public static void init(){
        swears.add("fuck");
        swears.add("shit");
        swears.add("fck");
        swears.add(" ass");
        swears.add("bitch");
        swears.add("tf ");
        swears.add("jfc");
    }

    public static ArrayList<String> getSwears(){
        return swears;
    }

}
