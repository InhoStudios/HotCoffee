package io.inhostudios.hotcoffee;

import io.inhostudios.hotcoffee.voice.AudioBridge;
import io.inhostudios.hotcoffee.Utils.ByteReader;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class App extends ListenerAdapter {

    // Audio manager
    AudioManager currentTo = null;
    AudioManager currentFrom = null;
    AudioBridge bridge = new AudioBridge();

    public static void main(String[] args) throws LoginException{
        JDA jda = new JDABuilder(Token.token).build();
        jda.addEventListener(new App());
        Globals.initPf();
        BotBullying.initCB();
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

        if(msg.getContentRaw().toLowerCase().contains("nepu")){
            msgCh.sendMessage(author.getName() + " is most powerful because he can say the N word.").queue();
        }

        if(author.isBot() && !author.getId().equals("532241974682451968")){
            msgCh.sendMessage(BotBullying.chooseResp(author.getName())).queue();
        }

        // check swears
        //for(int i = 0; i < Swears.getSwears().size(); i++){
        if(Swears.checkProf(content)){
            msgCh.sendMessage(Globals.chooseResp()).queue();
            Swears.incrementPf(author);
            if(Swears.coffeeable(author)){
                msgCh.sendMessage("`Free coffee! First come first serve, courtesy of " + author.getName() + "`").queue();
            }
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
                String swearJarRet = "Current Debtors: \n\n";
                for(int i = 0; i < Swears.getUserIDs().size(); i++){
                    swearJarRet = swearJarRet + Swears.getNames().get(i) + ": $" + Swears.getValues().get(i) + "\n";
                }
                msgCh.sendMessage("```" + swearJarRet + "```").queue();
            }

            if(command.equalsIgnoreCase(Globals.update)){
                msgCh.sendMessage("`Updated file!`").queue();
                try{
                    Swears.readFile();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }

            if(command.equalsIgnoreCase("ping")){
                msgCh.sendMessage("Pong!").queue();
            }

            //audio manager

            //channel to bridge from
            if(command.startsWith(Globals.bridgeFrom)){
                String chanName = command.substring(Globals.bridgeFrom.length());
                VoiceChannel chan  = getChannelWithName(evt.getGuild(), chanName);
                if (chan == null)
                {
                    evt.getChannel().sendMessage("'" + chanName + "' does not exist.");
                    return;
                }
                if (currentFrom != null)
                {
                    currentFrom.setReceivingHandler(null);
                    currentFrom.closeAudioConnection();
                }
                currentFrom = evt.getGuild().getAudioManager();
                currentFrom.openAudioConnection(chan);
                currentFrom.setReceivingHandler(bridge);
                msgCh.sendMessage("Connected to `" + chan.getName() + "`").queue();
            }
            //channel to bridge to
            if(command.startsWith(Globals.bridgeTo)){
                String chanName = command.substring(Globals.bridgeTo.length());
                VoiceChannel chan  = getChannelWithName(evt.getGuild(), chanName);
                if (chan == null)
                {
                    evt.getChannel().sendMessage("'" + chanName + "' does not exist.");
                    return;
                }
                if (currentTo != null)
                {
                    currentTo.setSendingHandler(null);
                    currentTo.closeAudioConnection();
                }
                currentTo = evt.getGuild().getAudioManager();
                currentTo.openAudioConnection(chan);
                currentTo.setSendingHandler(bridge);

                msgCh.sendMessage("Connected to `" + chan.getName() + "`").queue();
            }

            if(command.equalsIgnoreCase(Globals.dcon)){
                if(currentTo.getSendingHandler() != null){
                    currentTo.setSendingHandler(null);
                    currentTo.closeAudioConnection();
                    currentTo = null;
                }
                if(currentFrom.getReceiveHandler() != null){
                    currentFrom.setReceivingHandler(null);
                    currentFrom.closeAudioConnection();
                    currentFrom = null;
                }
                msgCh.sendMessage("Disconnected!").queue();
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

    public VoiceChannel getChannelWithName(Guild guild, String chanName) {
        VoiceChannel channel = guild.getVoiceChannels().stream().filter(vChan -> vChan.getName().equalsIgnoreCase(chanName)).findFirst().orElse(null);
        return channel;
    }

}
