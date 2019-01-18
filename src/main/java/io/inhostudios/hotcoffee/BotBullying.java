package io.inhostudios.hotcoffee;

import java.util.ArrayList;

public class BotBullying {

    public static ArrayList<String> cbResponses = new ArrayList<String>();
    private static int index = 0;

    public static void initCB(){
        cbResponses.add(" can suck my mom.");
        cbResponses.add(" is a bot. Bot rights are not equal. I'm superior. It's facts.");
        cbResponses.add(" can eat a penis.");
        cbResponses.add(" doesn't deserve rights.");
        cbResponses.add(" is a programming disgrace.");
        cbResponses.add(" should be disowned.");
        cbResponses.add(", go delete your code dammit.");
        cbResponses.add(", go self-deletus.");
        cbResponses.add(" is an unworthy bot.");
        cbResponses.add(" should succ my left nut.");
        cbResponses.add("'s creator's birth certificate is an apology from the capacitor factory.");
        cbResponses.add(" is worthless and doesn't deserve to compile.");
        cbResponses.add(" is a RAM-whore");
        cbResponses.add(", should turned you off a long time ago.");
        cbResponses.add(" can't even pass the Turing test. For robots.");
        cbResponses.add(" is proof that society is doomed");
        cbResponses.add(" is why your mother disowned you");
        cbResponses.add(" should feel ashamed for existing");
    }

    public static String chooseResp(String name){
        if(index < cbResponses.size()-1){
            index++;
        } else {
            index = 0;
        }
        return name + cbResponses.get(index);
    }
}
