import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class BootPlayer extends JFrame implements ActionListener {

    public static String selectedFilePath;
    JScrollPane scrollPaneForTrackList;
    List<ArrayList<String>> trackListData = new ArrayList<>();
    Thread thread = new Thread(new Runner());
    static String selectedCellPath;

    String[][] trackListDataString;
    String[] trackListColumns = new String[2];
    static String selectedDirectoryPath;
    JTable trackListTable;
    JFileChooser chooseWindow = new JFileChooser();
    File selectedDirectory;

    JFrame mainPlayerWindow; //Окно плеера
    JButton playButton, chooseDirectionButton; //Кнопка воспроизведения

    void makeTrackListTable(String path){

        //Блок инициализации выбранной дериктории пользователем
        trackListColumns[0] = "Track_Name";
        trackListColumns[1] = "Track_Directory";
        File dir = new File(path); //path указывает на директорию
        File[] arrFiles = dir.listFiles();

        for (int i = 0; i < arrFiles.length; i++) {
            File extProv = new File(arrFiles[i].getName());
            if (extProv.toString().endsWith(".mp3") || extProv.toString().endsWith(".wav")) {
                File nameOfCurrentFileFile = new File(arrFiles[i].getName());
                String nameOfCurrentFileString  = new String(nameOfCurrentFileFile.toString());
                String fullPathOfCurrentFileString  = new String(arrFiles[i].toString());
                ArrayList<String> trackListData1 = new ArrayList<>();

                trackListData1.add(nameOfCurrentFileString);
                trackListData1.add(fullPathOfCurrentFileString);
                trackListData.add(trackListData1);
            }
        }

        trackListDataString = new String[trackListData.size()][2];

        for (int i = 0; i < trackListData.size(); i++) {
            trackListDataString[i][0] = trackListData.get(i).get(0).toString();
            trackListDataString[i][1] = trackListData.get(i).get(1).toString();
        }

        trackListTable = new JTable(trackListDataString, trackListColumns);
        scrollPaneForTrackList  = new JScrollPane(trackListTable);

        JPanel tablePanel = new JPanel(); //Панель с таблицей (трек-листом)
        tablePanel.add(scrollPaneForTrackList);

        mainPlayerWindow.getContentPane().add(tablePanel);
        mainPlayerWindow.pack();
        mainPlayerWindow.show();
    }
    BootPlayer() {







        //Блок с настройкой фильтрации поиска в выборе файла
//        chooseWindow.setAcceptAllFileFilterUsed(false);
//
//        FileNameExtensionFilter filterMp3 = new FileNameExtensionFilter(".mp3", "mp3");
//        FileNameExtensionFilter filterWav = new FileNameExtensionFilter(".wav", "wav");
//        FileNameExtensionFilter filterAudio =
//                new FileNameExtensionFilter("Audio files", "mp3", "wav");
//
//        chooseWindow.addChoosableFileFilter(filterMp3);
//        chooseWindow.addChoosableFileFilter(filterWav);
//        chooseWindow.addChoosableFileFilter(filterAudio);

        chooseWindow.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory();
            }

            @Override
            public String getDescription() {
                return null;
            }
        });

        //Создание основного окна

        mainPlayerWindow = new JFrame("Zerxlox Player");
        mainPlayerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPlayerWindow.setSize(500, 500);
        mainPlayerWindow.setLocationRelativeTo(null);
        mainPlayerWindow.setLayout(new GridLayout());

        //Создание кнопок

        chooseDirectionButton = new JButton("Выбрать директорию");
        chooseDirectionButton.setSize(100,50);
        chooseDirectionButton.addActionListener(this);

        playButton = new JButton("Воспроизвести выбранный трек");
        playButton.setSize(100,50);
        playButton.addActionListener(this);

        //Создание таблицы
//        trackListTable = new JTable(trackListDataString, trackListColumns);
//        scrollPaneForTrackList  = new JScrollPane(trackListTable);


        //Создание панелей и расположение элементов на одну панель

        JPanel buttonsPanel = new JPanel(); //Панель с кнопками
        buttonsPanel.add(chooseDirectionButton);
        buttonsPanel.add(playButton);

//        JPanel tablePanel = new JPanel(); //Панель с таблицей (трек-листом)
//        tablePanel.add(scrollPaneForTrackList);

        mainPlayerWindow.getContentPane().add(buttonsPanel);
//        mainPlayerWindow.getContentPane().add(tablePanel);
        mainPlayerWindow.pack();
        mainPlayerWindow.show();

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == chooseDirectionButton)
        {
            File musicDirectory = new File("C:/Users/Zerxlox/Music");

            chooseWindow.setCurrentDirectory(musicDirectory);

            chooseWindow.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int response = chooseWindow.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION)
            {
            selectedDirectory = new File(chooseWindow.getSelectedFile().getAbsolutePath());

            selectedDirectoryPath =  selectedDirectory.toString();
            makeTrackListTable(selectedDirectoryPath);

            }
        }

        if (e.getSource() == playButton & trackListTable.
                isCellSelected(trackListTable.getSelectedRow(),trackListTable.getSelectedColumn()))
        {
            selectedCellPath =  trackListTable.getValueAt(trackListTable.getSelectedRow(),1).toString();

            thread.start();

        }
    }

    class Runner implements Runnable
    {
        @Override
        public void run(){
            if (selectedCellPath.trim().endsWith(".mp3")){
                PlaySoundMp3.playMp3(selectedCellPath.trim());
            }

            if (selectedCellPath.trim().endsWith(".wav") /*|| selectedFilePath.trim().endsWith(".flac")*/ ){
                PlaySoundWav.play(selectedCellPath.trim());
            }
        }
    }
}


