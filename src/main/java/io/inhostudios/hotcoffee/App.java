package io.inhostudios.hotcoffee;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class App extends ListenerAdapter {

    public static void main(String[] args) throws LoginException{
        JDA jda = new JDABuilder(Token.token).build();
        jda.addEventListener(new App());
        Globals.initPf();
        Swears.init();
        try {
            Swears.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onMessageReceived(MessageReceivedEvent evt){
        MessageChannel msgCh = evt.getChannel();
        Message msg = evt.getMessage();
        User author = evt.getAuthor();
        String content = msg.getContentRaw();

        // check swears
        //for(int i = 0; i < Swears.getSwears().size(); i++){
        if(Swears.checkProf(content)){
            msgCh.sendMessage(Globals.chooseResp()).queue();
            Swears.incrementPf(author);
            try{
                Swears.saveUsers();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        //}

        if(content.length() > Globals.pref.length() && content.substring(0,Globals.pref.length()).equalsIgnoreCase(Globals.pref)){
            System.out.println("Command Entered");
            String command = content.substring(Globals.pref.length());

            if(command.equalsIgnoreCase(Globals.swear)){
                for(int i = 0; i < Swears.getUserIDs().size(); i++){
                    msgCh.sendMessage(Swears.getNames().get(i) + ": " + Swears.getValues().get(i)).queue();
                }
            }

            if(command.equalsIgnoreCase("ping")){
                msgCh.sendMessage("Pong!").queue();
            }

        }

        if(evt.isFromType(ChannelType.PRIVATE)){
            System.out.printf("[PM] %s: %s\n", evt.getAuthor().getName(), evt.getMessage().getContentDisplay());
        }
        else
        {
            System.out.printf("[%s][%s] %s: %s\n", evt.getGuild().getName(), evt.getTextChannel().getName(), evt.getMember().getEffectiveName(), evt.getMessage().getContentDisplay());
        }
    }

}
