package com.tictac.service.impl;

import com.tictac.exception.InvalidValueException;
import com.tictac.model.Board;
import com.tictac.service.BoardService;
import com.tictac.service.PrinterService;
import com.tictac.utils.ConstsEnum;
import com.tictac.utils.FileUtils;
import com.tictac.utils.StringUtils;

import java.io.IOException;
import java.util.List;

public class BoardServiceImpl implements BoardService {

    PrinterService printerService = new PrinterServiceImpl();


    public Board fillBoardEntity() throws IOException {

        List<String> fileValues = FileUtils.readDataFromFile();

        if(fileValues != null && !fileValues.isEmpty()){

            Board board = new Board();

            fileValues.forEach(value -> {
                if(value.contains(ConstsEnum.FIRST_PLAYER.getValue())){
                    board.setSymbolPlayer1(StringUtils.getValueFromFileLine(value));
                } else if(value.contains(ConstsEnum.SECOND_PLAYER.getValue())){
                    board.setSymbolPlayer2(StringUtils.getValueFromFileLine(value));
                } else if(value.contains(ConstsEnum.COMPUTER.getValue())){
                    board.setSymbolComputer(StringUtils.getValueFromFileLine(value));
                } else if(value.contains(ConstsEnum.BOARD_SIZE.getValue())){
                    //Implementar exceção
                    Integer boardSize = Integer.parseInt(StringUtils.getValueFromFileLine(value));
                    if(validateBoardSize(boardSize)){
                        board.setBoardSize(boardSize);
                        board.setBoard(new String[boardSize][boardSize]);
                    }
                }
            });
            return board;
        }
        return null;
    }

    private boolean validateBoardSize(Integer boardSize){
        return boardSize >= 3 && boardSize <= 10;
    }

    public boolean validateIfBoardPositionIsEmpty(Board board, int[] position){
        String boardPositionValue = board.getBoard()[position[0]][position[1]];
        return StringUtils.isEmpty(boardPositionValue);
    }

    public void registerPlayPosition(Board board, int [] position, ConstsEnum player){
        try{
            if(player.equals(ConstsEnum.FIRST_PLAYER)){
                board.getBoard()[position[0]][position[1]] = board.getSymbolPlayer1();
            }else if(player.equals(ConstsEnum.SECOND_PLAYER)){
                board.getBoard()[position[0]][position[1]] = board.getSymbolPlayer2();
            }else if(player.equals(ConstsEnum.COMPUTER)){
                board.getBoard()[position[0]][position[1]] = board.getSymbolComputer();
            }
        }catch (Exception e){
            throw new InvalidValueException();
        }
    }

    public void checkIfPlayerWonTheGame(Board board){
        checkIfPlayerWonbyLineOrColumn(board, ConstsEnum.BOARD_LINE.getValue());
        checkIfPlayerWonbyLineOrColumn(board, ConstsEnum.BOARD_COLUMN.getValue());
        checkIfPlayerWonbyDiagonal(board);
        if(board.isGameOver()){
            printerService.printPlayerWonTheGame(board);
        }
    }

    private void checkIfPlayerWonbyDiagonal (Board board){
        if(board.getBoard()[0][0] != null){
            checkDiagonalTop(board);
        }
        if(!board.isGameOver() && board.getBoard()[board.getBoardSize() - 1][0] != null){
            checkDiagonalBottom(board);
        }
    }

    private void checkDiagonalBottom(Board board){

        int result = 1;
        int column = board.getBoardSize() - 1;
        int line = 0;

        String playerSymbol = board.getBoard()[column][line];
        column--;
        line++;

        for(int count = (board.getBoardSize() - 2); count >= 0; count--){
            if(playerSymbol.equals(board.getBoard()[count][line])){
                result++;
                line++;
            }else{
                break;
            }
        }

        if(result == board.getBoardSize()){
            board.setGameOver(true);
            board.setWinner(playerSymbol);
        }
    }

    private void checkDiagonalTop(Board board){

        int result = 0;

        String playerSymbol = board.getBoard()[0][0];
        result++;
        for(int count = 1; count < board.getBoardSize(); count++){
            if(playerSymbol.equals(board.getBoard()[count][count])){
                result++;
            }else{
                break;
            }
        }

        if(result == board.getBoardSize()){
            board.setGameOver(true);
            board.setWinner(playerSymbol);
        }

    }

    private void checkIfPlayerWonbyLineOrColumn(Board board, String typeCheck){

        String playerSymbol = "";
        int count = 0;

        if(board.getBoard() != null){
            for(int line = 0; line < board.getBoardSize(); line++) {
                for (int column = 0; column < board.getBoardSize(); column++) {
                    if(getValueFromPositionByType(board, line, column, typeCheck) != null){
                        if(StringUtils.isEmpty(playerSymbol)){
                            playerSymbol = getValueFromPositionByType(board, line, column, typeCheck);
                            count++;
                        }else{
                            if(playerSymbol.equals(getValueFromPositionByType(board, line, column, typeCheck))){
                                count++;
                            }else{
                                break;
                            }
                        }
                    } else{
                        break;
                    }
                }
                if(count == board.getBoardSize()){
                    board.setGameOver(true);
                    board.setWinner(playerSymbol);
                }
            }
        }
    }

    private String getValueFromPositionByType(Board board, int line, int column, String typeCheck){
        return typeCheck.equals("line") ? board.getBoard()[line][column] : board.getBoard()[column][line];
    }

}
