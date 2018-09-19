package com.tictac.service.impl;

import com.tictac.exception.InvalidValueException;
import com.tictac.model.Board;
import com.tictac.service.BoardService;
import com.tictac.service.PrinterService;
import com.tictac.utils.ConstsEnum;
import com.tictac.utils.FileUtils;
import com.tictac.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
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
        iterateLineOrColumn(board, ConstsEnum.BOARD_LINE.getValue(),   ConstsEnum.VALIDATE_LINE_OR_COLUMN);
        if(!board.isGameOver()){
            iterateLineOrColumn(board, ConstsEnum.BOARD_COLUMN.getValue(), ConstsEnum.VALIDATE_LINE_OR_COLUMN);
            if(!board.isGameOver()){
                checkIfPlayerWonbyDiagonal(board);
            }
        }
        if(board.isGameOver()){
            printerService.printPlayerWonTheGame(board);
        }
    }

    private void checkIfPlayerWonbyDiagonal (Board board){
        int boardSize = board.getBoardSize() - 1;

        if(board.getBoard()[0][0] != null){
            iterateDiagonalTop(board, ConstsEnum.VALIDATE_DIAGONAL);
        }
        if(!board.isGameOver() && board.getBoard()[boardSize][0] != null){
            iterateDiagonalBottom(board, ConstsEnum.VALIDATE_DIAGONAL);
        }
    }

    private void iterateDiagonalInnerMethod(Board board, ConstsEnum operationType, List<String> lineValues, int[] voidPosotion, int line, int column, boolean exitMethod ){
        if(operationType.equals(ConstsEnum.VALIDATE_DIAGONAL)){
            checkLineOrColumn(board, lineValues, line, column, ConstsEnum.BOARD_COLUMN.getValue());
        }else if(operationType.equals(ConstsEnum.BLOCK_DIAGONAL)){
            exitMethod = blockLine(board, lineValues, voidPosotion, column, line, ConstsEnum.BOARD_LINE.getValue());
        }
    }

    public boolean iterateDiagonalBottom(Board board, ConstsEnum operationType){

        List<String> lineValues = new ArrayList<>();
        boolean exitMethod = false;
        int boardSize = board.getBoardSize() - 1;
        int[] voidPosition = new int[2];
        int line = 0;

        for(int count = boardSize; count >= 0; count--){
            iterateDiagonalInnerMethod(board, operationType, lineValues, voidPosition, line, count, exitMethod);
            if(exitMethod){
                break;
            }
            line++;
        }
        return exitMethod;
    }

    public boolean iterateDiagonalTop(Board board, ConstsEnum operationType){

        List<String> lineValues = new ArrayList<>();
        int boardSize = board.getBoardSize() -1;
        boolean exitMethod = false;
        int[] voidPosition = new int[2];

        for(int count = 0; count <= boardSize ; count++){
            iterateDiagonalInnerMethod(board, operationType, lineValues,voidPosition, count, count, exitMethod);
            if(exitMethod){
                break;
            }
        }
        return exitMethod;
    }

    public Boolean iterateLineOrColumn(Board board, String typeCheck, ConstsEnum operationType){

        List<String> lineValues;
        int boardSize = board.getBoardSize() -1;
        boolean exitMethod = false;
        int[] voidPosition = new int[2];

        if(board.getBoard() != null){
            for(int line = 0; line <= boardSize; line++) {
                if(exitMethod){
                    break;
                }
                lineValues = new ArrayList<>();
                for (int column = 0; column <= boardSize; column++) {
                    if(operationType.equals(ConstsEnum.VALIDATE_LINE_OR_COLUMN)){
                        checkLineOrColumn(board, lineValues, line, column, typeCheck);
                    }else if(operationType.equals(ConstsEnum.BLOCK_LINE_OR_COLUMN)){
                        exitMethod = blockLine(board, lineValues, voidPosition, line, column, typeCheck);
                        if(exitMethod){
                            break;
                        }
                    }
                }
            }
        }
        return exitMethod;
    }

    private void checkLineOrColumn(Board board, List<String> lineValues, Integer line, Integer column, String typeCheck){
        if(getValueFromPositionByType(board, line, column, typeCheck) != null){
            lineValues.add(getValueFromPositionByType(board, line, column, typeCheck));
        }
        if(validateLastIterationColumn(board, typeCheck, line, column)){
            if(lineValues.size() == board.getBoardSize()){
                if(StringUtils.checkSameValues(lineValues)){
                    board.setGameOver(true);
                    board.setWinner(lineValues.get(0));
                }
            }
        }
    }

    private String getValueFromPositionByType(Board board, int line, int column, String typeCheck){
        return typeCheck.equals(ConstsEnum.BOARD_LINE.getValue()) ? board.getBoard()[line][column] : board.getBoard()[column][line];
    }

    public boolean blockLine(Board board, List<String> lineValues, int[] voidPosition, Integer line, Integer column, String typeCheck) {

        boolean result = false;
        String positionValue = getValueFromPositionByType(board, line, column, typeCheck);

        if (!StringUtils.isEmpty(positionValue)) {
            lineValues.add(positionValue);
        } else {
            voidPosition[0] = line;
            voidPosition[1] = column;
        }
        if(validateLastIterationColumn(board, typeCheck, line, column)) {
            if (lineValues.size() == (board.getBoardSize() - 1)) {
                if (StringUtils.checkSameValues(lineValues)) {
                    board.getBoard()[voidPosition[0]][voidPosition[1]] = board.getSymbolComputer();
                    result = true;
                }
            }
        }

        return result;
    }

    private boolean validateLastIterationColumn(Board board, String typeCheck, Integer line, Integer column){
        boolean result = false;
        int boardSize = board.getBoardSize() -1;
        if(typeCheck.equals(ConstsEnum.BOARD_LINE.getValue())){
            if(column == boardSize){
                result = true;
            }
        }else if(typeCheck.equals(ConstsEnum.BOARD_COLUMN.getValue())){
            if(line == boardSize){
                result = true;
            }
        }
        return result;
    }

}
