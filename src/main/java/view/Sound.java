package view;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

    private static Clip clip;

    public static Clip getAudioClip(String fileName) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File audioFile = new File("src/main/resources/view/audio/" + fileName).getAbsoluteFile();
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }

    public static void playSound(String soundName) {
        if (clip != null) {
            stopMusic();
        }
        try {
            clip = getAudioClip(soundName + ".wav");
        } catch (Exception ignored) {
        }
        clip.start();
    }

    public static void stopMusic() {
        clip.stop();
        clip = null;
    }
}
