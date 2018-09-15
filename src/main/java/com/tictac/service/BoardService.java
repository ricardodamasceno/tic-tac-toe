package com.tictac.service;

import com.tictac.model.Board;
import com.tictac.utils.ConstsEnum;

import java.io.IOException;

public interface BoardService {

    Board fillBoardEntity() throws IOException;
    boolean validateIfBoardPositionIsEmpty(Board board, int[] position);
    void registerPlayPosition(Board board, int [] position, ConstsEnum player);

}
