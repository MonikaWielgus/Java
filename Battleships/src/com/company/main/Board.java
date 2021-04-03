package com.company.main;

import java.util.HashSet;

public class Board {
    int[][] board; //0-empty, 1-ship, 3-hit, 4-question mark
    int numberOfMastsLeft = 20;
    int numberOfShipsLeft = 10;

    HashSet<String> checked;

    public Board() {
        board = new int[10][10];
        checked=new HashSet<>();
    }

    public Board(String map) {
        board = new int[10][10];
        char[] letters = map.toCharArray();

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                if (letters[i * 10 + j] == '#')
                    board[i][j] = 1;
        checked=new HashSet<>();
    }

    public Board(boolean b) { //board with question marks
        board = new int[10][10];

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                board[i][j] = 4;
        checked=new HashSet<>();
    }

    public String printBoard() {
        StringBuilder result = new StringBuilder("  ");
        for (int i = 1; i <= 10; i++){
            result.append(i);
            result.append(" ");
        }

        result.append("\n");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j <= 10; j++) {
                if (j == 0){
                    result.append((char) (i + 65));
                    result.append(" ");
                }
                else {
                    switch (board[i][j - 1]) {
                        case 0 -> result.append(". ");
                        case 1 -> result.append("# ");
                        case 2 -> result.append("~ ");
                        case 3 -> result.append("@ ");
                        case 4 -> result.append("? ");
                    }
                }
            }
            result.append(" \n");
        }
        return result.toString();
    }

    public boolean checkIfTheLastMast(String where){
        checked.add(where);
        int column=getColumn(where);
        int row=getRow(where);

        if(checkIfTheLastHelper(column - 1, row - 1))
            return false;
        if(checkIfTheLastHelper(column, row - 1))
            return false;
        if(checkIfTheLastHelper(column + 1, row - 1))
            return false;
        if(checkIfTheLastHelper(column + 1, row))
            return false;
        if(checkIfTheLastHelper(column + 1, row + 1))
            return false;
        if(checkIfTheLastHelper(column, row + 1))
            return false;
        if(checkIfTheLastHelper(column - 1, row + 1))
            return false;
        if(checkIfTheLastHelper(column - 1, row))
            return false;

        return true;

    }

    public boolean checkIfTheLastHelper(int i, int j){
        if(checkIfInTheBoard(i, j)){
            if(board[i][j]==1)
                return true;
            if(board[i][j]==3&&!checked.contains(getPlace(i,j)))
                return !checkIfTheLastMast(getPlace(i, j));
        }
        return false;
    }

    public void block(int i, int j){
        checked.add(getPlace(i,j));
        blockHelper(i+1,j);
        blockHelper(i+1,j+1);
        blockHelper(i+1,j-1);
        blockHelper(i,j-1);
        blockHelper(i,j+1);
        blockHelper(i-1,j-1);
        blockHelper(i-1,j);
        blockHelper(i-1,j+1);
    }

    public void blockHelper(int column, int row){
        if(checkIfInTheBoard(column, row)){
            if(board[column][row]==4)
                board[column][row]=0;
            if(board[column][row]==1&&!checked.contains(getPlace(column,row)))
                block(column,row);
        }
    }

    public static boolean checkIfInTheBoard(int i, int j){
        return i >= 0 && j >= 0 && i <= 9 && j <= 9;
    }

    public static String getPlace(int i, int j){
        return String.valueOf((char)(i+65))+(j+1);
    }

    public static int getColumn(String s){ //A,B,C,...,J
        return Integer.parseInt(String.valueOf(s.charAt(0)-65));
    }

    public static int getRow(String s){ //1,2,3,...,10
        return Integer.parseInt(s.substring(1))-1;
    }

}