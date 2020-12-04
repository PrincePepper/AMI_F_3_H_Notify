import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        NewNotifyJava.Builder notifyJava = new NewNotifyJava.Builder(primaryStage);

        notifyJava.title("Jane")
                .message("Привет ")
                .messageLink("всем ", event -> System.out.println("111111111111"))
                .message("тут демонстрация")
                .messageLink("ссылок ", event -> System.out.println("222222222222"))
                .appName("test")
                .addAttributionText("Vue SMS")
                .iconBorder(NewNotifyJava.Border.CIRCLE)
                .position(NewNotifyJava.Position.RIGHT_BOTTOM)
                .waitTime(NewNotifyJava.Durability.NEVER)
                .backgroundOpacity(1)
                .addInputTextBox()

                .setComboBox("second", "first", "second", "third", "four", "five", "six")
                .setPositiveButton("AGREE", event -> {
                    System.out.println(notifyJava.getValueTextField());
                    System.out.println(notifyJava.getValueComboBox());
                })
                .setNegativeButton("CANCEL", event -> System.out.println("negative"))
                .iconPathURL("https://softboxmarket.com/images/thumbnails/618/540/detailed/3/official-bts-wings-2nd-album-cd-poster-po_00.jpg")
                .changeTransition(NewNotifyJava.Animation.DISPLAY)
                .build();

    }
}
