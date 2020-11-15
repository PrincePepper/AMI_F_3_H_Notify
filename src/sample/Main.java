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
                .iconBorder(NewNotifyJava.Border.CIRCLE)
                .position(NewNotifyJava.Position.RIGHT_BOTTOM)
                .waitTime(NewNotifyJava.Durability.NEVER)
                .backgroundOpacity(1)
                .setPositiveButton("AGREE", event -> System.out.println("positive"))
                .setNegativeButton("CANCEL", event -> System.out.println("negative"))
                .iconPathURL("https://softboxmarket.com/images/thumbnails/618/540/detailed/3/official-bts-wings-2nd-album-cd-poster-po_00.jpg")
//                .addToastInput(new NewNotifyJava.CustomBuilder.ToastSelectionBox("snoozeTime") {
//                    DefaultSelectionBoxItemId ="15",
//                    Items =
//                    {
//                        new ToastSelectionBoxItem("5", "5 minutes"),
//                                new ToastSelectionBoxItem("15", "15 minutes"),
//                                new ToastSelectionBoxItem("60", "1 hour"),
//                                new ToastSelectionBoxItem("240", "4 hours"),
//                                new ToastSelectionBoxItem("1440", "1 day")
//                    }
//                })
                .show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
