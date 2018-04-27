package UI;

import FileManagement.SoundScanner;
import Media.Instrument;
import Media.LinkOpener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    @FXML
    public static Stage primaryStage;
    @FXML
    private static ListView<String> listOfSoundLibraries;
    @FXML
    private static MenuBar menuBar;
    @FXML
    private static Slider volumeSlider;
    @FXML
    private static CheckMenuItem soundLibrarySwitchCheckMenuItem;

    private static Instrument instrument = new Instrument();

    private static String soundSourceFolderPath = "/Users/John/Laser-harp/sounds";
    private static String soundFileExtension = "wav";

    public static String getSoundFileExtension() {
        return soundFileExtension;
    }

    protected static void init() {
        listOfSoundLibraries = (ListView<String>) primaryStage.getScene().lookup("#soundLibraryListView");
        menuBar = (MenuBar) primaryStage.getScene().lookup("#menuBar");
        volumeSlider = (Slider) primaryStage.getScene().lookup("#volumeSlider");
        soundLibrarySwitchCheckMenuItem = new CheckMenuItem("Border lasers switch sound Libraries");
        soundLibrarySwitchCheckMenuItem.setSelected(true);
        menuBar.getMenus().get(1).getItems().add(soundLibrarySwitchCheckMenuItem);

        scanSoundFolder();
        menuBar.getMenus().get(0).getItems().get(0).setOnAction(e -> soundLibraryTest());
        menuBar.getMenus().get(0).getItems().get(1).setOnAction(e -> scanSoundFolder());
        menuBar.getMenus().get(0).getItems().get(2).setOnAction(e -> Launcher.openVirtualHarp());
        menuBar.getMenus().get(1).getItems().get(0).setOnAction(e -> setNewSoundSourceFolder());
        menuBar.getMenus().get(1).getItems().get(1).setOnAction(e -> setNewSoundFileExtension());
        menuBar.getMenus().get(2).getItems().get(0).setOnAction(e -> LinkOpener.open("https://github.com/gdgzdar/Laser-harp"));

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                System.out.println(new_val.floatValue() + "%");
                instrument.setVolume(new_val.floatValue() / 100);
            }
        });
    }

    private static void addSoundLibrary(String soundLibrary) {
        listOfSoundLibraries.getItems().add(soundLibrary);
    }

    public static String getSelectedLibrary() {
        return listOfSoundLibraries.getSelectionModel().getSelectedItem();
    }

    private static void soundLibraryTest() {
        (new Thread() {
            @Override
            public void run() {
                for (String tone : instrument.getTones()) {
                    try {
                        instrument.play(tone);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private static void scanSoundFolder() {
        listOfSoundLibraries.getItems().clear();
        for (File f : SoundScanner.getSoundSources()) {
            addSoundLibrary(f.getName());
            listOfSoundLibraries.getSelectionModel().selectLast();
        }
    }

    private static void setNewSoundSourceFolder() {
        String newSoundSourceFolderPath = SoundScanner.getNewSourceFolder().getPath();
        System.out.println(newSoundSourceFolderPath);
        soundSourceFolderPath = newSoundSourceFolderPath;
        scanSoundFolder();
    }

    private static void setNewSoundFileExtension() {
        String newSoundFileExtension = SoundScanner.setNewExtension();
        if (newSoundFileExtension != null) {
            soundFileExtension = newSoundFileExtension;
            scanSoundFolder();
        }
    }

    public static String getSoundSourceFolderPath() {
        return soundSourceFolderPath;
    }

    public static boolean selectBack() {
        if (listOfSoundLibraries.getSelectionModel().getSelectedIndex() != 0 && soundLibrarySwitchCheckMenuItem.isSelected()) {
            listOfSoundLibraries.getSelectionModel().select(listOfSoundLibraries.getSelectionModel().getSelectedIndex() - 1);
            return true;
        }
        return false;
    }

    public static boolean selectNext() {
        if (listOfSoundLibraries.getSelectionModel().getSelectedIndex() != listOfSoundLibraries.getItems().size() - 1 &&
                soundLibrarySwitchCheckMenuItem.isSelected()) {
            listOfSoundLibraries.getSelectionModel().select(listOfSoundLibraries.getSelectionModel().getSelectedIndex() + 1);
            return true;
        }
        return false;
    }

}
