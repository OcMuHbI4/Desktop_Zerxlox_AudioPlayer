import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;



public class PlaySoundMp3 extends Thread {
    static AdvancedPlayer  player;

    static int[] pausedOnFrame = {0};

    static FileInputStream fis;

    static BufferedInputStream bis;
    static File soundFile;

    static void playMp3( String songName) {

        soundFile = new File(BootPlayer.selectedCellPath);

        try {
            fis = new FileInputStream(soundFile);
            bis = new BufferedInputStream(fis);
            player = new AdvancedPlayer(bis);

            player.setPlayBackListener(new PlaybackListener() {
                @Override
                public void playbackFinished(PlaybackEvent event) {
                    pausedOnFrame[0] = event.getFrame();
                }
            });



            player.play(pausedOnFrame[0], Integer.MAX_VALUE);


        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }

    }

    static void stopMp3() {

        player.close();

    }

}
