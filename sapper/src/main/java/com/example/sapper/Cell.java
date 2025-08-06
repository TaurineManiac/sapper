package com.example.sapper;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Cell {
    private int row;
    private int col;
    private boolean isBomb;
    private boolean isOpen = false;
    private boolean isFlagged = false;
    private boolean isAlreadyEnded = false;
    private int counterOfClosestBombs;
    private MineField mineField;
    protected Button button;
    Rectangle rectangle;
    StackPane stackPane;
    private Image image;
    private ImageView imageView;
    private javafx.scene.control.Label label;




    public void setIsBomb(boolean isBomb){
        this.isBomb=isBomb;
    }
    public void setIsOpen(boolean isOpen){
        this.isOpen=isOpen;
    }
    public void setIsFlagged(boolean isFlagged){
        this.isFlagged=isFlagged;
    }
    public void setCounterOfClosestBombs(int counter){
        this.counterOfClosestBombs=counter;
    }
    public boolean getIsBomb(){
        return isBomb;
    }
    public boolean getIsOpen(){
        return isOpen;
    }
    public boolean getIsFlagged(){
        return isFlagged;
    }
    public int getCounterOfClosestBombs(){
        return counterOfClosestBombs;
    }
    public StackPane getStackPane(){ return stackPane;}

    public void setEventToButton() {
        button.setOnMouseClicked(mouseEvent -> {
            if (isOpen || isAlreadyEnded) return;

            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if(mineField.getFirstTimeForClick()){
                    mineField.setFirstTimeForClick(false);
                    mineField.generateField(row,col);
                    mineField.timer.play();
                }
                if (!isFlagged) {

                    setButtonInvisible();
                }
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                if(!mineField.getFirstTimeForClick()){
                    setFlagToButton();
                }
            }
        });
    }

    public void setFlagToButton(){
        if (!isFlagged) {
            isFlagged = true;
            Image flagImg = new Image(getClass().getResourceAsStream("/redflag.jpg"));
            ImageView iv = new ImageView(flagImg);
            iv.setFitWidth(30);
            iv.setFitHeight(30);
            button.setGraphic(iv);
//            stackPane.getChildren().addAll(rectangle,button);
        }
        else{
            isFlagged = false;
            button.setGraphic(null);
        }
    }

    public void setAlreadyEnded(boolean alreadyEnded){
        this.isAlreadyEnded =alreadyEnded;
    }

    public void setButtonInvisible() {
        isOpen = true;
        Font minecraftFont = Font.loadFont(getClass().getResourceAsStream("/Minecraft.ttf"), 18);

        button.setStyle("-fx-background-color: transparent; " +
                "-fx-border-color: black; " +
                "-fx-border-width: 1; " +
                "-fx-pref-width: 32; " +
                "-fx-pref-height: 32; " +
                "-fx-min-width: 32; " +
                "-fx-min-height: 32; " +
                "-fx-max-width: 32; " +
                "-fx-max-height: 32;");

        if (isBomb) {
            Image bombImage = new Image(getClass().getResourceAsStream("/bomb.jpeg"));
            ImageView bombView = new ImageView(bombImage);
            bombView.setPreserveRatio(true);
            bombView.setFitWidth(30); // Чуть меньше чем кнопка для отступов
            bombView.setFitHeight(30);
            button.setGraphic(bombView);
//            stackPane.getChildren().clear();
//            stackPane.getChildren().addAll(rectangle,button);
            mineField.userLose();
        } else if (counterOfClosestBombs >= 0) {
            if(counterOfClosestBombs!=0){
                label.setText(String.valueOf(counterOfClosestBombs));
                button.setStyle("-fx-background-color: transparent; " +
                        "-fx-border-color: black; " +
                        "-fx-border-width: 1; " +
                        "-fx-pref-width: 32; " +
                        "-fx-pref-height: 32; " +
                        "-fx-min-width: 32; " +
                        "-fx-min-height: 32; " +
                        "-fx-max-width: 32; " +
                        "-fx-max-height: 32; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-insets: 0; " +
                        "-fx-background-radius: 0; " +
                        "-fx-padding: 0; " +
                        "-fx-effect: null;" );
                button.setFont(minecraftFont);
                label.setFont(minecraftFont);
                mineField.counterSafeCells++;
                mineField.userWin();
            }
            else{
                isOpen = false;
//            stackPane.getChildren().clear();
//            stackPane.getChildren().addAll(rectangle,label);

                mineField.recursiveOpenCells(row,col);
            }



        }
        isOpen = true;
    }

    public void setButtonInvisibleWithoutCallingRecursiveOpenCells(){
        Font minecraftFont = Font.loadFont(getClass().getResourceAsStream("/Minecraft.ttf"), 18);
        isOpen = true;
        button.setStyle("-fx-background-color: transparent; " +
                "-fx-border-color: black; " +
                "-fx-border-width: 1; " +
                "-fx-pref-width: 32; " +
                "-fx-pref-height: 32; " +
                "-fx-min-width: 32; " +
                "-fx-min-height: 32; " +
                "-fx-max-width: 32; " +
                "-fx-max-height: 32;");

        if (isBomb) {
            Image bombImage = new Image(getClass().getResourceAsStream("/bomb.jpeg"));
            ImageView bombView = new ImageView(bombImage);
            bombView.setPreserveRatio(true);
            bombView.setFitWidth(30); // Чуть меньше чем кнопка для отступов
            bombView.setFitHeight(30);
            button.setGraphic(bombView);
            mineField.userLose();
        } else if (counterOfClosestBombs >= 0) {
            if(counterOfClosestBombs!=0){
                label.setText(String.valueOf(counterOfClosestBombs));
                button.setStyle("-fx-background-color: transparent; " +
                        "-fx-border-color: black; " +
                        "-fx-border-width: 1; " +
                        "-fx-pref-width: 32; " +
                        "-fx-pref-height: 32; " +
                        "-fx-min-width: 32; " +
                        "-fx-min-height: 32; " +
                        "-fx-max-width: 32; " +
                        "-fx-max-height: 32; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-insets: 0; " +
                        "-fx-background-radius: 0; " +
                        "-fx-padding: 0; " +
                        "-fx-effect: null;" );
                button.setFont(minecraftFont);
                label.setFont(minecraftFont);
            }
//            stackPane.getChildren().clear();
//            stackPane.getChildren().addAll(rectangle,label);


//            mineField.counterSafeCells++;
            mineField.userWin();
        }

    }

    public void setButtonVisible() {
        button.setStyle("-fx-background-color: rgba(122,127,128,1); " +
                "-fx-border-color: black; " +
                "-fx-border-width: 1; " +
                "-fx-pref-width: 32; " +
                "-fx-pref-height: 32; " +
                "-fx-min-width: 32; " +
                "-fx-min-height: 32; " +
                "-fx-max-width: 32; " +
                "-fx-max-height: 32;");
        button.setGraphic(null);
        button.setText("");
        label.setText("");
//        stackPane.getChildren().clear();
    }

    Cell(int row, int col, boolean isBomb, int closestBombs, MineField mineField) {
        this.row = row;
        this.col = col;
        this.isBomb= isBomb;
        this.counterOfClosestBombs = closestBombs;
        isAlreadyEnded = false;
        this.mineField = mineField;
        label = new javafx.scene.control.Label(String.valueOf(closestBombs));
        button = new Button();
        button.setStyle("-fx-background-color: rgba(122,127,128,1); " +
                "-fx-border-color: black; " +
                "-fx-border-width: 1; " +
                "-fx-pref-width: 32; " +
                "-fx-pref-height: 32; " +
                "-fx-min-width: 32; " +
                "-fx-min-height: 32; " +
                "-fx-max-width: 32; " +
                "-fx-max-height: 32;");
        stackPane = new StackPane();
        label = new javafx.scene.control.Label();
        rectangle = new Rectangle();
        rectangle.setStyle("-fx-background-color: lightgray; " +
                "-fx-border-color: black; " +
                "-fx-border-width: 1; " +
                "-fx-pref-width: 32; " +
                "-fx-pref-height: 32; " +
                "-fx-min-width: 32; " +
                "-fx-min-height: 32; " +
                "-fx-max-width: 32; " +
                "-fx-max-height: 32;");
        stackPane.getChildren().addAll(rectangle, label, button);
    }

    public Button getButton() {
        return button;
    }
}
