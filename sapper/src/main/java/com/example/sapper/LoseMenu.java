package com.example.sapper;

import com.sun.tools.javac.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class LoseMenu {
    MainMenu main;
    Rectangle rectangle;
    Label label;
    GameDifficulty gameDifficulty;
    StackPane stackPane;
    final int CELL_SIZE = 32;
    Button mainMenuButton;
    Button closeButton;

    public LoseMenu(MainMenu mainMenu) {
        this.main = mainMenu;
        // Инициализация основного контейнера
        stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);

        // Загрузка шрифта (с обработкой ошибки)
        Font minecraftFont;
        try {
            minecraftFont = Font.loadFont(getClass().getResourceAsStream("/Minecraft.ttf"), 24);
        } catch (Exception e) {
            minecraftFont = Font.font("Arial", 24); // Fallback шрифт
        }
        stackPane.setMouseTransparent(true);
        // Создание элементов интерфейса
        label = new Label("YOU LOSE.");
        label.setFont(minecraftFont);
        label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");


        mainMenuButton = new Button("Main menu");
        closeButton = new Button("Close");

        // Установка шрифта для кнопок
        mainMenuButton.setFont(minecraftFont);
        closeButton.setFont(minecraftFont);

        // Настройка контейнера для элементов меню
        VBox menuBox = new VBox(20, label, mainMenuButton, closeButton);
        menuBox.setAlignment(Pos.CENTER);
//        menuBox.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-padding: 20;");

        stackPane.getChildren().add(menuBox);
    }

    public void setEventToCloseButton() {
        closeButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                stackPane.getChildren().clear();
            }
        });
    }

    public void setEventToMainMenuButton() {
        mainMenuButton.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                main.show(gameDifficulty,false);
            }
        });
    }

    public VBox getBox(){
        VBox menuBox = new VBox(20, label, mainMenuButton, closeButton);
        menuBox.setAlignment(Pos.CENTER);
//        menuBox.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-padding: 20;");
        return menuBox;
    }

    public void show(GameDifficulty difficulty) {
        setMouseTransparent(false);
        gameDifficulty = difficulty;
        // Создание фонового прямоугольника
        rectangle = new Rectangle(
                difficulty.getCol() * CELL_SIZE,
                difficulty.getRow() * CELL_SIZE
        );
        rectangle.setFill(Color.rgb(200,0,0, 0.5));

        setEventToCloseButton();
        setEventToMainMenuButton();

        // Очистка и добавление элементов
        stackPane.getChildren().clear();
        stackPane.getChildren().addAll(rectangle, getBox());
        stackPane.toFront();
    }

    public void hide() {
        setMouseTransparent(true);
        stackPane.getChildren().clear();
    }

    // Геттеры
    public StackPane getStackPane() {
        return stackPane;
    }

    public Button getMainMenuButton() {
        return mainMenuButton;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public void setMouseTransparent(boolean mouseTransparent) {
        stackPane.setMouseTransparent(mouseTransparent);
    }

//    // Создаём полупрозрачный зелёный прямоугольник
//    Rectangle overlay = new Rectangle(CELL_SIZE * COLS, CELL_SIZE * ROWS);
//        overlay.setFill(Color.rgb(119, 221, 119, 0.5)); // RGBA: последнее значение (0.5) - прозрачность
}
