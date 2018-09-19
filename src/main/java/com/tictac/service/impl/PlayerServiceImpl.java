package com.tictac.service.impl;

import com.tictac.exception.CoordinatesOutOfBoundsException;
import com.tictac.exception.InvalidCoordinateValueException;
import com.tictac.model.Board;
import com.tictac.service.BoardService;
import com.tictac.service.PlayerService;
import com.tictac.service.PrinterService;
import com.tictac.utils.ConstsEnum;
import com.tictac.utils.StringUtils;

import java.util.Random;
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
        boolean exitPlay = false;
        printerService.printComputersTurn();
        if(!board.isRandomPlay()){
            playRandomValue(board);
        }else{
            exitPlay = blockPLayer(board);
            if(!exitPlay){
                exitPlay = playOppositeCorner(board, board.getBoardSize() - 1);
            }
            if(!exitPlay){
                computerNextMove(board);
            }
            boardService.checkIfPlayerWonTheGame(board);
        }
        printerService.printTicTacBoard(board);
    }

    private void computerNextMove(Board board){

        boolean exitPlay = false;
        exitPlay = boardService.iterateLineOrColumn(board, ConstsEnum.BOARD_LINE.getValue(), ConstsEnum.COMPUTER_NEXT_MOVE);

        if(!exitPlay){
            exitPlay = boardService.iterateLineOrColumn(board, ConstsEnum.BOARD_COLUMN.getValue(), ConstsEnum.COMPUTER_NEXT_MOVE);
        }
        if(!exitPlay){
            playRandomValue(board);
        }
    }

    private void playRandomValue(Board board){

        int boardSize = board.getBoardSize() - 1;
        Random random = new Random();

        do {
            int line = random.nextInt(boardSize);
            random = new Random();
            int column = random.nextInt(boardSize);

            if(boardService.validateIfBoardPositionIsEmpty(board, new int[]{line, column})){
                board.getBoard()[line][column] = board.getSymbolComputer();
                board.setRandomPlay(true);
            }
        }while (!board.isRandomPlay());

    }

    private boolean playOppositeCorner(Board board, int boardSize){
        if(!boardService.validateIfBoardPositionIsEmpty(board, new int[]{0, 0}) && !board.getBoard()[0][0].equals(board.getSymbolComputer())){
            if(boardService.validateIfBoardPositionIsEmpty(board, new int[]{boardSize, boardSize})){
                board.getBoard()[boardSize][boardSize] = board.getSymbolComputer();
                return true;
            }
        }

        if(!boardService.validateIfBoardPositionIsEmpty(board, new int[]{boardSize, boardSize}) && !board.getBoard()[boardSize][boardSize].equals(board.getSymbolComputer())){
            if(boardService.validateIfBoardPositionIsEmpty(board, new int[]{0, 0})){
                board.getBoard()[0][0] = board.getSymbolComputer();
                return true;
            }
        }

        if(!boardService.validateIfBoardPositionIsEmpty(board, new int[]{0, boardSize}) && !board.getBoard()[0][boardSize].equals(board.getSymbolComputer())){
            if(boardService.validateIfBoardPositionIsEmpty(board, new int[]{boardSize, 0})){
                board.getBoard()[boardSize][0] = board.getSymbolComputer();
                return true;
            }
        }

        if(!boardService.validateIfBoardPositionIsEmpty(board, new int[]{boardSize, 0}) && !board.getBoard()[boardSize][0].equals(board.getSymbolComputer())){
            if(boardService.validateIfBoardPositionIsEmpty(board, new int[]{0, boardSize})){
                board.getBoard()[0][boardSize] = board.getSymbolComputer();
                return true;
            }
        }
        return false;
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
