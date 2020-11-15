package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        NewNotifyJava.CustomBuilder notifyJava = new NewNotifyJava.CustomBuilder(primaryStage);
        notifyJava.title("Jane")
                .message("Doe")
                .appName("test")
                .addAttributionText("Vue SMS")
                .iconBorder(NewNotifyJava.Border.SQUARE)
                .position(NewNotifyJava.Position.RIGHT_BOTTOM)
                .waitTime(NewNotifyJava.Durability.NEVER)
                .setPositiveButton("AGREE", event -> System.out.println("positive"))
                .setNegativeButton("CANCEL", event -> System.out.println("negative"))
                .iconPathURL("https://softboxmarket.com/images/thumbnails/618/540/detailed/3/official-bts-wings-2nd-album-cd-poster-po_00.jpg")
                .show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
