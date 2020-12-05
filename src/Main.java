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
                .messageLinkBrowser("всем ", "http://developer.alexanderklimov.ru/android/java/arraylist.php")
                .message("тут демонстрация")
                .messageLinkBrowser("ссылок ", "https://www.google.com/search?q=rfr+cltkfnm+arraylist+%D0%BD%D0%B5%D1%81%D0%BA%D0%BE%D0%BB%D1%8C%D0%BA%D0%B8%D1%85+%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%B5%D0%BD%D0%BD%D1%8B%D1%85&oq=rfr+cltkfnm+arraylist+%D0%BD%D0%B5%D1%81%D0%BA%D0%BE%D0%BB%D1%8C%D0%BA%D0%B8%D1%85+%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%B5%D0%BD%D0%BD%D1%8B%D1%85&aqs=chrome..69i57.15534j0j7&sourceid=chrome&ie=UTF-8")
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
