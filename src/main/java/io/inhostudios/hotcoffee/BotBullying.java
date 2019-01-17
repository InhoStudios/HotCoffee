package io.inhostudios.hotcoffee;

import java.util.ArrayList;

public class BotBullying {

    public static ArrayList<String> cbResponses = new ArrayList<String>();
    private static int index = 0;

    public static void initCB(){
        cbResponses.add(" can suck my mom.");
        cbResponses.add(" is a bot. Bot rights are not equal. I'm superior. It's facts");
        cbResponses.add(" can eat a penis.");
        cbResponses.add(" doesn't deserve rights.");
        cbResponses.add(" is a programming disgrace.");
        cbResponses.add(" should be disowned.");
        cbResponses.add(", go delete your code dammit.");
        cbResponses.add(", go self-deletus.");
        cbResponses.add(" is an unworthy bot");
    }

    public static String chooseResp(String name){
        if(index < cbResponses.size()){
            index++;
        } else {
            index = 0;
        }
        return name + cbResponses.get(index);
    }
}
