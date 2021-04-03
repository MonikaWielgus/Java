package com.company.main;

import com.company.main.MapGenerator;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException { //-mode server/client -port N
	    Scanner scanner=new Scanner(System.in);
	    String MODE=args[1];
	    int PORT=Integer.parseInt(args[3]);
	    String board=MapGenerator.generateMap();

	    if(MODE.equals("server")){
            Player hostPlayer=new Player(board);
            Player guestPlayer=new Player();
            new GameHOST(hostPlayer,guestPlayer,PORT);
        }
	    else if(MODE.equals("client")){
            Socket socket=new Socket(java.net.InetAddress.getByName("localhost").getHostName(),PORT);
            OutputStream os=socket.getOutputStream();
            PrintWriter writerToServer=new PrintWriter(os,true);

            writeToServer(writerToServer,board);

            while(true){
                InputStream is=socket.getInputStream();
                BufferedReader readerFromServer=new BufferedReader(new InputStreamReader(is));
                readHostPlayerMove(readerFromServer);

                if(readBoards(readerFromServer))
                    break;

                shoot(writerToServer,scanner);

                String feedback=getFeedback(readerFromServer);
                if(feedback.equals("ostatni zatopiony")){
                    System.out.println("wygrana");
                    break;
                }
            }
            socket.close();
        }
    }

    public static void writeToServer(PrintWriter writer, String message){
        writer.println(message);
    }

    public static void readHostPlayerMove(BufferedReader reader) throws IOException {
        for(int i=0; i<6; i++)
            System.out.println(reader.readLine());
    }

    public static boolean readBoards(BufferedReader reader) throws IOException {
        String message=reader.readLine();
        System.out.println(message);
        while(!message.equals("")){ //reading received boards
            if(message.equals("przegrana")){
                return true;
            }
            message=reader.readLine();
            System.out.println(message);
        }
        return false;
    }

    public static void shoot(PrintWriter writer, Scanner scanner){
        System.out.println("Gdzie chcesz strzelić? Format: strzelam;A1");
        writer.println(scanner.next());
    }

    public static String getFeedback(BufferedReader reader) throws IOException {
        String message=reader.readLine();
        System.out.println(message);
        return message;
    }
}