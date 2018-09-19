package com.tictac.service.impl;

import com.tictac.exception.CoordinatesOutOfBoundsException;
import com.tictac.exception.InvalidCoordinateValueException;
import com.tictac.model.Board;
import com.tictac.service.BoardService;
import com.tictac.service.PlayerService;
import com.tictac.service.PrinterService;
import com.tictac.utils.ConstsEnum;
import com.tictac.utils.StringUtils;

import java.util.Scanner;

public class PlayerServiceImpl implements PlayerService {

    Scanner reader;
    BoardService boardService = new BoardServiceImpl();
    PrinterService printerService = new PrinterServiceImpl();

    public boolean startGame() {

        boolean validValue = false;
        boolean result = false;
        String typedValue;

        do {
            System.out.println("\nDo you wish to continue ? (y/n)");
            reader = new Scanner(System.in);
            try {
                typedValue = reader.next();
                typedValue = typedValue.toLowerCase();
                if (typedValue.equals(ConstsEnum.Y.getValue())) {
                    result = true;
                    validValue = true;
                } else if (typedValue.equals(ConstsEnum.N.getValue())) {
                    result = false;
                    validValue = true;
                } else {
                    System.out.println("\nInvalid value !!!");
                }
            } catch (Exception e) {
                System.out.println("\nInvalid value !!!");
            }
        } while (!validValue);
        return result;
    }

    public void playHuman(Board board, ConstsEnum player){

        boolean validValue = false;
        reader = new Scanner(System.in);
        int [] position;

        printerService.printPlayerTurn(player);

        do {
            try{
                String value = reader.next();
                position = StringUtils.getPlayCoordinates(board, value);
                if(boardService.validateIfBoardPositionIsEmpty(board, position)){
                    boardService.registerPlayPosition(board, position, player);
                    validValue = true;
                }else{
                    printerService.printPositionAlreadyFilled(position);
                }
            }catch (InvalidCoordinateValueException e){
                printerService.printInvalidPositionMessage();
            }catch (CoordinatesOutOfBoundsException e){
                printerService.printCoordinatesOutOfBoundsException();
            }
        } while (!validValue);

        boardService.checkIfPlayerWonTheGame(board);

        if(!board.isGameOver()){
            printerService.printUpdatedBoardMessage(board);
        }
    }

    public void playComputer(Board board){
        printerService.printComputersTurn();
        blockPLayer(board);
        printerService.printTicTacBoard(board);
    }

    private boolean blockPLayer(Board board){

        boolean blockLine = false, blockDiagonal = false;

        blockLine = blockLine(board);
        if(!blockLine){
            blockDiagonal = blockDiagonal(board);
        }
        return blockLine || blockDiagonal;
    }

    private boolean blockLine(Board board){
        return boardService.iterateLineOrColumn(board, ConstsEnum.BOARD_LINE.getValue(), ConstsEnum.BLOCK_LINE_OR_COLUMN)
                || boardService.iterateLineOrColumn(board, ConstsEnum.BOARD_COLUMN.getValue(), ConstsEnum.BLOCK_LINE_OR_COLUMN);
    }

    private boolean blockDiagonal (Board board){
        return boardService.iterateDiagonalBottom(board, ConstsEnum.BLOCK_DIAGONAL)
                || boardService.iterateDiagonalTop(board, ConstsEnum.BLOCK_DIAGONAL);
    }


}
