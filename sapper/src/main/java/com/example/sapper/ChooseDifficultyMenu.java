package com.example.sapper;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class ChooseDifficultyMenu {
    GameDifficulty difficulty;
    MainMenu mainMenu;
    MineField mineField;
    Button closeButton;
    Button easyButton;
    Button mediumButton;
    Button hardButton;
    Label chooseDifficultyLabel;
    Rectangle background;
    StackPane stackPane;
    boolean isHaveMainMenuHaveCloseButton = false;
    final int CELL_SIZE = 32;

    public ChooseDifficultyMenu() {
        javafx.scene.text.Font minecraftFont = Font.loadFont(getClass().getResourceAsStream("/Minecraft.ttf"), 18);
        easyButton = new Button("Easy");
        mediumButton = new Button("Medium");
        hardButton = new Button("Hard");
        closeButton = new Button("Close");
        chooseDifficultyLabel = new Label("Choose Difficulty:");

        easyButton.setFont(minecraftFont);
        mediumButton.setFont(minecraftFont);
        hardButton.setFont(minecraftFont);
        closeButton.setFont(minecraftFont);
        chooseDifficultyLabel.setFont(minecraftFont);

        chooseDifficultyLabel.setStyle("-fx-font-weight: bold;-fx-cell-size: 32 ; -fx-text-alignment: CENTER;");

        setEventToCloseButton(false);
        setEventToEasyButton();
        setEventToMediumButton();
        setEventToHardButton();

        stackPane = new StackPane();
    }

    public void show(GameDifficulty gameDifficulty,boolean isHaveMainMenuHaveCloseButton) {
        this.isHaveMainMenuHaveCloseButton = isHaveMainMenuHaveCloseButton;
        if(this.isHaveMainMenuHaveCloseButton) {
            setEventToCloseButton(this.isHaveMainMenuHaveCloseButton);
        }
        this.difficulty = gameDifficulty;
        background = new Rectangle(CELL_SIZE*this.difficulty.getCol()+20, CELL_SIZE*this.difficulty.getRow()+60);
        background.setFill(Color.LIGHTGRAY);

        setMouseTransparent(false);
        stackPane.getChildren().clear();
        stackPane.getChildren().addAll(background,getVBox());
    }

    VBox getVBox() {
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.getChildren().addAll( easyButton, mediumButton, hardButton);
        hBox.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.getChildren().addAll( chooseDifficultyLabel,hBox,closeButton );
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    public void hide(){
        setMouseTransparent(true);
        stackPane.getChildren().clear();
    }

    void setMouseTransparent(boolean mouseTransparent){
        stackPane.setMouseTransparent(mouseTransparent);
    }

    public StackPane getStackPane() {
        return stackPane;
    }


    void setEventToCloseButton(boolean isHaveMainMenuHaveCloseButton) {
        closeButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                hide();
                mainMenu.show(difficulty,isHaveMainMenuHaveCloseButton);

            }
        });
    }

    void setEventToEasyButton() {
        easyButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                this.difficulty = GameDifficulty.EASY;
                hide();
                mineField.restartGameOnAnotherDifficulty(GameDifficulty.EASY);

            }
        });
    }

    void setEventToMediumButton() {
        mediumButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                this.difficulty = GameDifficulty.MEDIUM;
                hide();
                mineField.restartGameOnAnotherDifficulty(GameDifficulty.MEDIUM);
            }
        });
    }

    void setEventToHardButton() {
        hardButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                this.difficulty = GameDifficulty.HARD;
                hide();
                mineField.restartGameOnAnotherDifficulty(GameDifficulty.HARD);
            }
        });
    }
    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void setMineField(MineField mineField) {
        this.mineField = mineField;
    }
    public MineField getMineField() {
        return mineField;
    }

}
