package com.tictac.model;

public class Board {

    private String [][] board;
    private Integer boardSize;
    private boolean gameOver = false;

    private String winner = "";
    private String symbolPlayer1 = "";
    private String symbolPlayer2 = "";
    private String symbolComputer = "";

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public Integer getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(Integer boardSize) {
        this.boardSize = boardSize;
    }

    public String getSymbolPlayer1() {
        return symbolPlayer1;
    }

    public void setSymbolPlayer1(String symbolPlayer1) {
        this.symbolPlayer1 = symbolPlayer1;
    }

    public String getSymbolPlayer2() {
        return symbolPlayer2;
    }

    public void setSymbolPlayer2(String symbolPlayer2) {
        this.symbolPlayer2 = symbolPlayer2;
    }

    public String getSymbolComputer() {
        return symbolComputer;
    }

    public void setSymbolComputer(String symbolComputer) {
        this.symbolComputer = symbolComputer;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
