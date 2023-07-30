import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PlaySoundMp3 extends Thread {

    static void playMp3( String songName) {

        File soundFile = new File(BootPlayer.selectedFilePath);

        try {
            FileInputStream fis = new FileInputStream(soundFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            Player player = new Player(bis);
            player.play();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }

    }
}
