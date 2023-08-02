import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class BootPlayer extends JFrame implements ActionListener {
    JPanel tablePanel = new JPanel();
    public static String selectedFilePath;
    JScrollPane scrollPaneForTrackList;

    boolean notInitTable = true;
    boolean canPlay = true;
    static String selectedCellPath;

    String[][] trackListDataString;
    String[] trackListColumns = new String[2];
    static String selectedDirectoryPath;
    JTable trackListTable;
    JFileChooser chooseWindow = new JFileChooser();
    File selectedDirectory;

    JFrame mainPlayerWindow; //Окно плеера
    JButton playButton, chooseDirectionButton, stopButton; //Кнопка воспроизведения

    void makeTrackListTable(String path){

        //Блок инициализации выбранной дериктории пользователем
        trackListColumns[0] = "Track_Name";
        trackListColumns[1] = "Track_Directory";
        File dir = new File(path); //path указывает на директорию
        File[] arrFiles = dir.listFiles();
        List<ArrayList<String>> trackListData = new ArrayList<>();

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



        if (notInitTable) {

            trackListTable = new JTable(trackListDataString, trackListColumns);
            scrollPaneForTrackList  = new JScrollPane(trackListTable);
            tablePanel = new JPanel(); //Панель с таблицей (трек-листом)
            tablePanel.add(scrollPaneForTrackList);
            mainPlayerWindow.getContentPane().add(tablePanel);

            notInitTable = false;
        } else {
            tablePanel.remove(0);
            scrollPaneForTrackList.remove(0);
            trackListTable = new JTable(trackListDataString, trackListColumns);
            scrollPaneForTrackList  = new JScrollPane(trackListTable);
            tablePanel.add(scrollPaneForTrackList);
            mainPlayerWindow.getContentPane().add(tablePanel);
        }
        mainPlayerWindow.pack();
        mainPlayerWindow.show();
    }
    BootPlayer() {

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
        mainPlayerWindow.setLocationRelativeTo(null);
        mainPlayerWindow.setLayout(new GridBagLayout());

        chooseDirectionButton = new JButton("Выбрать директорию");
        chooseDirectionButton.setSize(100,50);
        chooseDirectionButton.addActionListener(this);

        playButton = new JButton("Воспроизвести выбранный трек");
        playButton.setSize(100,50);
        playButton.addActionListener(this);

        stopButton = new JButton("Остановить");
        stopButton.setSize(100,50);
        stopButton.addActionListener(this);

        JPanel buttonsPanel = new JPanel(); //Панель с кнопками
        buttonsPanel.add(chooseDirectionButton);
        buttonsPanel.add(playButton);
        buttonsPanel.add(stopButton);

        mainPlayerWindow.getContentPane().add(buttonsPanel);
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

        if (e.getSource() == stopButton)
        {
            PlaySoundMp3.stopMp3();
            canPlay = true;
        }

        if ((e.getSource() == playButton & trackListTable.
                isCellSelected(trackListTable.getSelectedRow(),trackListTable.getSelectedColumn())))
        {
            if (!canPlay)
            {
                PlaySoundMp3.stopMp3();
                canPlay = true;
            }

            selectedCellPath =  trackListTable.getValueAt(trackListTable.getSelectedRow(),1).toString();

            Thread thread = new Thread(new Runner());
            thread.start();
            canPlay = false;
        }
    }

    class Runner implements Runnable
    {
        @Override
       synchronized public void run(){
            if (selectedCellPath.trim().endsWith(".mp3")){
                PlaySoundMp3.playMp3(selectedCellPath.trim());
            }

            if (selectedCellPath.trim().endsWith(".wav") /*|| selectedFilePath.trim().endsWith(".flac")*/ ){
                PlaySoundWav.play(selectedCellPath.trim());
            }
        }
    }
}


