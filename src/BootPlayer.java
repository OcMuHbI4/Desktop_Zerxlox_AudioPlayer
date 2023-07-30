import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class BootPlayer extends JFrame implements ActionListener {

    //int threadCount = 0;
    static String selectedFilePath;
    JFileChooser chooseWindow = new JFileChooser();
    File selectedFile;
    Thread thread = new Thread(new Runner());
    JFrame mainPlayerWindow; //Окно плеера
    JButton playButton, chooseDirectionButton; //Кнопка воспроизведения
    BootPlayer(){

        mainPlayerWindow = new JFrame("Zerxlox Player");
        mainPlayerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPlayerWindow.setSize(500, 250);
        mainPlayerWindow.setLocationRelativeTo(null);

//        JTable trackList = new JTable();
//        trackList.setPreferredSize(new Dimension(100,300));

        chooseDirectionButton = new JButton("Выбрать файл");
        chooseDirectionButton.setSize(100,50);
        chooseDirectionButton.addActionListener(this);

        playButton = new JButton("Воспроизвести");
        playButton.setSize(100,50);
        playButton.addActionListener(this);

        JPanel jpnl = new JPanel();
        jpnl.add(chooseDirectionButton);
        jpnl.add(playButton);

        mainPlayerWindow.getContentPane().add(jpnl);
        mainPlayerWindow.pack();
        mainPlayerWindow.show();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton)
        {
            thread.start();
//            if (threadCount == 0) {
//
//            } else {
//                thread.interrupt();
//                thread.start();
//            }
        }

        if (e.getSource() == chooseDirectionButton)
        {
            int response = chooseWindow.showOpenDialog(this);
            if (response == JFileChooser.APPROVE_OPTION)
            {
            selectedFile = new File(chooseWindow.getSelectedFile().getAbsolutePath());
            selectedFilePath =  selectedFile.toString();
            }
        }
    }

    class Runner implements Runnable
    {
        @Override
        public void run(){
            if (selectedFilePath.trim().endsWith(".mp3")){
                PlaySoundMp3.playMp3(selectedFilePath.trim());
                //threadCount++;
            }

            if (selectedFilePath.trim().endsWith(".wav")){
                SoundWav.play(selectedFilePath.trim());
               // threadCount++;
            }
        }
    }
}


//        Boolean couldFindASong = false;
//
//        do {
//
//
//        songName=  JOptionPane.showInputDialog(null,
//                "Введите название песни с рабочего стола",
//                "Воспроизведение песни", -1);
//
//
//        if (!songName.trim().isEmpty()) {
//
//            songName = songName.trim() + ".wav";
//
//            couldFindASong = true;
//
//            play(songName);
//        }
//
//        } while (couldFindASong);

//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//
//                String songName= txtArea.getText();
//                songName = songName.trim() + ".wav";
//
//                Sound.play(songName);
//
//}


