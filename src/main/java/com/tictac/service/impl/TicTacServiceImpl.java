package com.tictac.service.impl;

import com.tictac.model.Board;
import com.tictac.service.BoardService;
import com.tictac.service.PlayerService;
import com.tictac.service.PrinterService;
import com.tictac.service.TicTacService;
import com.tictac.utils.ConstsEnum;

import java.io.IOException;

public class TicTacServiceImpl implements TicTacService {

    private BoardService boardService = new BoardServiceImpl();
    private PlayerService playerService = new PlayerServiceImpl();
    private PrinterService printerService = new PrinterServiceImpl();

    private Board board;

    public void playGame() throws IOException {
        board = boardService.fillBoardEntity();
        fillBoard(board);
        printerService.printWelcomeMessage(board);
        if (playerService.startGame()) {
            do {
                playerService.playHuman(board, ConstsEnum.FIRST_PLAYER);
                if(!board.isGameOver()){
                    playerService.playHuman(board, ConstsEnum.SECOND_PLAYER);
                }
                if(!board.isGameOver()){
                    playerService.playComputer(board);
                }
            }while (!board.isGameOver());
        } else {
            printerService.printComeBackSoonMessage();
        }
    }

    /*Temporary function*/
    private void fillBoard(Board board){
        board.getBoard()[0][0] = "a";
        board.getBoard()[1][1] = "a";
        //board.getBoard()[2][3] = "a";
        /*board.getBoard()[3][3] = "a";
        board.getBoard()[4][3] = "a";
        board.getBoard()[5][3] = "a";
        board.getBoard()[6][3] = "a";
        board.getBoard()[7][3] = "a";
        board.getBoard()[8][3] = "a";*/
        //board.getBoard()[0][1] = "a";
        //board.getBoard()[0][2] = "a";
        //board.getBoard()[1][0] = "a";
        //board.getBoard()[1][1] = "a";
        //board.getBoard()[1][2] = "a";
        //board.getBoard()[2][0] = "b";
        //board.getBoard()[2][1] = "c";
        //board.getBoard()[2][2] = "a";
    }

}


