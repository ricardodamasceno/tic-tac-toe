package com.tictac;

import com.tictac.service.TicTacService;
import com.tictac.service.impl.TicTacServiceImpl;

import java.io.IOException;

public class Application {

    private static TicTacService ticTacService = new TicTacServiceImpl();

    public static void main(String[] args) throws IOException {
        ticTacService.playGame();
    }

}
