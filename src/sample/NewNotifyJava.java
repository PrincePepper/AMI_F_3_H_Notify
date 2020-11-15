package sample;

import com.sun.javafx.scene.control.skin.ColorPalette;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
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

    enum Durability {
        SHORT,
        LONG,
        NEVER
    }

    enum Animation {
        TRANSPARENT,
        DISPLAY
    }

    private Position pos = Position.RIGHT_BOTTOM;
    private String title = "";
    private String message = "";
    private String appName = "";
    private String attributiontext = "";
    private Border iconBorder = Border.CIRCLE;
    private String iconPathURL = "";

    private String textColorTitle = "#FFFFFF";
    private String textColorMessage = "#b0b0b0";
    private String backgroundColor = "#1c1c1c";
    private double backgroundOpacity = 1;

    private Durability waitTime = Durability.SHORT;

    private String mPositiveButtonText = "";
    private String mNegativeButtonText = "";

    private EventHandler<ActionEvent> mPositiveButtonListener;
    private EventHandler<ActionEvent> mNegativeButtonListener;

    private String AddToastInputId;

    public static class CustomBuilder {
        private final NewNotifyJava notify_config;
        private final Stage popup = new Stage();
        private final Stage primaryStage;

        public CustomBuilder(Stage primaryStage) {
            notify_config = new NewNotifyJava();
            this.primaryStage = primaryStage;
        }

        public CustomBuilder title(String title) {
            notify_config.title = title;
            return this;
        }

        public CustomBuilder message(String message) {
            notify_config.message = message;
            return this;
        }

        public CustomBuilder appName(String app_name) {
            notify_config.appName = app_name;
            return this;
        }

        public CustomBuilder addAttributionText(String attribution_name) {
            notify_config.attributiontext = " • " + attribution_name;
            return this;
        }

        public CustomBuilder textColorTitle(String textColor) {
            notify_config.textColorTitle = textColor;
            return this;
        }

        public CustomBuilder textColorMessage(String textColor) {
            notify_config.textColorMessage = textColor;
            return this;
        }

        public CustomBuilder backgroundColor(String backgroundColor) {
            notify_config.backgroundColor = backgroundColor;
            return this;
        }

        public CustomBuilder backgroundOpacity(double backgroundOpacity) {
            notify_config.backgroundOpacity = backgroundOpacity;
            return this;
        }

        public CustomBuilder position(Position pos) {
            notify_config.pos = pos;
            return this;
        }

        public CustomBuilder iconBorder(Border border) {
            notify_config.iconBorder = border;
            return this;
        }

        public CustomBuilder iconPathURL(String iconPathURL) {
            notify_config.iconPathURL = iconPathURL;
            return this;
        }


        public CustomBuilder waitTime(Durability waiting_time) {
            notify_config.waitTime = waiting_time;
            return this;
        }


        @FunctionalInterface
        public interface EventHandler extends javafx.event.EventHandler<ActionEvent> {
            /**
             * Invoked when a specific event of the type for which this handler is
             * registered happens.
             *
             * @param event the event which occurred
             */
            @Override
            void handle(ActionEvent event);
        }


        public CustomBuilder setPositiveButton(String name, final EventHandler listener) {
            notify_config.mPositiveButtonText = name;
            notify_config.mPositiveButtonListener = listener;
            return this;
        }

        public CustomBuilder setNegativeButton(String name, final EventHandler listener) {
            notify_config.mNegativeButtonText = name;
            notify_config.mNegativeButtonListener = listener;
            return this;
        }

//        interface ToastSelectionBox<DefaultSelectionBoxItemId extends String>{
//
//        }
//
//        public CustomBuilder addToastInput(final ToastSelectionBox selectionBox) {
//            return this;
//        }

        VBox content = new VBox();
        HBox content_visual = new HBox();
        VBox msgLayout = new VBox();
        HBox content_actions = new HBox();

        double defWidth = 350;
        double defHeightVisual = 120;
        double defHeightActions = 40;

        public void show() {
            Rectangle2D screenRect = Screen.getPrimary().getBounds();

            build();

            //height of the task bar
            JFrame jFrame = new JFrame();
            java.awt.Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(jFrame.getGraphicsConfiguration());
            int taskBarSize = scnMax.bottom;
            //отступ от края экрана
            double shift = 10;

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
                    popup.setY(screenRect.getHeight() - defHeightVisual - defHeightActions - taskBarSize - shift * 2);
                    break;
                case RIGHT_BOTTOM:
                    popup.setX(screenRect.getWidth() - defWidth - shift);
                    popup.setY(screenRect.getHeight() - defHeightVisual - defHeightActions - taskBarSize - shift * 2);
                    break;
            }


            Task<Object> task = new Task<Object>() {
                @Override
                protected Object call() throws Exception {
                    if (notify_config.waitTime != Durability.NEVER) {
                        if (notify_config.waitTime == Durability.SHORT)
                            Thread.sleep(5000);
                        else if (notify_config.waitTime == Durability.LONG)
                            Thread.sleep(25000);
                        closeAnim();
                    }
                    return null;
                }
            };
            Thread th = new Thread(task);
            th.start();

            content_actions.addEventFilter(MouseEvent.MOUSE_PRESSED, MouseEvent -> closeAnim());
            content.addEventFilter(MouseEvent.MOUSE_ENTERED, MouseEvent -> popup.setOpacity(1));
            content.addEventFilter(MouseEvent.MOUSE_EXITED, MouseEvent -> popup.setOpacity(notify_config.backgroundOpacity));

            Scene scene = new Scene(content);
            scene.setFill(Color.TRANSPARENT);
            popup.setScene(scene);
            popup.setAlwaysOnTop(true);
            popup.initOwner(primaryStage);
            popup.initStyle(StageStyle.TRANSPARENT);
            popup.setOpacity(notify_config.backgroundOpacity);
            popup.show();
            openAnim();
        }

        private void build() {
            content.setStyle("-fx-background-color:" + notify_config.backgroundColor);
            content_visual.setPrefSize(defWidth, defHeightVisual);
            content_visual.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
            content_visual.setSpacing(10.0);

            ImageAdd();

            LabelAdd();

            content_actions.setPrefSize(defWidth, defHeightActions);
            content_actions.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
            content_actions.setSpacing(10.0);

            ButtonAdd();
        }

        private void ImageAdd() {
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
                Image backgroundImage = new Image(path);

                if (notify_config.iconBorder == Border.CIRCLE) {
                    iconBorderCircle = new Circle(defHeightVisual / 2,
                            defHeightVisual / 2,
                            defHeightVisual / 4);
                    iconBorderCircle.setFill(new ImagePattern(backgroundImage, 1, 1, 1, 1, true));
                    content_visual.getChildren().add(iconBorderCircle);
                } else {
                    iconBorderRectangle = new Rectangle(0, 0,
                            defHeightVisual / 2,
                            defHeightVisual / 2);
                    iconBorderRectangle.setFill(new ImagePattern(backgroundImage, 0, 0, 1, 1, true));
                    content_visual.getChildren().add(iconBorderRectangle);
                }
            }
        }

        private void LabelAdd() {
            Label title = new Label(notify_config.title);
            title.setFont(Font.font(24));
            title.setStyle("-fx-font-weight: bold; -fx-text-fill:" + notify_config.textColorTitle);

            Label message = new Label(notify_config.message);
            message.setFont(Font.font(20));
            message.setStyle("-fx-text-fill:" + notify_config.textColorMessage);

            Label app = new Label(notify_config.appName + notify_config.attributiontext);
            app.setFont(Font.font(14));
            app.setStyle("-fx-text-fill:" + notify_config.textColorMessage);

            msgLayout.getChildren().addAll(title, message, app);
            content_visual.getChildren().add(msgLayout);
            content.getChildren().add(content_visual);
        }

        private void ButtonAdd() {
            if (notify_config.mPositiveButtonListener != null) {
                Button mPositiveButton = new Button(notify_config.mPositiveButtonText);
                mPositiveButton.setPrefSize(content_actions.getPrefWidth(), content_actions.getMaxWidth());
                mPositiveButton.setStyle("-fx-font-weight: bold; -fx-background-color: #626262; -fx-text-fill: white");
                mPositiveButton.setOnAction(notify_config.mPositiveButtonListener);

                content_actions.getChildren().add(mPositiveButton);
            }

            if (notify_config.mNegativeButtonListener != null) {
                Button mNegativeButton = new Button(notify_config.mNegativeButtonText);
                mNegativeButton.setPrefSize(content_actions.getPrefWidth(), content_actions.getMaxWidth());
                mNegativeButton.setStyle("-fx-font-weight: bold; -fx-background-color: #626262; -fx-text-fill: white");
                mNegativeButton.setOnAction(notify_config.mNegativeButtonListener);

                content_actions.getChildren().add(mNegativeButton);
            }
            content.getChildren().add(content_actions);
        }

        private void openAnim() {
            FadeTransition ft = new FadeTransition(Duration.millis(1500), content);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
        }

        private void closeAnim() {
            FadeTransition ft = new FadeTransition(Duration.millis(1500), content);
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