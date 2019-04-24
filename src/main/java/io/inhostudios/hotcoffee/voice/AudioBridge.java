package io.inhostudios.hotcoffee.voice;

import net.dv8tion.jda.core.audio.AudioReceiveHandler;
import net.dv8tion.jda.core.audio.AudioSendHandler;
import net.dv8tion.jda.core.audio.CombinedAudio;
import net.dv8tion.jda.core.audio.UserAudio;
import net.dv8tion.jda.core.entities.User;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AudioBridge implements AudioReceiveHandler, AudioSendHandler {

    double volume = 0.5;
    ConcurrentLinkedQueue<byte[]> bridgeQueue = new ConcurrentLinkedQueue<byte[]>();

    @Override
    public boolean canReceiveCombined(){
        return true;
    }

    @Override
    public boolean canReceiveUser(){
        return false;
    }

    @Override
    public void handleCombinedAudio(CombinedAudio combinedAudio){
        bridgeQueue.add(combinedAudio.getAudioData(volume));
    }

    @Override
    public void handleUserAudio(UserAudio userAudio){

    }

    public void handleUserTalking(User user, boolean talking){

    }

    public boolean canProvide(){
        return !bridgeQueue.isEmpty();
    }

    public byte[] provide20MsAudio(){
        return bridgeQueue.poll();
    }

}