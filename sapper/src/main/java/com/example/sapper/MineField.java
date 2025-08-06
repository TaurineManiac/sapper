package com.example.sapper;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.concurrent.ThreadLocalRandom;

public class MineField {
    Cell cells[][];
    int matrixOfBombs[][]; //0-empty 1-bomb
    int matrixOfNeighbours[][]; //(-1)-bomb (0-7)-empty
    GameDifficulty difficulty;
    TopGamePanel topGamePanel;
    GameField gameField;
    WinMenu winMenu;
    LoseMenu loseMenu;
    MainMenu mainMenu;
    Timer timer;
    GridPane gridPane;//для того чтобы я мог очищать поле,на котором размещаются кнопки,т.к. имеенно они не очиищались,хотя просчто матрица клеток удалялась
    Stage stage;
    StackPane stackPane;
    boolean firstTimeForClick = true;
    boolean firstTimeForGenerateStage=true;
    int firstRow;
    int firstCol;
    int maxBombsCounter;
    int maxBombNeighbourCell;
    int counterSafeCells = 0;
    int numberOfSafeCells = 0;
    int numberOfCells = 0;
    final int CELL_SIZE = 32;


    //мне крч стало пока похуй и я не хочу заморичиваться с ошибкой отсутсвия выхода(пример: 4 бомбы квадратом расположены в углу,
    // и та которая прям в углу,ты не узнаешь она бомба или нет)

    public MineField() {}

    public MineField(GridPane gridPane) {
        this.gridPane = gridPane;
    }
    public void generateMineField(GameDifficulty difficulty, WinMenu winMenu,LoseMenu loseMenu, Timer timer, MainMenu mainMenu) {
        this.difficulty = difficulty;
        int row = difficulty.getRow();
        int col = difficulty.getCol();

        switch (difficulty) {
            case EASY:
                maxBombsCounter = 30;
                maxBombNeighbourCell = 3;
                break;
            case MEDIUM:
                maxBombsCounter = 80;
                maxBombNeighbourCell = 5;
                break;
            case HARD:
                maxBombsCounter = 180;
                maxBombNeighbourCell = 7;
                break;
            default:
                break;
        }
        this.mainMenu = mainMenu;
        this.timer = timer;
        this.loseMenu = loseMenu;
        this.winMenu = winMenu;
        numberOfCells = row * col;

        setSizeOfStage(difficulty);

        initializeMatrices(difficulty);
        createZeroMatrix(difficulty);
//        generateField();
    }

    void generateField(int firstRow, int firstCol) {
        this.firstRow = firstRow;
        this.firstCol = firstCol;
        int rows = difficulty.getRow();
        int cols = difficulty.getCol();

        // Сбрасываем счётчики перед каждой новой генерацией

        numberOfSafeCells = 0;
        counterSafeCells    = 0;
        // Повторяем расстановку бомб, пока в первой кликнутой клетке не будет 0 соседей
        do {
            // 1) Полная очистка матриц

            createZeroMatrix(difficulty);

            // 2) Расставляем бомбы в случайных клетках (кроме первой)
            int bombsPlaced = 0;
            for (int i = 0; i < rows && bombsPlaced < maxBombsCounter; i++) {
                for (int j = 0; j < cols && bombsPlaced < maxBombsCounter; j++) {
                    // ни разу не ставим бомбу в первую кликнутую клетку
                    if (i == firstRow && j == firstCol) {
//                        numberOfSafeCells++;
                        continue;
                    }
                    if (!isExceedsMaxBombNeighbourCells(i, j)) {
                        bombsPlaced += tryToPutBombIntoCell(i, j);

                    }
                }
            }
            System.out.println(bombsPlaced);
            // После каждой расстановки бомб matrixOfNeighbours пересчитана за счёт tryToPutBombIntoCell

        } while (matrixOfNeighbours[firstRow][firstCol] != 0);  // <-- тут логика перевёрнута

        // 3) Наконец, заполняем объекты Cell и считаем общее число безопасных клеток
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = cells[i][j];
                cell.setIsBomb(matrixOfBombs[i][j] == 1);
                cell.setCounterOfClosestBombs(matrixOfNeighbours[i][j]);
                cell.setEventToButton();
                if (!cell.getIsBomb()) {
                    numberOfSafeCells++;
                }
            }
        }
        System.out.println(numberOfSafeCells+ "uuuuuuuuuuu");

//        boolean isFirstTime = true;
//        boolean isExceedsMaxBombNeighbourCells = false;
//        int bombsCounter = 0;
//
//        for (int i = firstRow; i < row; i++) {
//            if (bombsCounter >= maxBombsCounter) {
//                break;
//            }
//            for (int j = firstCol; j < col; j++) {
//                //1- bomb
//                //0- empty
//
//                if (isFirstTime) {
//                    matrixOfNeighbours[i][j] = 0;
//                    matrixOfBombs[i][j]=0;
//                    isFirstTime = false;
//                    continue;
//                }
//                isExceedsMaxBombNeighbourCells = isExceedsMaxBombNeighbourCells(i, j);
//                if (!isExceedsMaxBombNeighbourCells) {
//                    bombsCounter += tryToPutBombIntoCell(i, j);
//                }
//            }
//        }
//        for (int i = 0; i < firstRow; i++) {
//            for (int j = 0; j < firstCol; j++) {
//                isExceedsMaxBombNeighbourCells = isExceedsMaxBombNeighbourCells(i, j);
//                if (!isExceedsMaxBombNeighbourCells) {
//                    bombsCounter += tryToPutBombIntoCell(i, j);
//                }
//            }
//        }
//        for (int i = firstRow; i < row; i++) {
//            for (int j = firstCol; j < col; j++) {
//                cells[i][j].setIsBomb(matrixOfBombs[i][j] == 1);
//                if (matrixOfNeighbours[i][j] == -1) {
//                    cells[i][j].setCounterOfClosestBombs(-1);
//                    continue;
//                }
//                cells[i][j].setCounterOfClosestBombs(matrixOfNeighbours[i][j]);
//                cells[i][j].setEventToButton();
//                if (!cells[i][j].getIsBomb()) {
//                    numberOfSafeCells++;
//                }
//            }
//        }
//        for (int i = 0; i < firstRow; i++) {
//            for (int j = 0; j < firstCol; j++) {
//                cells[i][j].setIsBomb(matrixOfBombs[i][j] == 1);
//                if (matrixOfNeighbours[i][j] == -1) {
//                    cells[i][j].setCounterOfClosestBombs(-1);
//                    continue;
//                }
//                cells[i][j].setCounterOfClosestBombs(matrixOfNeighbours[i][j]);
//                cells[i][j].setEventToButton();
//                if (!cells[i][j].getIsBomb()) {
//                    numberOfSafeCells++;
//                }
//            }
//        }
    }

    void initializeMatrices(GameDifficulty difficulty) {
        int row = difficulty.getRow();
        int col = difficulty.getCol();
        matrixOfBombs = new int[row][col];
        matrixOfNeighbours = new int[row][col];
        cells = new Cell[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                cells[i][j] = new Cell(i, j, false, 0, this);
            }
        }
    }

    void createZeroMatrix(GameDifficulty difficulty) {
        int row = difficulty.getRow();
        int col = difficulty.getCol();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                cells[i][j].setCounterOfClosestBombs(0);
                matrixOfNeighbours[i][j] = 0;
                matrixOfBombs[i][j] = 0;
            }
        }
    }

    boolean isExceedsMaxBombNeighbourCells(int i, int j) {
        for (int k = i - 1; k < i + 2; k++) {
            for (int l = j - 1; l < j + 2; l++) {
                if (k == i && l == j) {
                    continue;
                }
                try {
                    if (matrixOfNeighbours[k][l] >= maxBombNeighbourCell) {
                        return true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        return false;
    }

    int tryToPutBombIntoCell(int i, int j) {
        int bombCounter = 0;
        matrixOfBombs[i][j] = ThreadLocalRandom.current().nextInt(1, 4) / 3;
        if (matrixOfBombs[i][j] == 1) {
            bombCounter++;
            matrixOfNeighbours[i][j] = -1;
            for (int k = i - 1; k < i + 2; k++) {
                for (int l = j - 1; l < j + 2; l++) {
                    try {
                        if ((k == i && l == j) || (matrixOfNeighbours[k][l] == -1)) {
                            continue;
                        }
                        matrixOfNeighbours[k][l]++;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }
        return bombCounter;
    }

    public void userLose() {
        timer.stop();
        setAllBombFlags();
        loseMenu.show(difficulty);
        // логика проигрыша, без параметров
    }

    public void userWin() {
        if (counterSafeCells >= numberOfSafeCells) {
            timer.stop();
            setAllBombFlags();
            winMenu.show(difficulty);
        }
    }

    void setAllBombFlags() {
        int row = difficulty.getRow();
        int col = difficulty.getCol();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(cells[i][j].getIsFlagged()){
                    cells[i][j].setFlagToButton();
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (cells[i][j].getIsOpen()) {
                    continue;
                }
                if (!cells[i][j].getIsFlagged() && cells[i][j].getIsBomb()) {
                    cells[i][j].setFlagToButton();
                }
            }
        }
    }

    public void restartGameOnTheSameDifficulty() {
        setParametersToStartVariant();
        createZeroMatrix(difficulty);
        rebuildGridPane(difficulty);
        loseMenu.hide();
        winMenu.hide();
    }

    public void restartGameOnAnotherDifficulty(GameDifficulty newDifficulty) {
        setParametersToStartVariant();
        topGamePanel.setDifficulty(newDifficulty);
        gameField.setDifficulty(newDifficulty);
        topGamePanel.show(newDifficulty);
        generateMineField(newDifficulty,winMenu,loseMenu,timer,mainMenu);
        rebuildGridPane(difficulty);


        loseMenu.hide();
        winMenu.hide();
    }

    void setParametersToStartVariant(){
        timer.stop();
        timer.setMin("00");
        timer.setSec("00");
        counterSafeCells=0;
        numberOfSafeCells=0;
        int row = difficulty.getRow();
        int col = difficulty.getCol();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                cells[i][j].setButtonVisible();
                cells[i][j].setAlreadyEnded(false);
                cells[i][j].setIsOpen(false);
                cells[i][j].setIsFlagged(false);
            }
        }
        firstTimeForClick = true;
    }

    public void recursiveOpenCells(int row, int col) {//чо-то типо поиска в ширину или глубину
        if(!cells[row][col].getIsBomb() && !cells[row][col].getIsFlagged() && !cells[row][col].getIsOpen()) {
            cells[row][col].setButtonInvisibleWithoutCallingRecursiveOpenCells();
            counterSafeCells++;
            userWin();
            if(cells[row][col].getCounterOfClosestBombs()==0){
                tryCallAllRecursiveCells(row,col);
            }
        }
    }

    public void tryCallAllRecursiveCells(int row, int col) {
        try {
            recursiveOpenCells(row-1,col);
        }
        catch (ArrayIndexOutOfBoundsException _) {}
        try {
            recursiveOpenCells(row-1,col-1);
        }
        catch (ArrayIndexOutOfBoundsException _) {}
        try {
            recursiveOpenCells(row+1,col);
        }
        catch (ArrayIndexOutOfBoundsException _) {}
        try {
            recursiveOpenCells(row+1,col+1);
        }
        catch (ArrayIndexOutOfBoundsException _) {}
        try {
            recursiveOpenCells(row,col-1);
        }
        catch (ArrayIndexOutOfBoundsException _) {}
        try {
            recursiveOpenCells(row+1,col-1);
        }
        catch (ArrayIndexOutOfBoundsException _) {}
        try {
            recursiveOpenCells(row,col+1);
        }
        catch (ArrayIndexOutOfBoundsException _) {}
        try {
            recursiveOpenCells(row-1,col+1);
        }
        catch (ArrayIndexOutOfBoundsException _) {}
    }

    public void rebuildGridPane(GameDifficulty newDifficulty) {
        Font minecraftFont = Font.loadFont(getClass().getResourceAsStream("/Minecraft.ttf"), 18);
        gridPane.getChildren().clear();
        for (int i = 0; i < newDifficulty.getRow(); i++) {
            for (int j = 0; j < newDifficulty.getCol(); j++) {
                Cell cell = cells[i][j];
                cell.setEventToButton();
                cell.button.setFont(minecraftFont);
                gridPane.add(cell.getStackPane(), j, i);
            }
        }
    }

    public boolean getFirstTimeForClick() {
        return firstTimeForClick;
    }
    public void setFirstTimeForClick(boolean firstTimeForClick) {
        this.firstTimeForClick = firstTimeForClick;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public Stage getStage() {
        return stage;
    }
    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public void setTopGamePanel(TopGamePanel topGamePanel) {
        this.topGamePanel = topGamePanel;
    }

    public void setGameField(GameField gameField) {
        this.gameField = gameField;
    }

    void setSizeOfStage(GameDifficulty newDifficulty) {
        if(firstTimeForGenerateStage){
            firstTimeForGenerateStage=false;
        }
        else{
            int row = newDifficulty.getRow();
            int col = newDifficulty.getCol();
            stage.setWidth(CELL_SIZE*col+30);
            stage.setHeight(CELL_SIZE*row+100);
            stage.centerOnScreen();
        }
    }

    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }
}
