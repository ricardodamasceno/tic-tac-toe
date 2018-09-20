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

        if(!board.isInvalidBoard()){
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
        }else{
            printerService.printInvalidFileInformation();
        }
    }
}


