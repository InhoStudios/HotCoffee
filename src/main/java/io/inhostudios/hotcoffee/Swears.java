package io.inhostudios.hotcoffee;

import net.dv8tion.jda.core.entities.User;

import java.io.*;
import java.util.ArrayList;

public class Swears {

    private static ArrayList<String> swears = new ArrayList<String>();
    private static ArrayList<String> safeWords = new ArrayList<String>();

    private static ArrayList<String> userIDs = new ArrayList<String>();
    private static ArrayList<Integer> values = new ArrayList<Integer>();
    private static ArrayList<String> names = new ArrayList<String>();

    public Swears(){
        init();
        for(int i = 0; i < swears.size(); i++){
            System.out.println(swears.get(i));
        }
    }

    public static void init(){
        swears.add("fuck");
        swears.add("fuuuck");
        swears.add("shit");
        swears.add("fck");
        swears.add(" ass");
        swears.add("bitch");
        swears.add("tf ");
        swears.add("jfc");
        swears.add("bitchass");
        swears.add("bich");
        swears.add("dick");

        safeWords.add("pass");
        safeWords.add("bass");
        safeWords.add("kafka");
    }

    public static ArrayList<String> getNames() {
        return names;
    }

    public static ArrayList<String> getSwears(){
        return swears;
    }

    public static ArrayList<String> getUserIDs() {
        return userIDs;
    }

    public static ArrayList<Integer> getValues() {
        return values;
    }

    public static boolean checkProf(String content){

        for(int i = 0; i < swears.size(); i++){
            for(int j = 0; j < safeWords.size(); j++){
                if(content.contains(swears.get(i)) && content.contains(safeWords.get(i))){
                    if(content.replace(safeWords.get(i),"").contains(swears.get(i))){
                       return true;
                    }
                }
            }
        }

        content = content
                .replace(" ", "")
                .replace(".","")
                .replace("!","")
                .replace("-","");
        System.out.println(content);
        for(int i = 0; i < swears.size(); i++){
            if(content.toLowerCase().contains(swears.get(i))){
                return true;
            }
        }
        if(content.contains("ass")){
            int index = content.indexOf("ass");
            if(index == 0 || content.substring(index - 1, index).equalsIgnoreCase(" ")){
                return true;
            }
        }

        if((content.length() == 2 && content.equalsIgnoreCase("fk"))){

        }

        //check for fucc
        /*if(content.contains("f") && content.contains("u") && content.contains ("c")){
            if(content.substring(content.indexOf("f") + 1,content.indexOf("f" + 2)).equals("u")){
                if(content.indexOf("c") - 1 == content.indexOf("u")){
                    return true;
                }
            }
        }*/
        return false;
    }

    public static void readFile() throws IOException {
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\data.file";
        File file = new File(path);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = "";
        while(!(line = br.readLine()).equals(null)){
            System.out.println(line);
            int splitIndex = line.indexOf(";");
            int splitIndex2 = line.indexOf("|");
            userIDs.add(line.substring(0,splitIndex));
            names.add(line.substring(splitIndex + 1, splitIndex2));
            values.add(Integer.parseInt(line.substring(splitIndex2 + 1)));
            System.out.println("Data received: " + line);
        }
    }

    public static void incrementPf(User user) {
        boolean found = false;
        for (int i = 0; i < userIDs.size(); i++) {
            if(user.getId().equals(userIDs.get(i))){
                values.set(i,values.get(i) + 1);
                found = true;
                break;
            }
        }
        if(!found){
            userIDs.add(user.getId());
            names.add(user.getName());
            values.add(1);
        }
    }

    public static void saveUsers() throws IOException {
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\data.file";
        System.out.println("File path: " + path);
        File file = new File(path);
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);

        System.out.println("Save initiated");

        for(int i = 0; i < userIDs.size(); i++){
            String saveData = userIDs.get(i) + ";" + names.get(i) + "|" + values.get(i) + "\n";
            System.out.println("Saving data " + saveData);
            pw.write(saveData);
            System.out.println("Saved");
        }

        pw.close();
    }

}
