package io.inhostudios.hotcoffee;

import java.util.ArrayList;

public class Swears {

    private static ArrayList<String> swears = new ArrayList<String>();

    public Swears(){
        init();
    }

    private void init(){
        swears.add("fuck");
        swears.add("shit");
        swears.add("fck");
        swears.add("ass");
    }

    public static ArrayList<String> getSwears(){
        return swears;
    }

}
