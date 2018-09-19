package com.tictac.service.impl;

import com.tictac.model.Board;
import com.tictac.service.PrinterService;
import com.tictac.utils.ConstsEnum;

public class PrinterServiceImpl implements PrinterService {

    public void printTicTacBoard(Board board) {
        String output = "";
        for(int horizontal = 0; horizontal < board.getBoardSize(); horizontal++){
            for(int vertical = 0; vertical < board.getBoardSize(); vertical++){
                if(vertical == 0){
                    output = board.getBoard()[horizontal][vertical] != null ? output.concat("\n" + board.getBoard()[horizontal][vertical] + " ") : output.concat("\n= ");
                }else{
                    output = board.getBoard()[horizontal][vertical] != null ? output.concat(" ".concat(board.getBoard()[horizontal][vertical] + " ")) : output.concat(" = ");
                }
            }
        }
        System.out.println(output);
    }

    public void printWelcomeMessage(Board board){
        System.out.println("\n\nWelcome to the Tic Tac Toe Game");
        System.out.println("\nLet's check the information you typed in the config.txt file");
        System.out.println("\nPlayer One will be represented by the symbol '".concat(board.getSymbolPlayer1()).concat("'"));
        System.out.println("Player Two will be represented by the symbol '".concat(board.getSymbolPlayer2()).concat("'"));
        System.out.println("The computer will be represented by the symbol '".concat(board.getSymbolComputer()).concat("'"));
        System.out.println("\n\nThe size of the board will be '".concat(board.getBoardSize().toString()).concat("' and you can check it below"));
        printTicTacBoard(board);
    }

    public void printComeBackSoonMessage(){
        System.out.println("\n\n=/");
        System.out.println("Comeback soon !");
    }

    public void printInvalidPositionMessage(){
        System.out.println("\n\nType a valid position");
        System.out.println("Ex: The position need to have the pattern 0,0");
    }

    public void printPositionAlreadyFilled(int[] position){
        System.out.println("\n\nThe position '".concat(Integer.toString(position[0])).concat(",").concat(Integer.toString(position[1])).concat("' is not empty"));
        System.out.println("Choose another one.");
    }

    public void printUpdatedBoardMessage(Board board){
        System.out.println("\n\nNow the board looks like this.");
        printTicTacBoard(board);
    }

    public void printPlayerTurn(ConstsEnum player){
        System.out.println("\n\n".concat(player.getName()).concat("'s turn."));
        System.out.println("Type the coordinates of the position you want to set on the board");
    }

    public void printComputersTurn(){
        System.out.println("\n\n".concat(ConstsEnum.COMPUTER.getName()).concat("'s turn."));
    }

    public void printCoordinatesOutOfBoundsException(){
        System.out.println("\n\nThe coordinates you entered exceed the board size.");
        System.out.println("Type a valid position");
    }

    public void printInvalidFileInformation(){
        System.out.println("\n\nThe information you inserted in the file are invalid");
    }

    public void printPlayerWonTheGame(Board board){
        System.out.println("\n\nGame Over !!!");
        printTicTacBoard(board);
        System.out.println("\nPlayer with the symbol " + board.getWinner().concat(" won the game !\n\n"));
    }

    public void printContinue(){
        System.out.println("\nDo you wish to continue ? (y/n)");
    }

    public void printInvalidValue(){
        System.out.println("\nInvalid value !!!");
    }

}
