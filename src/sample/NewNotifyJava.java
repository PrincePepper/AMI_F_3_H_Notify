package sample;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
import java.util.ArrayList;
import java.util.Collections;

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
    private String title;
    private String message;
    private String appName;
    private String attributiontext;
    private Border iconBorder = Border.CIRCLE;
    private String iconPathURL = "";
    private boolean changeTransition = false;


    private String textColorTitle = "#FFFFFF";
    private String textColorMessage = "#b0b0b0";
    private String backgroundColor = "#1c1c1c";
    private double backgroundOpacity = 1;

    private Durability waitTime = Durability.SHORT;

    private String typeText_TextInput = "";
    private String backgroundText_TextInput = "";

    private String mPositiveButtonText;
    private String mNegativeButtonText;

    private EventHandler<ActionEvent> mPositiveButtonListener;
    private EventHandler<ActionEvent> mNegativeButtonListener;

    public static class CustomBuilder {
        private final NewNotifyJava notify_config;
        private final Stage popup = new Stage();
        private final Stage primaryStage;

        private static final ArrayList<String> arrayListComboBox = new ArrayList<>();
        private final VBox content = new VBox();
        private final HBox content_visual = new HBox();
        private final VBox msgLayout = new VBox();
        private final HBox content_actions = new HBox();
        private final double defWidth = 350;
        private String main_text;


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

        public CustomBuilder changeTransition(boolean change_transition) {
            notify_config.changeTransition = change_transition;
            return this;
        }

        public CustomBuilder addInputTextBox(String type_text, String back_text) {
            notify_config.typeText_TextInput = type_text;
            notify_config.backgroundText_TextInput = back_text;
            return this;
        }

        public CustomBuilder setComboBox(String maintext, String... var) {
            Collections.addAll(arrayListComboBox, var);
            main_text = maintext;
            return this;
        }

        public CustomBuilder setPositiveButton(String name, final EventHandler<ActionEvent> listener) {
            notify_config.mPositiveButtonText = name;
            notify_config.mPositiveButtonListener = listener;
            return this;
        }

        public CustomBuilder setNegativeButton(String name, final EventHandler<ActionEvent> listener) {
            notify_config.mNegativeButtonText = name;
            notify_config.mNegativeButtonListener = listener;
            return this;
        }

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
                    popup.setY(screenRect.getHeight() - taskBarSize - shift);
                    break;
                case RIGHT_BOTTOM:
                    popup.setX(screenRect.getWidth() - defWidth - shift);
                    popup.setY(screenRect.getHeight() - taskBarSize - shift - 300);
                    break;
            }


            Task<Object> task = new Task<>() {
                @Override
                protected Object call() throws Exception {
                    System.out.println("111111111");
                    System.out.println("2222222");
                    if (notify_config.waitTime != Durability.NEVER) {
                        if (notify_config.waitTime == Durability.SHORT)
                            Thread.sleep(5000);
                        else if (notify_config.waitTime == Durability.LONG)
                            Thread.sleep(25000);
                        closeAnim(notify_config.changeTransition);
                    }
                    return null;
                }
            };
            Thread th1 = new Thread(task);
            th1.start();

            content_actions.addEventFilter(MouseEvent.MOUSE_PRESSED, MouseEvent -> closeAnim(notify_config.changeTransition));
            content.addEventFilter(MouseEvent.MOUSE_ENTERED, MouseEvent -> popup.setOpacity(1));
            content.addEventFilter(MouseEvent.MOUSE_EXITED, MouseEvent -> popup.setOpacity(notify_config.backgroundOpacity));

            Scene scene = new Scene(content);
            scene.setFill(Color.TRANSPARENT);

            popup.setScene(scene);
            popup.setWidth(defWidth);
            popup.setAlwaysOnTop(true);
            popup.initOwner(primaryStage);
            popup.initStyle(StageStyle.TRANSPARENT);
            popup.setOpacity(notify_config.backgroundOpacity);
            popup.show();
            openAnim(notify_config.changeTransition);
        }

        private void build() {
            content.setStyle("-fx-background-color:" + notify_config.backgroundColor);
            content.setPrefSize(0, 0);
            content.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
            content_visual.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
            content_visual.setSpacing(10.0);

            ImageAdd();
            LabelAdd();

            textFieldAdd();
            if (!arrayListComboBox.isEmpty())
                comboBox();

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
                    iconBorderCircle = new Circle(50, 50, 25);
                    iconBorderCircle.setFill(new ImagePattern(backgroundImage, 1, 1, 1, 1, true));
                    content_visual.getChildren().add(iconBorderCircle);
                } else {
                    iconBorderRectangle = new Rectangle(0, 0, 50, 50);
                    iconBorderRectangle.setFill(new ImagePattern(backgroundImage, 0, 0, 1, 1, true));
                    content_visual.getChildren().add(iconBorderRectangle);
                }
            }
        }

        private void LabelAdd() {
            if (notify_config.title != null) {
                Label title = new Label(notify_config.title);
                title.setFont(Font.font(24));
                title.setStyle("-fx-font-weight: bold; -fx-text-fill:" + notify_config.textColorTitle);
                msgLayout.getChildren().add(title);
            }
            if (notify_config.message != null) {
                Label message = new Label(notify_config.message);
                message.setFont(Font.font(20));
                message.setStyle("-fx-text-fill:" + notify_config.textColorMessage);
                msgLayout.getChildren().add(message);
            }
            if (notify_config.appName != null) {
                Label app = new Label(notify_config.appName + notify_config.attributiontext);
                app.setFont(Font.font(14));
                app.setStyle("-fx-text-fill:" + notify_config.textColorMessage);
                msgLayout.getChildren().add(app);
            }
            content_visual.getChildren().add(msgLayout);
            content.getChildren().add(content_visual);
        }


        private void textFieldAdd() {
            if (notify_config.typeText_TextInput.equals("pswd")) {
                PasswordField textPassword = new PasswordField();
                textPassword.setPadding(new Insets(5, 5, 5, 5));
                textPassword.setAccessibleText(notify_config.backgroundText_TextInput);
                content.getChildren().add(textPassword);
            } else {
                TextField textField = new TextField();
                textField.setColumns(11);
            }

        }

        private void comboBox() {
            VBox temp = new VBox();
            if (main_text == null)
                main_text = arrayListComboBox.get(0);
//            ObservableList<String> type = FXCollections.observableArrayList(arrayListComboBox);
            final ComboBox<String> pullBox = new ComboBox<>();
            pullBox.getItems().addAll(arrayListComboBox);
            pullBox.setVisibleRowCount(5);
            pullBox.setValue(main_text); // устанавливаем выбранный элемент по умолчанию
            pullBox.setPrefWidth(defWidth);
            pullBox.setStyle("-fx-font-weight: bold; -fx-background-color: #ffffff; -fx-text-fill: #000000");
            pullBox.setPadding(new Insets(0, 5, 0, 5));
            new AutoCompleteComboBoxListener(pullBox);
            content.getChildren().addAll(pullBox);
        }

        private void ButtonAdd() {
            content_actions.setPrefWidth(defWidth);
            content_actions.setSpacing(10.0);
            content_actions.setPadding(new Insets(5, 5, 10, 5));

            if (notify_config.mPositiveButtonListener != null) {
                Button mPositiveButton = new Button(notify_config.mPositiveButtonText);
                mPositiveButton.setPrefWidth(content_actions.getPrefWidth());
                mPositiveButton.setStyle("-fx-font-weight: bold; -fx-background-color: #626262; -fx-text-fill: white");
                mPositiveButton.setOnAction(notify_config.mPositiveButtonListener);
                content_actions.getChildren().add(mPositiveButton);
            }

            if (notify_config.mNegativeButtonListener != null) {
                Button mNegativeButton = new Button(notify_config.mNegativeButtonText);
                mNegativeButton.setPrefWidth(content_actions.getPrefWidth());
                mNegativeButton.setStyle("-fx-font-weight: bold; -fx-background-color: #626262; -fx-text-fill: white");
                mNegativeButton.setOnAction(notify_config.mNegativeButtonListener);
                content_actions.getChildren().add(mNegativeButton);
            }


            content.getChildren().add(content_actions);
        }

        private void openAnim(boolean transition) {
            if (transition) {
                TranslateTransition tt = new TranslateTransition(Duration.millis(600), content);
                if (notify_config.pos == Position.RIGHT_BOTTOM || notify_config.pos == Position.RIGHT_TOP) {
                    tt.setByX(-defWidth);
                    tt.setFromX(defWidth);
                } else {
                    tt.setByX(defWidth);
                    tt.setFromX(-defWidth);
                }
                tt.play();
            } else {
                FadeTransition ft = new FadeTransition(Duration.millis(600), content);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
            }

        }

        private void closeAnim(boolean transition) {
            if (transition) {
                TranslateTransition tt = new TranslateTransition(Duration.millis(600), content);
                if (notify_config.pos == Position.RIGHT_BOTTOM || notify_config.pos == Position.RIGHT_TOP) {
                    tt.setByX(defWidth);
                } else {
                    tt.setByX(-defWidth);

                }
                tt.setOnFinished(event -> {
                    System.out.println("close");
                    popup.close();
                });
                tt.play();
            } else {
                FadeTransition ft = new FadeTransition(Duration.millis(600), content);
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

        public static class AutoCompleteComboBoxListener implements EventHandler<KeyEvent> {

            private final ComboBox comboBox;
            private final ObservableList<String> data;
            private boolean moveCaretToPos = false;
            private int caretPos;

            public AutoCompleteComboBoxListener(final ComboBox<String> comboBox) {
                this.comboBox = comboBox;
                StringBuilder sb = new StringBuilder();
                data = comboBox.getItems();

                this.comboBox.setEditable(true);
                this.comboBox.setOnKeyPressed(t -> comboBox.hide());
                this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
                comboBox.getEditor().setStyle("-fx-font-weight: bold; -fx-background-color: #ffffff; -fx-text-fill: #000000");
            }

            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.UP) {
                    caretPos = -1;
                    moveCaret(comboBox.getEditor().getText().length());
                    return;
                } else if (event.getCode() == KeyCode.DOWN) {
                    if (!comboBox.isShowing()) {
                        comboBox.show();
                    }
                    caretPos = -1;
                    moveCaret(comboBox.getEditor().getText().length());
                    return;
                } else if (event.getCode() == KeyCode.BACK_SPACE) {
                    moveCaretToPos = true;
                    caretPos = comboBox.getEditor().getCaretPosition();
                } else if (event.getCode() == KeyCode.DELETE) {
                    moveCaretToPos = true;
                    caretPos = comboBox.getEditor().getCaretPosition();
                }

                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                        || event.isControlDown() || event.getCode() == KeyCode.HOME
                        || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
                    return;
                }

                ObservableList list = FXCollections.observableArrayList();
                for (Object datum : data) {
                    if (datum.toString().toLowerCase().startsWith(
                            AutoCompleteComboBoxListener.this.comboBox
                                    .getEditor().getText().toLowerCase())) {
                        list.add(datum);
                    }
                }
                String t = comboBox.getEditor().getText();

                comboBox.setItems(list);
                comboBox.getEditor().setText(t);
                if (!moveCaretToPos) {
                    caretPos = -1;
                }
                moveCaret(t.length());
                if (!list.isEmpty()) {
                    comboBox.show();
                }
            }

            private void moveCaret(int textLength) {
                if (caretPos == -1) {
                    comboBox.getEditor().positionCaret(textLength);
                } else {
                    comboBox.getEditor().positionCaret(caretPos);
                }
                moveCaretToPos = false;
            }

        }
    }
}