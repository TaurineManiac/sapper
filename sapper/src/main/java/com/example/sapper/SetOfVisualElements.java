package com.example.sapper;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SetOfVisualElements {
    LoseMenu loseMenu;
    WinMenu winMenu;
    MainMenu mainMenu;
    ChooseDifficultyMenu chooseDifficultyMenu;
    GameField gameField;
    TopGamePanel topGamePanel;
    MineField mineField;
    GameDifficulty difficulty;
    BorderPane borderPane;
    StackPane stackPaneFinal;

    public SetOfVisualElements(Stage stage) {
        mainMenu = new MainMenu();

        chooseDifficultyMenu = new ChooseDifficultyMenu();
        mainMenu.setChooseDifficultyMenu(chooseDifficultyMenu);

        winMenu = new WinMenu(mainMenu);
        loseMenu = new LoseMenu(mainMenu);


        gameField = new GameField();
        topGamePanel = new TopGamePanel();

        setupComponentsRelationship();

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: lightgray");
        stackPaneFinal = new StackPane();
        createStackPaneFinal(stage);
    }

    public void setupComponentsRelationship() {
        mainMenu.setChooseDifficultyMenu(chooseDifficultyMenu);
        chooseDifficultyMenu.setMainMenu(mainMenu);

        mineField = new MineField(gameField.getGridPane());
        mineField.setGameField(gameField);
        mineField.setTopGamePanel(topGamePanel);
        gameField.setMineField(mineField);

        // Передача зависимостей
        chooseDifficultyMenu.setMineField(mineField);
        topGamePanel.setMineField(mineField);
        topGamePanel.setMainMenu(mainMenu);

        // Начальная настройка
        mainMenu.setDifficulty(GameDifficulty.EASY);
        gameField.setDifficulty(GameDifficulty.EASY);
        mineField.generateMineField(GameDifficulty.EASY, winMenu, loseMenu, topGamePanel.getTimer(), mainMenu);
        winMenu.hide();
        loseMenu.hide();
        chooseDifficultyMenu.hide();
        mainMenu.show(GameDifficulty.EASY,false);


    }

    public void createStackPaneFinal(Stage stage) {
        borderPane.setTop(topGamePanel.getStackPaneOfPanel());

        StackPane stackPaneTemp = new StackPane();
        stackPaneTemp.getChildren().addAll(gameField.getGridPane(),winMenu.getStackPane(),loseMenu.getStackPane());
        borderPane.setCenter(stackPaneTemp);

        stackPaneFinal.getChildren().add(borderPane);
        stackPaneFinal.getChildren().add(mainMenu.getStackPane());
        stackPaneFinal.getChildren().add(chooseDifficultyMenu.getStackPane());

        mineField.setStackPane(stackPaneFinal);
        mineField.setStage(stage);


    }

    public StackPane getStackPaneFinal() {
        return stackPaneFinal;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public MineField getMineField() {
        return mineField;
    }

    public void changeDifficulty(GameDifficulty newDifficulty) {
        this.difficulty = newDifficulty;
        mineField.restartGameOnAnotherDifficulty(newDifficulty);
    }

    public void setStaticWindow(Stage stage) {
        stage.setResizable(false);//фиксированны размер окна
    }
}
