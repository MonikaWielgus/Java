package com.company.main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameHOST {
    Socket clientSocket;
    ServerSocket serverSocket;
    InputStream input;
    BufferedReader reader;
    OutputStream output;
    PrintWriter writerToSecondPlayer;

    public Player hostPlayer;
    public Player secondPlayer;
    int PORT;

    public GameHOST(Player one, Player two, int port) throws IOException {
        serverSocket=new ServerSocket(port);

        while(true){
            Socket socket=serverSocket.accept();
            if(socket!=null){
                clientSocket=socket;
                break;
            }
        }

        input=clientSocket.getInputStream();
        reader=new BufferedReader(new InputStreamReader(input));

        output=clientSocket.getOutputStream();
        writerToSecondPlayer=new PrintWriter(output,true);

        hostPlayer=one;
        secondPlayer=two;
        PORT=port;
        secondPlayer.myBoard=new Board(reader.readLine());

        start();
    }

    public void start() throws IOException{
        boolean hostPlayerWins=false;
        boolean secondPlayerWins=false;
        while(true){

            printForHostPlayer();

            String hostPlayerMessage=getCommandFromHostPlayer();
            String analyzedMessageFromHostPlayer=analyzeMessage(secondPlayer,hostPlayerMessage);

            if(analyzedMessageFromHostPlayer.equals("ostatni zatopiony"))
                hostPlayerWins=true;

            writerToSecondPlayer.println(messageToSecondPlayer(hostPlayerMessage,analyzedMessageFromHostPlayer));
            System.out.println(analyzedMessageFromHostPlayer);
            analyzeMessage(hostPlayer,analyzedMessageFromHostPlayer);

            StringBuilder messageForSecondPlayer=printForSecondPlayer();

            if(hostPlayerWins){
                messageForSecondPlayer.append("przegrana");
                writerToSecondPlayer.println(messageForSecondPlayer.toString());
            }
            else{
                writerToSecondPlayer.println(messageForSecondPlayer);
                String messageFromSecondPlayer=reader.readLine();

                String analyzedMessageFromSecondPlayer=analyzeMessage(hostPlayer,messageFromSecondPlayer);
                writerToSecondPlayer.println(analyzedMessageFromSecondPlayer);
                if(analyzedMessageFromSecondPlayer.equals("ostatni zatopiony"))
                    secondPlayerWins=true;
                if(!secondPlayerWins)
                    analyzeMessage(secondPlayer,analyzedMessageFromSecondPlayer);
                System.out.println("\nPrzeciwnik:\n"+messageFromSecondPlayer+"\n"+analyzedMessageFromSecondPlayer+"\n");

            }

            if(hostPlayerWins){
                System.out.println("\nwygrana");
                break;
            }
            if(secondPlayerWins){
                System.out.println("przegrana");
                break;
            }
        }
        if(clientSocket.isClosed())
            serverSocket.close();
    }

    public void printForHostPlayer(){
        System.out.println("Twoja plansza");
        System.out.println(hostPlayer.myBoard.printBoard());
        System.out.println("Plansza przeciwnika");
        System.out.println(hostPlayer.opponentsBoard.printBoard());
        System.out.println("Gdzie chcesz strzelić? Format: strzelam;A1");
    }

    public StringBuilder printForSecondPlayer(){
        StringBuilder message=new StringBuilder();
        message.append("Twoja plansza\n");
        message.append(secondPlayer.myBoard.printBoard());
        message.append("Plansza przeciwnika\n");
        message.append(secondPlayer.opponentsBoard.printBoard());
        return message;
    }

    public String messageToSecondPlayer(String hostPlayerMessage, String returnMessage){
        String messageToSecondPlayer = "\nPrzeciwnik:\n" +
                hostPlayerMessage +
                "\n" +
                returnMessage +
                "\n";
        return messageToSecondPlayer;
    }

    public String getCommandFromHostPlayer(){
        return hostPlayer.getMessage();
    }

    public String analyzeMessage(Player toWho, String message){
        String []temp=message.split(";");
        if(temp.length<2)
            return "Błędna komenda";
        String command=temp[0];
        String where=temp[1];
        switch(command){
            case "strzelam":
                return toWho.shotAtMe(where);
            case "pudło":
                toWho.mishitOpponent(where);
                break;
            case "trafiony":
                toWho.hitOpponent(where);
                break;
            case "trafiony zatopiony":
                toWho.sunkOpponent(where);
        }
        return "Błędna komenda";
    }
}
