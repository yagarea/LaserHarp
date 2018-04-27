package FileManagement;

import UI.Controller;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class SoundScanner {

    private static File[] getPotentialSoundSources(String rootDirectoryPath) {
        return new File(rootDirectoryPath).listFiles(File::isDirectory);
    }

    public static File[] getSoundSources(String rootDirectoryPath, String extension) {
        File[] potentialSoundSources = getPotentialSoundSources(rootDirectoryPath);
        ArrayList<File> ok = new ArrayList<>();
        for (File f : potentialSoundSources) {
            if (containsEveryTone(f, extension)) {
                ok.add(f);
            }
        }
        return ok.toArray(new File[ok.size()]);
    }

    public static File[] getSoundSources() {
        return getSoundSources(Controller.getSoundSourceFolderPath(), Controller.getSoundFileExtension());
    }

    private static boolean containsEveryTone(File input, String extension) {
        int count = 0;
        try {
            for (File f : input.listFiles()) {
                int i = f.getName().lastIndexOf('.');
                if (i > 0 && f.getName().substring(i + 1).equals(extension)) {
                    count++;
                }
            }
            return count >= 22;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static File getNewSourceFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        while (true) {
            File selectedDirectory = directoryChooser.showDialog(Controller.primaryStage);
            if (selectedDirectory != null) {
                return selectedDirectory.getAbsoluteFile();
            }
        }
    }

    public static String setNewExtension() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("mp3");
        choices.add("ogg");
        choices.add("wav");
        choices.add("au");
        choices.add("aiff");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(Controller.getSoundFileExtension(), choices);
        dialog.setTitle("Laser harp software");
        dialog.setHeaderText("Select a sound file extension");
        dialog.setContentText("Supported extensions:");

        Optional<String> result = dialog.showAndWait();
        return result.isPresent() ? result.get() : null;
    }
}
