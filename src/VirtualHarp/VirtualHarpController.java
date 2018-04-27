package VirtualHarp;

import Media.Instrument;
import UI.Controller;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;

public class VirtualHarpController {
    @FXML
    private static Stage virtualHarpStage;
    @FXML
    private static ArrayList<Line> gdgZdarLasers = new ArrayList<>();
    @FXML
    private static ArrayList<Line> basicHarpLasers = new ArrayList<>();

    private static Instrument virtualInstrument = new Instrument();

    private static int numberOfLasers = 26;

    public static void setVirtualHarpStage(Stage virtualHarpStage) {
        VirtualHarpController.virtualHarpStage = virtualHarpStage;
    }

    public static void init() {
        gdgZdarHarpInit();
        basicHarpInit();
    }

    private static void gdgZdarHarpInit() {
        float p1[] = {75.7f, 46f};
        float p2[] = {75.5f, 240f};
        float shift = 18.66f;
        Pane gdgZdarPane = (Pane) virtualHarpStage.getScene().lookup("#gdgZdarPane");
        for (int i = 0; i < numberOfLasers; i++) {
            Line laser = new Line(p1[0] + (i * shift), p1[1], p2[0] + (i * shift), p2[1]);
            laser.setStroke(Color.RED);
            laser.setStrokeWidth(3);
            laser.setOnMouseEntered(e -> cursorEnteredLaser(gdgZdarLasers, laser));
            laser.setOnMouseExited(e -> cursorExitedLaser(laser));
            gdgZdarPane.getChildren().add(laser);
            gdgZdarLasers.add(laser);
        }
    }

    private static void basicHarpInit() {
        float p1[] = {310f, 240f};
        Pane basicHarpPane = (Pane) virtualHarpStage.getScene().lookup("#basicHarpPane");
        for (int i = 0; i < numberOfLasers; i++) {
            Line laser = new Line(p1[0], p1[1], 10 + (i * virtualHarpStage.getScene().getWidth() / numberOfLasers), 0);
            laser.setStroke(Color.RED);
            laser.setStrokeWidth(3);
            laser.setOnMouseEntered(e -> cursorEnteredLaser(basicHarpLasers, laser));
            laser.setOnMouseExited(e -> cursorExitedLaser(laser));
            basicHarpPane.getChildren().add(laser);
            basicHarpLasers.add(laser);
        }
        virtualHarpStage.getScene().lookup("#basicHarpImageView").toFront();
    }

    private static void cursorEnteredLaser(ArrayList<Line> listOfLasers, Line laser) {
        if (laser.equals(listOfLasers.get(0))) {
            if (Controller.selectBack()) {
                laser.setStroke(Color.YELLOW);
            }
        } else if (laser.equals(listOfLasers.get(numberOfLasers - 1))) {
            if (Controller.selectNext()) {
                laser.setStroke(Color.YELLOW);
            }
        } else {
            virtualInstrument.play(Controller.getSelectedLibrary(), virtualInstrument.getTones()[listOfLasers.indexOf(laser) - 1]);
            laser.setStroke(Color.GREEN);
        }
    }

    private static void cursorExitedLaser(Line laser) {
        new Thread() {
            @Override
            public synchronized void start() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                laser.setStroke(Color.RED);
                this.interrupt();
            }
        }.start();
    }

}
