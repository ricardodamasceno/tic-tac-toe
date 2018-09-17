package com.tictac.utils;

public enum ConstsEnum {

    Y               ("y", ""),
    N               ("n", ""),
    BOARD_SIZE      ("boardSize", ""),
    BOARD_COLUMN    ("column", ""),
    BOARD_LINE      ("line", ""),
    FIRST_PLAYER    ("firstPlayerSymbol", "First Player"),
    SECOND_PLAYER   ("secondPlayerSymbol", "Second Player"),
    COMPUTER        ("computerSymbol", "Computer"),
    FILE_PATH       ("src/main/resources/config.txt", "");

    private final String value;
    private final String name;

    ConstsEnum(String value, String name){
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
