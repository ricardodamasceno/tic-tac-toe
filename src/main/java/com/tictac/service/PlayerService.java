package com.tictac.service;

import com.tictac.model.Board;
import com.tictac.utils.ConstsEnum;

public interface PlayerService {

    boolean startGame();
    void playHuman(Board board, ConstsEnum player);

}
