package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        NewNotifyJava.Builder notifyJava = new NewNotifyJava.Builder(primaryStage);
        notifyJava.title("Jane")
                .message("Doe")
                .appName("test")
                .position(NewNotifyJava.Position.RIGHT_TOP)
                .iconPathURL("https://softboxmarket.com/images/thumbnails/618/540/detailed/3/official-bts-wings-2nd-album-cd-poster-po_00.jpg")
                .show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
