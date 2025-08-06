package com.example.sapper;

public enum GameDifficulty {
    EASY(9,9),MEDIUM(16,16),HARD(16,32);

    int row;
    int col;
    GameDifficulty(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
}
