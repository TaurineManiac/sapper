package com.example.sapper;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class TopGamePanel {
    Label restartLabel;
    Button restartButton;
    Button mainMenuButton;
    Label timerLabel;
    Rectangle backgroundTimerRectangle;
    StackPane restartStackPane;
    StackPane timerStackPane;
    StackPane stackPaneOfPanel;
    GameDifficulty difficulty;
    Timer timer;
    MineField mineField;
    MainMenu mainMenu;

    public TopGamePanel() {
        Font minecraftFont = Font.loadFont(getClass().getResourceAsStream("/Minecraft.ttf"), 18);
        StackPane stackPane = new StackPane();
        restartLabel = new Label("Restart");
        restartLabel.setFont(minecraftFont);
        restartLabel.setTextAlignment(TextAlignment.CENTER);
        restartLabel.setMouseTransparent(true);
        restartButton = new Button();
        restartButton.setFont(minecraftFont);
        restartButton.setMinSize(100, 32);
        restartButton.setMaxSize(100, 32);
        restartStackPane = new StackPane();
        restartStackPane.getChildren().addAll(restartButton,restartLabel);

        mainMenuButton = new Button();

        //тут просто создаём таймер
        timer= new Timer();
        timer.setFont(minecraftFont);
        //тут задаём стилёк
        timerLabel = new Label();
        timerLabel.setFont(minecraftFont);
        timerLabel.setStyle("-fx-text-fill: rgba(144, 238, 144, 0.8);"); // светло-салатовый с прозрачностью
        timerLabel.textProperty().bind(
                timer.min.textProperty().concat(":").concat(timer.sec.textProperty())
        );

        backgroundTimerRectangle = new Rectangle(100, 32); // ширина и высота примерно как у кнопки
        backgroundTimerRectangle.setArcWidth(10);  // скругление краёв
        backgroundTimerRectangle.setArcHeight(10);
        backgroundTimerRectangle.setStyle("-fx-fill: darkgray; -fx-stroke: black; -fx-stroke-width: 1;");

        timerStackPane = new StackPane();
        timerStackPane.getChildren().addAll(backgroundTimerRectangle, timerLabel);
        timerStackPane.setMinSize(100, 32);
        timerStackPane.setMaxSize(100, 32);
        timerStackPane.setPrefSize(100, 32);

        mainMenuButton = new Button();
        mainMenuButton.setStyle("-fx-background-color: transparent; " +
                "-fx-border-color: black; " +
                "-fx-border-width: 1; " +
                "-fx-pref-width: 32; " +
                "-fx-pref-height: 32; " +
                "-fx-min-width: 32; " +
                "-fx-min-height: 32; " +
                "-fx-max-width: 32; " +
                "-fx-max-height: 32;");
        mainMenuButton.setFont(minecraftFont);
        Image flagImg = new Image(getClass().getResourceAsStream("/menu_lines_3.png"));
        ImageView iv = new ImageView(flagImg);
        iv.setFitWidth(30);
        iv.setFitHeight(30);
        mainMenuButton.setGraphic(iv);

        setEventToMainMenuButton();
        setEventToRestartButton();

        stackPaneOfPanel = new StackPane();
    }

    public void show(GameDifficulty difficulty) {
        this.difficulty = difficulty;
        setMouseTransparent(false);
        stackPaneOfPanel.getChildren().clear();
        stackPaneOfPanel.getChildren().add(getHBox());
    }

    public void hide(){
        setMouseTransparent(true);
        stackPaneOfPanel.getChildren().clear();
    }

    HBox getHBox(){
        HBox hBox = new HBox(20, timerStackPane,mainMenuButton,restartStackPane);
        hBox.setAlignment(Pos.CENTER);
        hBox.setStyle("-fx-padding: 10;");
        return hBox;
    }


    public void setMineField(MineField mineField) {
        this.mineField = mineField;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setDifficulty(GameDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    void setMouseTransparent(boolean transparent) {
        stackPaneOfPanel.setMouseTransparent(transparent);
    }

    public void setEventToRestartButton() {
        restartButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY) {
                mineField.restartGameOnTheSameDifficulty();
            }
        });
    }
    public void setEventToMainMenuButton() {
        mainMenuButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY) {
                mainMenu.show(difficulty,true);
            }
        });
    }

    public StackPane getStackPaneOfPanel() {
        return stackPaneOfPanel;
    }
    public Timer getTimer() {
        return timer;
    }
}
