package com.tictac.service.impl;

import com.tictac.exception.InvalidValueException;
import com.tictac.model.Board;
import com.tictac.service.BoardService;
import com.tictac.utils.ConstsEnum;
import com.tictac.utils.FileUtils;
import com.tictac.utils.StringUtils;

import java.io.IOException;
import java.util.List;

public class BoardServiceImpl implements BoardService {

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

}
