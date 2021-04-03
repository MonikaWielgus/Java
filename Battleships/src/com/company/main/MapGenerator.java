package com.company.main;

import java.util.Random;

public class MapGenerator {
    private static final Random rand=new Random();

    public static String generateMap(){
        int[] board=new int[100];
        placeShips(board, 4, 1);
        placeShips(board,3,2);
        placeShips(board,2,3);
        placeShips(board,1,4);

        return mapToString(board);
    }

    private static void placeShips(int[] board, int numberOfMasts, int numberOfShips){
        for(int i=0; i<numberOfShips; i++){
            placeShip(board,numberOfMasts);
        }
    }

    private static void placeShip(int[] board, int numberOfMasts){
        boolean done=false;
        int where;
        int []temp=new int[100];

        while(!done){ //it is done when ship is placed
            where=rand.nextInt(100);
            System.arraycopy(board,0,temp,0,100);

            if(shipPlaced(temp,where,numberOfMasts)){
                done=true;
                System.arraycopy(temp,0,board,0,100);
                blockBoard(board);
            }
        }
    }

    private static boolean shipPlaced(int[] board, int realPlace, int numberOfMastsLeft){
        if(numberOfMastsLeft==0)
            return true; //ship is placed

        if(board[realPlace]==0){ //this place is free
            board[realPlace]=2; //putting mast
            int nextPlace=randomNextPlace(realPlace);

            if(nextPlace==-1) //going beyond the board
                return false;
            else{ //there is a potentially suitable place
                if(board[nextPlace]==0) //it is not taken
                    return shipPlaced(board, nextPlace, numberOfMastsLeft - 1);
                else
                    return false; //it is taken
            }
        }
        return false;
    }

    private static void blockBoard(int[] board){
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                if(board[realPlace(i,j)]==2){ //there is a mast
                    blockExactPlace(board,realPlace(i+1,j));
                    blockExactPlace(board,realPlace(i+1,j+1));
                    blockExactPlace(board,realPlace(i,j+1));
                    blockExactPlace(board,realPlace(i-1,j+1));
                    blockExactPlace(board,realPlace(i-1,j));
                    blockExactPlace(board,realPlace(i-1,j-1));
                    blockExactPlace(board,realPlace(i,j-1));
                    blockExactPlace(board,realPlace(i+1,j-1));
                }
            }
        }
    }

    private static void blockExactPlace(int[] board, int realPlace){
        if(checkIfNotBeyondTheBoard(realPlace)&&board[realPlace]==0)
            board[realPlace]=1;
    }

    private static int randomNextPlace(int realPlace){
        int next=rand.nextInt(3);
        int x=getX(realPlace);
        int y=getY(realPlace);
        return switch (next) {
            case 0 -> randomNextPlaceHelper(realPlace(x + 1, y));
            case 1 -> randomNextPlaceHelper(realPlace(x - 1, y));
            case 2 -> randomNextPlaceHelper(realPlace(x, y + 1));
            case 3 -> randomNextPlaceHelper(realPlace(x, y - 1));
            default -> -1;
        };
    }

    private static int randomNextPlaceHelper(int realPlace){
        if(checkIfNotBeyondTheBoard(realPlace))
            return realPlace;
        else
            return -1;

    }

    private static String mapToString(int []board){
        StringBuilder result=new StringBuilder();
        for (int value : board) {
            if (value == 2)
                result.append('#');
            else
                result.append('.');
        }
        return String.valueOf(result);
    }

    private static boolean checkIfNotBeyondTheBoard(int realPlace) {
        int x=getX(realPlace);
        int y=getY(realPlace);
        return x >= 0 && x <= 9 && y >= 0 && y <= 9;
    }

    private static int getX(int number){
        return number%10;
    }

    private static int getY(int number){
        return number/10;
    }

    private static int realPlace(int x, int y){
        return y*10+x;
    }
}
