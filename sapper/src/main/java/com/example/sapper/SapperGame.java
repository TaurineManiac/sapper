package com.example.sapper;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class SapperGame extends Application {
    private static final int ROWS = 9;
    private static final int COLS = 9;
    private static final int CELL_SIZE = 32;
    private Timer timer;
    private MineField mineField;

    @Override
    public void start(Stage stage) throws Exception {
//        BorderPane root = new BorderPane();
//        root.setStyle("-fx-background-color: lightgray;");
//
//        GridPane gridPane = new GridPane();
//        gridPane.setAlignment(Pos.CENTER);
//        gridPane.setHgap(0);
//        gridPane.setVgap(0);
//
//        // Загрузка шрифта
//        Font minecraftFont = Font.loadFont(getClass().getResourceAsStream("/Minecraft.ttf"), 18 );
//
//        // Инициализация таймера
//        timer = new Timer();
//        timer.min = new Label("00");
//        timer.sec = new Label("00");
//        timer.setFont(minecraftFont);
//
//// Стилизация таймера
//        Label timerLabel = new Label();
//        timerLabel.setFont(minecraftFont);
//        timerLabel.setStyle("-fx-text-fill: rgba(144, 238, 144, 0.8);"); // светло-салатовый с прозрачностью
//        timerLabel.textProperty().bind(
//                timer.min.textProperty().concat(":").concat(timer.sec.textProperty())
//        );
//
//// Прямоугольник фона
//        Rectangle timerBackground = new Rectangle(100, 32); // ширина и высота примерно как у кнопки
//        timerBackground.setArcWidth(10);  // скругление краёв
//        timerBackground.setArcHeight(10);
//        timerBackground.setStyle("-fx-fill: darkgray; -fx-stroke: black; -fx-stroke-width: 1;");
//
//// StackPane для таймера и фона
//        StackPane timerStack = new StackPane(timerBackground, timerLabel);
//        timerStack.setMinSize(100, 32);
//        timerStack.setMaxSize(100, 32);
//        timerStack.setPrefSize(100, 32);
//
//
//
//        // Кнопка рестарта
//        Button restartButton = new Button("Restart");
//        restartButton.setFont(minecraftFont);
//        restartButton.setTextAlignment(TextAlignment.CENTER);
//
//
//        restartButton.setOnAction(event -> {
//
//            mineField.restartGameOnTheSameDifficulty();
//
//
//        });
//
//        MainMenu mainMenu = new MainMenu();
//        ChooseDifficultyMenu chooseDifficultyMenu = new ChooseDifficultyMenu();
//        mainMenu.setChooseDifficultyMenu(chooseDifficultyMenu);
//        chooseDifficultyMenu.setMainMenu(mainMenu);
//        chooseDifficultyMenu.hide();
//        mainMenu.hide();
//        WinMenu winMenu = new WinMenu(mainMenu);
//        winMenu.hide();
//        LoseMenu loseMenu = new LoseMenu(mainMenu);
//        loseMenu.hide();
//
//        // Инициализация игрового поля
//        mineField = new MineField(gridPane);
//        mineField.generateMineField(GameDifficulty.EASY,winMenu,loseMenu,timer,mainMenu);
//        chooseDifficultyMenu.setMineField(mineField);
//
//        Button mainMenuButton = new Button();
//        mainMenuButton.setStyle("-fx-background-color: transparent; " +
//                "-fx-border-color: black; " +
//                "-fx-border-width: 1; " +
//                "-fx-pref-width: 32; " +
//                "-fx-pref-height: 32; " +
//                "-fx-min-width: 32; " +
//                "-fx-min-height: 32; " +
//                "-fx-max-width: 32; " +
//                "-fx-max-height: 32;");
//        mainMenuButton.setFont(minecraftFont);
//        Image flagImg = new Image(getClass().getResourceAsStream("/menu_lines_3.png"));
//        ImageView iv = new ImageView(flagImg);
//        iv.setFitWidth(30);
//        iv.setFitHeight(30);
//        mainMenuButton.setGraphic(iv);
//        mainMenuButton.setOnAction(event -> {
//            mainMenu.show(mineField.getDifficulty());
//        });
//
//        HBox topPanel = new HBox(20, timerStack, mainMenuButton,restartButton);
//        topPanel.setAlignment(Pos.CENTER);
//        topPanel.setStyle("-fx-padding: 10;");
//
//        // Игровое поле
////        GridPane gridPane = new GridPane();
////        gridPane.setAlignment(Pos.CENTER);
////        gridPane.setHgap(0);
////        gridPane.setVgap(0);
//
//
//
//        // Добавление клеток на поле
//        for (int i = 0; i < mineField.difficulty.getRow(); i++) {
//            for (int j = 0; j < mineField.difficulty.getCol(); j++) {
//                Cell cell = mineField.cells[i][j];
//                cell.setEventToButton();
//                cell.button.setFont(minecraftFont);
//                gridPane.add(cell.getStackPane(), j, i);
//            }
//        }
//
//
//
//        StackPane centerStack = new StackPane();
//        centerStack.getChildren().add(gridPane);
//        centerStack.getChildren().add(winMenu.getStackPane());
//        centerStack.getChildren().add(loseMenu.getStackPane());
//
//
//        root.setTop(topPanel);
//        root.setCenter(centerStack);
//
//        StackPane stackPane = new StackPane();
//        stackPane.getChildren().addAll(root,mainMenu.getStackPane(),chooseDifficultyMenu.getStackPane());
//        stackPane.setAlignment(Pos.CENTER);
//
//
//        Scene scene = new Scene(stackPane, mineField.difficulty.getCol() * CELL_SIZE + 20, mineField.difficulty.getRow() * CELL_SIZE + 60);
//        mineField.setStage(stage);
//        mineField.setStackPane(stackPane);
//        stage.setResizable(false);//фиксированны размер окна
//        stage.setTitle("Sapper");
//        stage.setScene(scene);
//        stage.show();




        SetOfVisualElements setOfVisualElements = new SetOfVisualElements(stage);
        Scene scene = new Scene(setOfVisualElements.getStackPaneFinal(),setOfVisualElements.getMineField().getDifficulty().getCol()*CELL_SIZE+20,setOfVisualElements.getMineField().getDifficulty().getRow()*CELL_SIZE+60);
        stage.setScene(scene);
        stage.setTitle("Sapper Game");
        stage.getIcons().add(new Image("bomb.jpeg"));
        setOfVisualElements.setStaticWindow(stage);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}