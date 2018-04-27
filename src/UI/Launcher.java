package UI;

import VirtualHarp.VirtualHarpController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    protected static void openVirtualHarp() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.setLocation(VirtualHarpController.class.getResource("VirtualHarp.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 620, 330);
            Stage stage = new Stage();
            stage.setTitle("Virtual Laser Harp");
            stage.setScene(scene);
            VirtualHarpController.setVirtualHarpStage(stage);
            VirtualHarpController.init();
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../MainWindow.fxml"));
        primaryStage.setTitle("Laser Harp Software");
        primaryStage.setScene(new Scene(root, 700, 400));
        Controller.primaryStage = primaryStage;
        Controller.init();
        primaryStage.show();
        openVirtualHarp();
    }

    @Override
    public void stop() {
        System.exit(0);
    }

}
