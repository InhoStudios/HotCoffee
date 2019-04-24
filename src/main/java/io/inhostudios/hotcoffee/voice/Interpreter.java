package io.inhostudios.hotcoffee.voice;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import io.inhostudios.hotcoffee.Utils.EnvironmentUtil;

import java.io.IOException;
import java.util.List;

public class Interpreter implements Runnable {

    private String path = System.getProperty("user.dir") + "\\src\\main\\resources\\credentials.json";

    public Interpreter(byte[] data) throws Exception{

        try{
            EnvironmentUtil.injectEnvironmentVariable("GOOGLE_APPLICATION_CREDENTIALS", path);
        } catch(Exception e){
            e.printStackTrace();
        }

        processSpeech(data);
    }

    public List<SpeechRecognitionResult> processSpeech(byte[] data){
        try (SpeechClient speechClient = SpeechClient.create()){
            ByteString audioBytes = ByteString.copyFrom(data);

            RecognitionConfig config = RecognitionConfig.newBuilder().setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                    .setSampleRateHertz(16000)
                    .setLanguageCode("en-US")
                    .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            for(SpeechRecognitionResult result : results){
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                System.out.printf("Transcription: %s%n", alternative.getTranscript());
            }

            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run() {

    }
}
