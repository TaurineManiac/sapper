package com.example.sapper;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainMenu {
    GameDifficulty difficulty;
    ChooseDifficultyMenu chooseDifficultyMenu;
    Rectangle background;
    Button closeButton;
    Button newGameButton;
    Button loadGameButton;
    Button exitButton;
    Button aboutButton;
    Label gameName;
    StackPane stackPane;
    File aboutFile=new File("C:\\Users\\Ignat\\Desktop\\java_projects\\sapper\\sapper\\src\\main\\resources\\about.txt");
    boolean isHaveCloseButton=false;
    final int CELL_SIZE = 32;

    public MainMenu() {
        Font minecraftFont = Font.loadFont(getClass().getResourceAsStream("/Minecraft.ttf"), 18);
        Font minecraftFontForName = Font.loadFont(getClass().getResourceAsStream("/Minecraft.ttf"), 30);
        Font minecraftFontForAbout = Font.loadFont(getClass().getResourceAsStream("/Minecraft.ttf"), 10);
        newGameButton = new Button("New Game");
        loadGameButton = new Button("Load Game");
        exitButton = new Button("Exit");
        aboutButton = new Button("About");
        closeButton = new Button("Close");
        gameName = new Label("SAPPER");
        stackPane = new StackPane();

        closeButton.setFont(minecraftFont);
        newGameButton.setFont(minecraftFont);
        loadGameButton.setFont(minecraftFont);
        exitButton.setFont(minecraftFont);
        aboutButton.setFont(minecraftFontForAbout);


        gameName.setFont(minecraftFontForName);
        setEventToCloseButton();
        setEventToNewGameButton();
        setEventToExitButton();
        setEventToAboutButton();
//        newGameButton.setStyle("-fx-font-weight: bold; -fx-text-alignment: CENTER;");
//        loadGameButton.setStyle("-fx-font-weight: bold; -fx-text-alignment: CENTER;");
//        exitButton.setStyle("-fx-font-weight: bold; -fx-text-alignment: CENTER;");
    }

    public void show(GameDifficulty difficulty,boolean isHaveCloseButton) {
        this.isHaveCloseButton=isHaveCloseButton;
        setMouseTransparent(false);
        this.difficulty = difficulty;
        int row =this.difficulty.getRow();
        int col =this.difficulty.getCol();
        background = new Rectangle(col*CELL_SIZE+20,row*CELL_SIZE+60);
        background.setFill(Color.LIGHTGRAY);

        // Кнопка About в правом нижнем углу
        StackPane aboutPane = new StackPane(aboutButton);
        aboutPane.setAlignment(Pos.BOTTOM_RIGHT);

        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(aboutPane);
        borderPane.setCenter(getVBox(this.isHaveCloseButton));

        stackPane.getChildren().clear();
        stackPane.getChildren().addAll(background,borderPane);
        stackPane.toFront();
//        getVBox()
    }

    VBox getVBox(boolean IsHaveCloseButton) {

        VBox vBox = new VBox();
        if(IsHaveCloseButton) {
            vBox.getChildren().addAll(gameName,newGameButton,loadGameButton,closeButton,exitButton);
        }
        else{
            vBox.getChildren().addAll(gameName,newGameButton,loadGameButton,exitButton);
        }
        vBox.setSpacing(20);
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

    public void setEventToNewGameButton() {
        newGameButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                hide();
                chooseDifficultyMenu.show(difficulty,isHaveCloseButton);

            }
        });
    }

    public void setEventToLoadGameButton() {
        loadGameButton.setOnMouseClicked(e -> {
           if(e.getButton() == MouseButton.PRIMARY){
               setMouseTransparent(true);
               hide();
           }
        });
    }

    public void setEventToCloseButton() {
        closeButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                setMouseTransparent(true);
                hide();
            }
        });
    }

    public void setEventToExitButton() {
        exitButton.setOnMouseClicked(e -> {
           if(e.getButton() == MouseButton.PRIMARY){
               Platform.exit();
           }
        });
    }

    public void setEventToAboutButton() {
        aboutButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                try {
                    Desktop.getDesktop().open(aboutFile);
                } catch (IOException ex) {

                }
            }
        });
    }

    public void setChooseDifficultyMenu(ChooseDifficultyMenu chooseDifficultyMenu) {
        this.chooseDifficultyMenu = chooseDifficultyMenu;
    }

    public void setDifficulty(GameDifficulty difficulty) {
        this.difficulty = difficulty;
    }
}
