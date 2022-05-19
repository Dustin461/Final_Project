package Main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Main extends Application {
    public static Stage stage;
    @Override
    public void start(Stage PrimaryStage) throws Exception {
        Main.stage = new Stage();
        run(PrimaryStage);

    }

    void run(Stage Stage) throws Exception {
        // Extra function using button F11 to open/exit fullscreen
        Stage.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (KeyCode.F11.equals(e.getCode())) {
                Stage.setFullScreen(!Stage.isFullScreen());
            }
        });
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HomeScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 640);
        Stage.setTitle("NEWS!");
        Stage.setScene(scene);
        Stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
