import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;

public class NewNotifyJava {

    private final String title;
    private final String message;
    private final String appName;
    private final String attributiontext;
    private final String iconPathURL;
    private final String textColorTitle;
    private final String textColorMessage;
    private final String backgroundColor;
    private final double backgroundOpacity;
    private final String musicPathURL;

    public NewNotifyJava(Builder builder) {
        this.title = builder.title;
        this.message = builder.message;
        this.appName = builder.appName;
        this.attributiontext = builder.attributiontext;
        this.iconPathURL = builder.iconPathURL;
        this.textColorTitle = builder.textColorTitle;
        this.textColorMessage = builder.textColorMessage;
        this.backgroundColor = builder.backgroundColor;
        this.backgroundOpacity = builder.backgroundOpacity;
        this.musicPathURL = builder.musicPathURL;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getAppName() {
        return appName;
    }

    public String getAttributiontext() {
        return attributiontext;
    }

    public String getIconPathURL() {
        return iconPathURL;
    }

    public String getTextColorTitle() {
        return textColorTitle;
    }

    public String getTextColorMessage() {
        return textColorMessage;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public double getBackgroundOpacity() {
        return backgroundOpacity;
    }

    public enum Position {
        RIGHT_BOTTOM,
        RIGHT_TOP,
        LEFT_BOTTOM,
        LEFT_TOP
    }

    public enum Border {
        SQUARE,
        CIRCLE
    }

    public enum Durability {
        SHORT,
        LONG,
        NEVER
    }

    public enum Animation {
        TRANSPARENT,
        DISPLAY
    }

    public static class Builder {
        private static final ArrayList<String> arrayListComboBox = new ArrayList<>();
        final ComboBox<String> pullBox = new ComboBox<>();
        private final Stage popup = new Stage();
        private final Stage primaryStage;
        private final VBox content = new VBox();
        private final HBox content_visual = new HBox();
        private final VBox msgLayout = new VBox();
        private final VBox content_input = new VBox();
        private final HBox content_actions = new HBox();

        private final double defWidth = 350;

        private final String musicPathURL = "https://wav-library.net/sounds/0-0-1-17216-20";

        TextField textField = new TextField();
        private String newValueTextField;
        private Position pos = Position.RIGHT_BOTTOM;
        private String title;
        private String message;
        private String appName;
        private String attributiontext;
        private Border iconBorder = Border.CIRCLE;
        private String iconPathURL = "";
        private Animation changeTransition = Animation.TRANSPARENT;
        private String textColorTitle = "#FFFFFF";
        private String textColorMessage = "#b0b0b0";
        private String backgroundColor = "#1c1c1c";
        private double backgroundOpacity = 1;
        private Durability waitTime = Durability.SHORT;
        private Boolean addInputTextBox = false;
        private String mPositiveButtonText;
        private String mNegativeButtonText;
        private EventHandler<ActionEvent> positiveButtonListener;
        private EventHandler<ActionEvent> negativeButtonListener;
        private String main_text;

        public Builder(Stage primaryStage) {
            this.primaryStage = primaryStage;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder appName(String app_name) {
            this.appName = app_name;
            return this;
        }

        public Builder addAttributionText(String attribution_name) {
            this.attributiontext = " • " + attribution_name;
            return this;
        }

        public Builder textColorTitle(String textColor) {
            this.textColorTitle = textColor;
            return this;
        }

        public Builder textColorMessage(String textColor) {
            this.textColorMessage = textColor;
            return this;
        }

        public Builder backgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder backgroundOpacity(double backgroundOpacity) {
            this.backgroundOpacity = backgroundOpacity;
            return this;
        }

        public Builder position(Position pos) {
            this.pos = pos;
            return this;
        }

        public Builder iconBorder(Border border) {
            this.iconBorder = border;
            return this;
        }

        public Builder iconPathURL(String iconPathURL) {
            this.iconPathURL = iconPathURL;
            return this;
        }

        public Builder waitTime(Durability waiting_time) {
            this.waitTime = waiting_time;
            return this;
        }

        public Builder changeTransition(Animation change_transition) {
            this.changeTransition = change_transition;
            return this;
        }

        public Builder addInputTextBox() {
            this.addInputTextBox = true;
            return this;
        }

        public Builder setComboBox(String maintext, String... var) {
            Collections.addAll(arrayListComboBox, var);
            main_text = maintext;
            return this;
        }

        public Builder setPositiveButton(String name, final EventHandler<ActionEvent> listener) {
            this.mPositiveButtonText = name;
            this.positiveButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(String name, final EventHandler<ActionEvent> listener) {
            this.mNegativeButtonText = name;
            this.negativeButtonListener = listener;
            return this;
        }

        public String getValueComboBox() {
            return pullBox.getValue();
        }

        public String getValueTextField() {
            return newValueTextField;
        }

        public NewNotifyJava build() {
            Rectangle2D screenRect = Screen.getPrimary().getBounds();

            create_element();

            Task<Object> task = new Task<Object>() {
                @Override
                protected Object call() throws Exception {
                    Media sound = new Media(musicPathURL);
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                    if (waitTime != Durability.NEVER) {
                        if (waitTime == Durability.SHORT)
                            Thread.sleep(5000);
                        else if (waitTime == Durability.LONG)
                            Thread.sleep(25000);
                        closeAnim(changeTransition);
                    }
                    return null;
                }
            };

            Thread th1 = new Thread(task);
            th1.start();

            StringProperty heigth = new SimpleStringProperty();
            heigth.addListener((observable, oldValue, newValue) -> newValueTextField = newValue);
            textField.textProperty().bindBidirectional(heigth);
            content.addEventFilter(MouseEvent.MOUSE_ENTERED, MouseEvent -> popup.setOpacity(1));
            content.addEventFilter(MouseEvent.MOUSE_EXITED, MouseEvent -> popup.setOpacity(this.backgroundOpacity));

            Scene scene = new Scene(content);
            scene.setFill(Color.TRANSPARENT);

            popup.setScene(scene);
            popup.setWidth(defWidth);
            popup.setAlwaysOnTop(true);
            popup.initOwner(primaryStage);
            popup.initStyle(StageStyle.TRANSPARENT);
            popup.setOpacity(this.backgroundOpacity);
            popup.show();

            //height of the task bar
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice defaultScreenDevice = ge.getDefaultScreenDevice();
            GraphicsConfiguration defaultConfiguration = defaultScreenDevice.getDefaultConfiguration();
            java.awt.Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(defaultConfiguration);

            //отступ от края экрана
            double shift = 10;

            switch (this.pos) {
                case LEFT_TOP:
                    popup.setX(shift + screenInsets.left);
                    popup.setY(shift + screenInsets.top);
                    break;
                case RIGHT_TOP:
                    popup.setX(screenRect.getWidth() - defWidth - shift - screenInsets.right);
                    popup.setY(shift + screenInsets.top);
                    break;
                case LEFT_BOTTOM:
                    popup.setX(shift + screenInsets.left);
                    popup.setY(screenRect.getHeight() - screenInsets.bottom - shift - content.getHeight());
                    break;
                case RIGHT_BOTTOM:
                    popup.setX(screenRect.getWidth() - defWidth - shift - shift - screenInsets.right);
                    popup.setY(screenRect.getHeight() - screenInsets.bottom - shift - content.getHeight());
                    break;
            }

            openAnim(this.changeTransition);
            return new NewNotifyJava(this);
        }

        private void create_element() {
            content.setStyle("-fx-background-color:" + this.backgroundColor);
            content.setPadding(new Insets(5));
            content_visual.setPadding(new Insets(5));
            content_visual.setSpacing(10.0);

            ImageAdd();
            LabelAdd();

            content_input.setSpacing(10);
            content_input.setPadding(new Insets(5));
            if (addInputTextBox)
                textFieldAdd();
            if (!arrayListComboBox.isEmpty())
                comboBox();
            content.getChildren().add(content_input);
            ButtonAdd();
        }

        private void ImageAdd() {
            String path = this.iconPathURL;

            if (!this.iconPathURL.isEmpty()) {
                if (!this.iconPathURL.startsWith("http")) {
                    try {
                        path = new File(this.iconPathURL).toURI().toURL().toString();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                Circle iconBorderCircle;
                Rectangle iconBorderRectangle;
                Image backgroundImage = new Image(path);

                if (this.iconBorder == Border.CIRCLE) {
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
            if (this.title != null) {
                Label title = new Label(this.title);
                title.setFont(Font.font(24));
                title.setStyle("-fx-font-weight: bold; -fx-text-fill:" + this.textColorTitle);
                msgLayout.getChildren().add(title);
            }
            if (this.message != null) {
                Label message = new Label(this.message);
                message.setMaxWidth(defWidth - 100);
                message.setWrapText(true);
                message.setFont(Font.font(20));
                message.setStyle("-fx-text-fill:" + this.textColorMessage);
                msgLayout.getChildren().add(message);
            }
            if (this.appName != null) {
                Label app = new Label(this.appName + this.attributiontext);
                app.setFont(Font.font(14));
                app.setStyle("-fx-text-fill:" + this.textColorMessage);
                msgLayout.getChildren().add(app);
            }
            content_visual.getChildren().add(msgLayout);
            content.getChildren().add(content_visual);
        }

        private void textFieldAdd() {
            content_input.getChildren().add(textField);
        }

        private void comboBox() {

            pullBox.getItems().addAll(arrayListComboBox);
            new AutoCompleteComboBoxListener<>(pullBox);
            pullBox.setValue(main_text);
            pullBox.setVisibleRowCount(5);
            pullBox.setValue(main_text); // устанавливаем выбранный элемент по умолчанию
            pullBox.setPrefWidth(defWidth);
            pullBox.setStyle("-fx-font-weight: bold; -fx-background-color: #ffffff; -fx-text-fill: #000000");
            pullBox.setPadding(new Insets(0, 5, 0, 5));
            content_input.getChildren().addAll(pullBox);
        }

        private void ButtonAdd() {
            content_actions.setPrefWidth(defWidth);
            content_actions.setSpacing(10.0);
            content_actions.setPadding(new Insets(5));

            if (this.positiveButtonListener != null) {
                Button mPositiveButton = new Button(this.mPositiveButtonText);
                mPositiveButton.setPrefWidth(content_actions.getPrefWidth());
                mPositiveButton.setStyle("-fx-font-weight: bold; -fx-background-color: #626262; -fx-text-fill: white");
                mPositiveButton.setOnAction(this.positiveButtonListener);
                mPositiveButton.addEventFilter(MouseEvent.MOUSE_PRESSED, MouseEvent -> closeAnim(changeTransition));
                content_actions.getChildren().add(mPositiveButton);
            }

            if (this.negativeButtonListener != null) {
                Button mNegativeButton = new Button(this.mNegativeButtonText);
                mNegativeButton.setPrefWidth(content_actions.getPrefWidth());
                mNegativeButton.setStyle("-fx-font-weight: bold; -fx-background-color: #626262; -fx-text-fill: white");
                mNegativeButton.setOnAction(this.negativeButtonListener);
                mNegativeButton.addEventFilter(MouseEvent.MOUSE_PRESSED, MouseEvent -> closeAnim(changeTransition));
                content_actions.getChildren().add(mNegativeButton);
            }
            content.getChildren().add(content_actions);
        }

        private void openAnim(Animation transition) {
            if (transition == Animation.TRANSPARENT) {
                TranslateTransition tt = new TranslateTransition(Duration.millis(600), content);
                if (this.pos == Position.RIGHT_BOTTOM || this.pos == Position.RIGHT_TOP) {
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

        private void closeAnim(Animation transition) {
            if (transition == Animation.TRANSPARENT) {
                TranslateTransition tt = new TranslateTransition(Duration.millis(600), content);
                if (this.pos == Position.RIGHT_BOTTOM || this.pos == Position.RIGHT_TOP) {
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
                ft.setFromValue(this.backgroundOpacity);
                ft.setToValue(0);
                ft.setCycleCount(1);
                ft.setOnFinished(event -> {
                    System.out.println("close");
                    popup.close();
                });
                ft.play();
            }
        }

        private static class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

            private final ComboBox comboBox;

            private final ObservableList<T> data;
            private boolean moveCaretToPos = false;
            private int caretPos;

            public AutoCompleteComboBoxListener(final ComboBox comboBox) {
                this.comboBox = comboBox;
                data = comboBox.getItems();

                this.comboBox.setEditable(true);
                this.comboBox.setOnKeyPressed(t -> comboBox.hide());
                this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
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

                ObservableList<T> list = FXCollections.observableArrayList();
                for (T datum : data) {
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