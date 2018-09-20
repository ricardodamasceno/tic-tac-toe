package com.tictac;

import com.tictac.model.Board;
import com.tictac.service.BoardService;
import com.tictac.service.PrinterService;
import com.tictac.service.impl.BoardServiceImpl;
import com.tictac.service.impl.PrinterServiceImpl;
import com.tictac.utils.ConstsEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardServiceImplTest {

    private Board board;
    private BoardService boardService = new BoardServiceImpl();
    private PrinterService printerService = new PrinterServiceImpl();

    @Before
    public void setUp(){
        board = prepareBoard();
    }

    @Test
    public void validateDiagonalBottom(){
        prepareDiagonalBottom();
        boardService.iterateDiagonalBottom(board, ConstsEnum.VALIDATE_DIAGONAL);
        Assert.assertEquals(board.isGameOver(), true);
        Assert.assertEquals(board.getWinner(), board.getSymbolPlayer1());
    }

    @Test
    public void blockDiagonalBottom(){
        prepareDiagonalBottom();
        resetGameOver();
        board.getBoard()[0][4] = null;
        boardService.iterateDiagonalBottom(board, ConstsEnum.BLOCK_DIAGONAL);
        Assert.assertEquals(board.isGameOver(), false);
        Assert.assertEquals(board.getWinner(), null);
        Assert.assertEquals(board.getBoard()[0][4], board.getSymbolComputer());
    }

    @Test
    public void markComputerNextPlayDiagonalBottom(){
        prepareComputerNextPlayDiagonalBottom();
        boardService.iterateDiagonalBottom(board, ConstsEnum.COMPUTER_NEXT_MOVE);
        Assert.assertEquals(board.isGameOver(), false);
        Assert.assertEquals(board.getWinner(), null);
        Assert.assertEquals(board.getBoard()[0][4], board.getSymbolComputer());
    }

    @Test
    public void validateDiagonalTop(){
        prepareDiagonalTop();
        boardService.iterateDiagonalTop(board, ConstsEnum.VALIDATE_DIAGONAL);
        Assert.assertEquals(board.isGameOver(), true);
        Assert.assertEquals(board.getWinner(), board.getSymbolPlayer2());
    }

    @Test
    public void blockDiagonalTop(){
        prepareDiagonalTop();
        resetGameOver();
        board.getBoard()[4][4] = null;
        boardService.iterateDiagonalTop(board, ConstsEnum.BLOCK_DIAGONAL);
        Assert.assertEquals(board.isGameOver(), false);
        Assert.assertEquals(board.getWinner(), null);
        Assert.assertEquals(board.getBoard()[4][4], board.getSymbolComputer());
    }

    @Test
    public void markComputerNextPlayDiagonalTop(){
        prepareComputerNextPlay();
        boardService.iterateDiagonalTop(board, ConstsEnum.COMPUTER_NEXT_MOVE);
        Assert.assertEquals(board.isGameOver(), false);
        Assert.assertEquals(board.getWinner(), null);
        Assert.assertEquals(board.getBoard()[4][4], board.getSymbolComputer());
    }

    @Test
    public void checkGameOverByColumn(){
        prepareCheckColumn();
        boardService.iterateLineOrColumn(board, ConstsEnum.BOARD_COLUMN.getValue(), ConstsEnum.VALIDATE_LINE_OR_COLUMN );
        Assert.assertEquals(board.isGameOver(), true);
        Assert.assertEquals(board.getWinner(), board.getSymbolComputer());
    }

    @Test
    public void checkGameOverByLine(){
        prepareCheckLine();
        boardService.iterateLineOrColumn(board, ConstsEnum.BOARD_LINE.getValue(), ConstsEnum.VALIDATE_LINE_OR_COLUMN );
        Assert.assertEquals(board.isGameOver(), true);
        Assert.assertEquals(board.getWinner(), board.getSymbolPlayer1());
    }

    @Test
    public void blockLine(){
        prepareBlockLine();
        boardService.iterateLineOrColumn(board, ConstsEnum.BOARD_LINE.getValue(), ConstsEnum.BLOCK_LINE_OR_COLUMN );
        Assert.assertEquals(board.isGameOver(), false);
        Assert.assertEquals(board.getWinner(), null);
        Assert.assertEquals(board.getBoard()[0][4], board.getSymbolComputer());
    }

    @Test
    public void blockColumn(){
        prepareBlockColumn();
        boardService.iterateLineOrColumn(board, ConstsEnum.BOARD_COLUMN.getValue(), ConstsEnum.BLOCK_LINE_OR_COLUMN );
        Assert.assertEquals(board.isGameOver(), false);
        Assert.assertEquals(board.getWinner(), null);
        Assert.assertEquals(board.getBoard()[4][0], board.getSymbolComputer());
    }

    @Test
    public void computerNextMoveColumn(){
        prepareComputerNextPlay();
        boardService.iterateLineOrColumn(board, ConstsEnum.BOARD_COLUMN.getValue(), ConstsEnum.COMPUTER_NEXT_MOVE );
        Assert.assertEquals(board.isGameOver(), false);
        Assert.assertEquals(board.getWinner(), null);
        Assert.assertEquals(board.getBoard()[4][0], board.getSymbolComputer());
    }

    @Test
    public void computerNextMoveLine(){
        prepareComputerNextPlay();
        boardService.iterateLineOrColumn(board, ConstsEnum.BOARD_LINE.getValue(), ConstsEnum.COMPUTER_NEXT_MOVE );
        Assert.assertEquals(board.isGameOver(), false);
        Assert.assertEquals(board.getWinner(), null);
        Assert.assertEquals(board.getBoard()[0][4], board.getSymbolComputer());
    }

    private Board prepareBoard(){
        Board board = new Board();
        board.setSymbolPlayer1("A");
        board.setSymbolPlayer2("B");
        board.setSymbolComputer("C");
        board.setBoardSize(5);
        board.setBoard(new String[board.getBoardSize()][board.getBoardSize()]);
        return board;
    }

    private void eraseBoard(){
        board.setBoard(new String[board.getBoardSize()][board.getBoardSize()]);
    }

    private void prepareComputerNextPlay(){
        eraseBoard();
        resetGameOver();
        board.getBoard()[0][0] = board.getSymbolComputer();
    }

    private void prepareComputerNextPlayDiagonalBottom(){
        eraseBoard();
        resetGameOver();
        board.getBoard()[4][0] = board.getSymbolComputer();
    }

    private void prepareDiagonalBottom(){
        eraseBoard();
        board.getBoard()[4][0] = board.getSymbolPlayer1();
        board.getBoard()[3][1] = board.getSymbolPlayer1();
        board.getBoard()[2][2] = board.getSymbolPlayer1();
        board.getBoard()[1][3] = board.getSymbolPlayer1();
        board.getBoard()[0][4] = board.getSymbolPlayer1();
    }

    private void prepareDiagonalTop(){
        eraseBoard();
        board.getBoard()[0][0] = board.getSymbolPlayer2();
        board.getBoard()[1][1] = board.getSymbolPlayer2();
        board.getBoard()[2][2] = board.getSymbolPlayer2();
        board.getBoard()[3][3] = board.getSymbolPlayer2();
        board.getBoard()[4][4] = board.getSymbolPlayer2();
    }

    private void prepareCheckColumn(){
        eraseBoard();
        board.getBoard()[0][0] = board.getSymbolComputer();
        board.getBoard()[1][0] = board.getSymbolComputer();
        board.getBoard()[2][0] = board.getSymbolComputer();
        board.getBoard()[3][0] = board.getSymbolComputer();
        board.getBoard()[4][0] = board.getSymbolComputer();
    }

    private void prepareBlockColumn(){
        eraseBoard();
        board.getBoard()[0][0] = board.getSymbolPlayer1();
        board.getBoard()[1][0] = board.getSymbolPlayer1();
        board.getBoard()[2][0] = board.getSymbolPlayer1();
        board.getBoard()[3][0] = board.getSymbolPlayer1();
        board.getBoard()[4][0] = null;
        resetGameOver();
    }

    private void prepareCheckLine(){
        eraseBoard();
        board.getBoard()[0][0] = board.getSymbolPlayer1();
        board.getBoard()[0][1] = board.getSymbolPlayer1();
        board.getBoard()[0][2] = board.getSymbolPlayer1();
        board.getBoard()[0][3] = board.getSymbolPlayer1();
        board.getBoard()[0][4] = board.getSymbolPlayer1();
    }

    private void prepareBlockLine(){
        eraseBoard();
        board.getBoard()[0][0] = board.getSymbolPlayer2();
        board.getBoard()[0][1] = board.getSymbolPlayer2();
        board.getBoard()[0][2] = board.getSymbolPlayer2();
        board.getBoard()[0][3] = board.getSymbolPlayer2();
        resetGameOver();
    }

    private void resetGameOver(){
        board.setGameOver(false);
        board.setWinner(null);
    }

}
