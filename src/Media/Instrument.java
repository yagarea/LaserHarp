package Media;

import UI.Controller;

public class Instrument {

    private final String[] tones = "C D E F G A H C1 D1 E1 F1 G1 A1 H1 C2 D2 E2 F2 G2 A2 H2 C3".split(" ");
    private Float volume = 0.9f;

    public void play(String selectedSoundLibrary, String tone) {
        String pathToSoundFile = Controller.getSoundSourceFolderPath() + "/" + selectedSoundLibrary + "/" + tone +
                "." + Controller.getSoundFileExtension();
        playFile(pathToSoundFile);
    }

    public void play(String tone) {
        play(Controller.getSelectedLibrary(), tone);
    }

    public void playFile(String filePath) {
        new Thread(new SoundPlayer(filePath, getVolume())).start();
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volumeDecibels) {
        this.volume = volumeDecibels;
    }

    public String[] getTones() {
        return tones;
    }

}
