package com.googlecode.ochagl.musics;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;


public class Sound {
    private Clip clip;

    public Sound(String resname) {

        URL url = Sound.class.getClassLoader().getResource(resname);

        if (url == null) {
            throw new RuntimeException(new IOException("Cannot find: " + resname));
        }
        try {
        AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResourceAsStream(resname));
        AudioFormat format = ais.getFormat();
        clip = (Clip) AudioSystem.getLine(new DataLine.Info(
                Clip.class, format));
        clip.open(ais);

        clip.addLineListener(new LineListener() {
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.STOP) {
                    event.getLine().close();
                }
            }
        });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void play() {
        clip.start();
    }
}
