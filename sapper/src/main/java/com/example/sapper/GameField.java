package com.example.sapper;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class GameField {
    GridPane gridPane;
    GameDifficulty difficulty;
    MineField mineField;

    public GameField() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
    }

    public void show(GameDifficulty difficulty) {
        Font minecraftFont = Font.loadFont(getClass().getResourceAsStream("/Minecraft.ttf"), 18);
        setDifficulty(difficulty);
        setMouseTransparent(false);
        gridPane.getChildren().clear();
        for(int i=difficulty.getRow(); i<=difficulty.getCol(); i++) {
            for(int j=difficulty.getCol(); j<=difficulty.getRow(); j++) {
                Cell cell = mineField.getCell(i, j);
                cell.setEventToButton();
                cell.getButton().setFont(minecraftFont);
                gridPane.add(cell.getStackPane(),j,i);
            }
        }
        gridPane.setAlignment(Pos.CENTER);
    }

    public void hide(){
        setMouseTransparent(true);
        gridPane.getChildren().clear();
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setDifficulty(GameDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setMineField(MineField mineField) {
        this.mineField = mineField;
    }

    void setMouseTransparent(boolean transparent) {
        gridPane.setMouseTransparent(transparent);
    }
}
