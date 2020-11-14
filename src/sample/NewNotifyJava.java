package sample;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import kotlin.jvm.Throws;

import java.io.File;

import java.net.MalformedURLException;

public class NewNotifyJava {

    enum Position {
        RIGHT_BOTTOM,
        RIGHT_TOP,
        LEFT_BOTTOM,
        LEFT_TOP
    }

    enum Border {
        SQUARE,
        CIRCLE
    }

    private Position pos = Position.RIGHT_BOTTOM;
    private String title;
    private String message = "MESSAGE";
    private String appName = "app_name";

    private Border iconBorder = Border.SQUARE;
    private String iconPathURL;

    private String textColor = "#FFFFFF";
    private String backgroundColor = "#000000";
    private double backgroundOpacity = 0.9;

    private int waitTime = 3000;

    public static class Builder {
        private final NewNotifyJava notify_config;

        public Builder() {
            notify_config = new NewNotifyJava();
        }

        public Builder title(String title) {
            notify_config.title = title;
            return this;
        }

        public Builder message(String message) {
            notify_config.message = message;
            return this;
        }

        public Builder appName(String app_name) {
            notify_config.appName = app_name;
            return this;
        }

        public Builder textColor(String textColor) {
            notify_config.textColor = textColor;
            return this;
        }

        public Builder backgroundColor(String backgroundColor) {
            notify_config.backgroundColor = backgroundColor;
            return this;
        }

        public Builder backgroundOpacity(double backgroundOpacity) {
            notify_config.backgroundOpacity = backgroundOpacity;
            return this;
        }

        public Builder position(Position pos) {
            notify_config.pos = pos;
            return this;
        }

        public Builder iconBorder(Border border) {
            notify_config.iconBorder = border;
            return this;
        }

        public Builder iconPathURL(String iconPathURL) {
            notify_config.iconPathURL = iconPathURL;
            return this;
        }


        public Builder waitTime(int waiting_time) {
            notify_config.waitTime = waiting_time;
            return this;
        }

        HBox content = new HBox();
        VBox msgLayout = new VBox();

        double defWidth = 300.0;
        double defHeight = 140.0;


        private Stage popup;
        public Stage create() {
            build();

            Rectangle2D screenRect = Screen.getPrimary().getBounds();
            double shift = 10.0;

            switch (notify_config.pos) {
                case LEFT_TOP:
                    popup.setX(shift);
                    popup.setY(shift);
                    break;
                case RIGHT_TOP:
                    popup.setX(screenRect.getWidth() - defWidth - shift);
                    popup.setY(shift);
                    break;
                case LEFT_BOTTOM:
                    popup.setX(shift);
                    popup.setY(screenRect.getHeight() - defHeight - shift);
                    break;
                case RIGHT_BOTTOM:
                    popup.setX(screenRect.getWidth() - defWidth - shift);
                    popup.setY(screenRect.getHeight() - defHeight - shift);
                    break;
            }

            @NotNull
            Task task = new Task() {
                @Override
                //@Throws(exceptionClasses = InterruptedException.class)
                protected Object call() throws Exception {
                    Thread.sleep(notify_config.waitTime);
                    cloaseAnim();
                    return null;
                }
            };
            new Thread(task).start();
            popup.addEventFilter(MouseEvent.MOUSE_PRESSED, MouseEvent -> cloaseAnim());


            popup.setScene(new Scene(content));
            ////// че тут ставить????
            //popup.initOwner(primaryStage);
            popup.initStyle(StageStyle.TRANSPARENT);
            popup.setOpacity(notify_config.backgroundOpacity);
            popup.show();
            openAnim();
            return popup;
        }

        private void build() {
            content.setPrefSize(defWidth, defHeight);
            content.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
            content.setSpacing(10.0);
            content.setStyle("-fx-background-color:" + notify_config.backgroundColor);

            String path = notify_config.iconPathURL;

            if (!notify_config.iconPathURL.isEmpty()) {
                if (!notify_config.iconPathURL.startsWith("http")) {
                    try {
                        path = new File(notify_config.iconPathURL).toURI().toURL().toString();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                Circle iconBorderCircle;
                Rectangle iconBorderRectangle;
                if (notify_config.iconBorder == Border.CIRCLE) {
                    iconBorderCircle = new Circle(defHeight / 2,
                            defHeight / 2,
                            defHeight / 2);
                    iconBorderCircle.setFill(new ImagePattern(new Image(path)));
                    content.getChildren().add(iconBorderCircle);
                } else {
                    iconBorderRectangle = new Rectangle(defHeight / 2,
                            defHeight / 2,
                            defWidth,
                            defHeight);
                    iconBorderRectangle.setFill(new ImagePattern(new Image(path)));
                    content.getChildren().add(iconBorderRectangle);
                }
            }


            Label title = new Label(notify_config.title);
            title.setFont(Font.font(24));
            title.setStyle("-fx-font-weight: bold; -fx-text-fill:" + notify_config.textColor);

            Label message = new Label(notify_config.message);
            message.setFont(Font.font(20));
            message.setStyle("-fx-text-fill:" + notify_config.textColor);

            Label app = new Label(notify_config.appName);
            message.setFont(Font.font(16));
            app.setStyle("-fx-text-fill:" + notify_config.textColor);

            msgLayout.getChildren().addAll(title, message, app);
            content.getChildren().add(msgLayout);
        }

        private void openAnim() {
            FadeTransition ft = new FadeTransition(Duration.millis(1500.0), content);
            ft.setFromValue(0);
            ft.setToValue(notify_config.backgroundOpacity);
            ft.setCycleCount(1);
            ft.play();
        }

        private void cloaseAnim() {
            FadeTransition ft = new FadeTransition(Duration.millis(1500.0), content);
            ft.setFromValue(notify_config.backgroundOpacity);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.setOnFinished(event -> {
                System.out.println("close");
                popup.close();
            });
            ft.play();

        }

    }
}