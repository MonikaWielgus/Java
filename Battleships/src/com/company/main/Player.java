package com.company.main;

import java.util.Scanner;

public class Player {
    public Board opponentsBoard=new Board(false);
    public Board myBoard;

    public Player(){}
    public Player(String s){
        myBoard=new Board(s);
    }

    public String getMessage(){
        Scanner scanner=new Scanner(System.in);
        return scanner.next();
    }

    public String shotAtMe(String where){
        int column=Board.getColumn(where);
        int row=Board.getRow(where);

        if(checkIfMishit(column,row)){
            myBoard.board[column][row]=2;
            return "pudło;"+where;
        }
        if(checkIfHit(column, row)){
            myBoard.board[column][row]=3;
            myBoard.checked.clear();
            if(!myBoard.checkIfTheLastMast(where)){
                myBoard.numberOfMastsLeft--;
                return "trafiony;"+where;
            }
            else{
                myBoard.numberOfMastsLeft--;
                myBoard.numberOfShipsLeft--;
                if(myBoard.numberOfShipsLeft==0)
                    return "ostatni zatopiony";
                else
                    return "trafiony zatopiony;"+where;
            }
        }
        return "";
    }

    public boolean checkIfMishit(int column, int row){
        return myBoard.board[column][row] == 0 || myBoard.board[column][row] == 2;
    }

    public boolean checkIfHit(int column, int row){
        return myBoard.board[column][row] == 1 || myBoard.board[column][row] == 3;
    }

    public void mishitOpponent(String where){
        opponentsBoard.board[Board.getColumn(where)][Board.getRow(where)]=0;
    }

    public void hitOpponent(String where){
        opponentsBoard.board[Board.getColumn(where)][Board.getRow(where)]=1;
    }

    public void sunkOpponent(String where){
        int column=Board.getColumn(where);
        int row=Board.getRow(where);
        opponentsBoard.board[column][row]=1;
        opponentsBoard.checked.clear();
        opponentsBoard.block(column,row);
    }
}
