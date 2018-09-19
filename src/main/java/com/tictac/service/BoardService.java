package com.tictac.service;

import com.tictac.model.Board;
import com.tictac.utils.ConstsEnum;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    Board fillBoardEntity() throws IOException;
    boolean iterateDiagonalBottom(Board board, ConstsEnum operationType);
    boolean iterateDiagonalTop(Board board, ConstsEnum operationType);
    Boolean iterateLineOrColumn(Board board, String typeCheck, ConstsEnum operationType);
    boolean validateIfBoardPositionIsEmpty(Board board, int[] position);
    void registerPlayPosition(Board board, int [] position, ConstsEnum player);
    void checkIfPlayerWonTheGame(Board board);
    boolean blockLine(Board board, List<String> lineValues, Integer[] voidPosition, Integer line, Integer column, String typeCheck);

}
