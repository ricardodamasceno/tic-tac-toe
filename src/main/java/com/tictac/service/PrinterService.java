package com.tictac.service;

import com.tictac.model.Board;
import com.tictac.utils.ConstsEnum;

public interface PrinterService {

    void printWelcomeMessage(Board board);
    void printTicTacBoard(Board board);
    void printComeBackSoonMessage();
    void printInvalidPositionMessage();
    void printPositionAlreadyFilled(int[] position);
    void printUpdatedBoardMessage(Board board);
    void printPlayerTurn(ConstsEnum player);
    void printCoordinatesOutOfBoundsException();
    void printPlayerWonTheGame(Board board);
    void printComputersTurn();
    void printContinue();
    void printInvalidValue();
    void printInvalidFileInformation();

}
