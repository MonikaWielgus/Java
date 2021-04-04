package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String... args) throws IOException {
        if (args.length == 0) {
            System.out.println("Please specify command.");
            System.exit(1);
        }

        String currentDirectory = System.getProperty("user.dir"); //we are here

        ArrayList<String> commands = new ArrayList<String>(Arrays.asList(args)); //commands list
        handleTheCommand(commands,currentDirectory);

    }

    public static void handleTheCommand(ArrayList<String> commands, String currentDirectory) throws IOException {
        switch (commands.get(0)) {
            case "init" -> init(currentDirectory);
            case "add" -> addCommand(commands, currentDirectory);
            case "detach" -> detachCommand(commands, currentDirectory);
            case "checkout" -> checkoutCommand(commands, currentDirectory);
            case "commit" -> commitCommand(commands, currentDirectory);
            case "history" -> historyCommand(commands, currentDirectory);
            case "version" -> versionCommand(commands, currentDirectory);
            default -> {
                System.out.println("Unknown command " + commands.get(0) + ".");
                System.exit(1);
            }
        }
    }

    public static String getMessage(ArrayList<String> commands){
        String message="";
        if (commands.size() > 2 && commands.get(2).equals("-m")) {
            if (commands.size() > 3) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 3; i < commands.size(); i++) {
                    stringBuilder.append(commands.get(i)).append(" ");
                }
                message = stringBuilder.toString();
                message=message.trim();
            }
        }
        return message;
    }

    public static void addCommand(ArrayList<String> commands, String currentDirectory) throws IOException {
        if(commands.size()>1){
            Pattern pattern=Pattern.compile("\\w*\\.\\w*"); //for example file.txt
            Matcher matcher = pattern.matcher(commands.get(1));
            if(matcher.matches()) { //so we have file name
                String message=getMessage(commands);
                add(currentDirectory,matcher.group(0),message);
            }
        }
        else{
            System.out.println("Please specify file to add.");
            System.exit(21);
        }
    }

    public static void detachCommand(ArrayList<String> commands, String currentDirectory) throws IOException {
        if(commands.size()>1){
            Pattern pattern=Pattern.compile("\\w*\\.\\w*");
            Matcher matcher = pattern.matcher(commands.get(1));
            if(matcher.matches()) {
                String message=getMessage(commands);
                detach(currentDirectory,matcher.group(0),message);
            }
        }
        else{
            System.out.println("Please specify file to detach.");
            System.exit(30);
        }
    }

    public static void checkoutCommand(ArrayList<String> commands, String currentDirectory) throws IOException {
        if(commands.size()>1){
            Pattern pattern=Pattern.compile("[0-9]*");
            Matcher matcher = pattern.matcher(commands.get(1));
            if(matcher.matches()){
                checkout(currentDirectory,Integer.parseInt(commands.get(1)));
            }
            else{
                System.out.println("Invalid version number: "+commands.get(1)+".");
                System.exit(40);
            }
        }
    }

    public static void commitCommand(ArrayList<String> commands, String currentDirectory) throws IOException {
        if(commands.size()>1){
            Pattern pattern=Pattern.compile("\\w*\\.\\w*");
            Matcher matcher = pattern.matcher(commands.get(1));
            if(matcher.matches()) {
                String message=getMessage(commands);
                commit(currentDirectory,matcher.group(0),message);
            }
        }
        else{
            System.out.println("Please specify file to commit.");
            System.exit(50);
        }
    }

    public static void historyCommand(ArrayList<String> commands, String currentDirectory) throws FileNotFoundException {
        if(commands.size()>1&&commands.get(1).equals("-last")&&commands.size()>2)
            history(currentDirectory,Integer.parseInt(commands.get(2)));
        else
            history(currentDirectory,-1);
    }

    public static void versionCommand(ArrayList<String> commands, String currentDirectory) throws FileNotFoundException {
        if(commands.size()>1){
            Pattern pattern=Pattern.compile("[0-9]*");
            Matcher matcher = pattern.matcher(commands.get(1));
            if(matcher.matches())
                version(currentDirectory,Integer.parseInt(commands.get(1)));
            else//incorrect format
                version(currentDirectory,-1);
        }
        else
            version(currentDirectory,-2); //version not specified
    }

    public static void init(String path) throws IOException {
        if(Files.exists(Paths.get(path + "/.gvt"))){
            System.out.println("Current directory is already initialized.");
            System.exit(10);
        }
        else{
            Files.createDirectory(Paths.get(path+"/.gvt"));
            createNewVersion(path+"/.gvt",0,0,"GVT initialized.");
            System.out.println("Current directory initialized successfully.");
        }
    }

    public static void add(String path, String filename, String message) throws IOException {
        doesFileExist(path);
        if(!Files.exists(Paths.get(path +"/"+ filename))){ //no such file
            System.out.println("File "+filename+" not found.");
            System.exit(21);
        }
        else{
            Scanner in=new Scanner(new File(path+"/.gvt/information.txt"));
            int actualVersion=in.nextInt();
            int recentVersion=in.nextInt();
            int nextActualVersion=recentVersion+1;
            int nextRecentVersion=recentVersion+1;
            createNewVersion(path+"/.gvt",nextActualVersion,nextRecentVersion,"Added file: "+filename+"\n"+message);
            copyFiles(path+"/.gvt/"+actualVersion,path+"/.gvt/"+nextActualVersion);
            Files.copy(Paths.get(path+"/"+filename),Paths.get(path+"/.gvt/"+nextActualVersion+"/"+filename), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File "+filename+" added successfully.");
        }
    }

    public static void detach(String path, String filename, String message) throws IOException {
        doesFileExist(path);

        Scanner in=new Scanner(new File(path+"/.gvt/information.txt"));
        int actualVersion=in.nextInt();
        int recentVersion=in.nextInt();

        if(!Files.exists(Paths.get(path +"/.gvt/"+actualVersion+"/"+filename))){ //no such file in our actual version
            System.out.println("File "+filename+" is not added to gvt.");
        }
        else{
            int nextActualVersion=recentVersion+1;
            int nextRecentVersion=recentVersion+1;
            createNewVersion(path+"/.gvt",nextActualVersion,nextRecentVersion,"Detached file: "+filename+"\n"+message);
            copyFiles(path+"/.gvt/"+actualVersion,path+"/.gvt/"+nextActualVersion);
            Files.deleteIfExists(Paths.get(path +"/.gvt/"+nextActualVersion+"/"+filename));
            System.out.println("File "+filename+" detached successfully.");
        }
    }

    public static void commit(String path, String filename, String message) throws IOException {
        doesFileExist(path);

        Scanner in=new Scanner(new File(path+"/.gvt/information.txt"));
        int actualVersion=in.nextInt();
        int recentVersion=in.nextInt();
        if(!Files.exists(Paths.get(path +"/"+ filename))){ //no such file
            System.out.println("File "+filename+" does not exist.");
            System.exit(51);
        }
        else if(!Files.exists(Paths.get(path +"/.gvt/"+actualVersion+"/"+filename))){ //no such file in our actual version
            System.out.println("File "+filename+" is not added to gvt.");
        }
        else{ //file exists
            int nextActualVersion=recentVersion+1;
            int nextRecentVersion=recentVersion+1;
            createNewVersion(path+"/.gvt",nextActualVersion,nextRecentVersion,"Committed file: "+filename+"\n"+message);
            copyFiles(path+"/.gvt/"+actualVersion,path+"/.gvt/"+nextActualVersion);
            Files.copy(Paths.get(path+"/"+filename),Paths.get(path+"/.gvt/"+nextActualVersion+"/"+filename),StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File "+filename+" committed successfully.");
        }
    }

    public static void checkout(String path, int version) throws IOException {
        doesFileExist(path);

        Scanner in=new Scanner(new File(path+"/.gvt/information.txt"));
        int actualVersion=in.nextInt();
        int recentVersion=in.nextInt();
        if(version>recentVersion){
            System.out.println("Invalid version number: "+version+".");
            System.exit(40);
        }
        else{
            actualVersion=version;
            copyFiles(path+"/.gvt/"+actualVersion,path);
            Files.deleteIfExists(Paths.get(path + "/.gvt/information.txt"));
            Files.createFile(Paths.get(path+"/.gvt/information.txt"));
            FileWriter writer = new FileWriter(path+"/.gvt/information.txt");
            writer.write(actualVersion +"\n");
            writer.write(recentVersion+"\n");
            writer.flush();
            writer.close();
            System.out.println("Version "+version+" checked out successfully.");
        }
    }

    public static void history(String path, int number) throws FileNotFoundException {
        doesFileExist(path);
        Scanner in=new Scanner(new File(path+"/.gvt/information.txt"));
        int recentVersion=in.nextInt();
        if((number == -1) || (number > recentVersion)){
            for (int i = 0; i <= recentVersion; i++) {
                Scanner in2 = new Scanner(new File(path + "/.gvt/" + i + "/message.txt"));
                System.out.println(i + ": " + in2.nextLine().trim());
            }
        }
        else{
            for (int i = recentVersion - number+1; i < recentVersion; i++) {
                Scanner in2 = new Scanner(new File(path + "/.gvt/" + i + "/message.txt"));
                System.out.println(i + ": " + in2.nextLine().trim());
            }
            Scanner in2 = new Scanner(new File(path + "/.gvt/" + recentVersion + "/message.txt"));
            System.out.print(recentVersion + ": " + in2.nextLine().trim());
        }
    }

    public static void version(String path, int number) throws FileNotFoundException {
        doesFileExist(path);

        Scanner in=new Scanner(new File(path+"/.gvt/information.txt"));
        int recentVersion=in.nextInt();

        if((number==-1) || (number > recentVersion)){
            System.out.println("Invalid version number: "+number+".");
            System.exit(60);
        }
        else if(number==-2){ //parameter not specified
            System.out.println("Version: "+ recentVersion);
            Scanner in2 = new Scanner(new File(path + "/.gvt/" + recentVersion + "/message.txt"));
            while(in2.hasNext()){
                String temp=in2.nextLine().trim();
                if(in2.hasNext())
                    System.out.println(temp);
                else
                    System.out.print(temp);
            }
        }
        else{ //correct parameter
            Scanner in3 = new Scanner(new File(path + "/.gvt/" + number + "/message.txt"));
            System.out.println("Version: "+ number);
            while(in3.hasNext()){
                String temp=in3.nextLine().trim();
                if(in3.hasNext())
                    System.out.println(temp);
                else
                    System.out.print(temp);
            }
        }
    }

    public static void createNewVersion(String where, int actualVersion, int recentVersion, String message) throws IOException {
        Files.deleteIfExists(Paths.get(where + "/information.txt"));
        Files.createFile(Paths.get(where+"/information.txt")); //actual and recent version stored here
        FileWriter writer = new FileWriter(where+"/information.txt");
        writer.write(actualVersion +"\n");
        writer.write(recentVersion +"\n");
        writer.flush();
        writer.close();
        Files.createDirectory(Paths.get(where + "/" + recentVersion)); //new directory for the newest version
        Files.createFile(Paths.get(where + "/" + recentVersion+"/message.txt"));
        FileWriter writer2 = new FileWriter(where + "/" + recentVersion+"/message.txt");
        writer2.write(message);
        writer2.flush();
        writer2.close();
    }

    public static void copyFiles(String from, String to) throws IOException {
        File file=new File(from);
        String[] filesNames=file.list();
        for(String name : filesNames) {
            if (!(new File(from + name).isDirectory()))
                if (!(name.equals("message.txt")))
                    Files.copy(Paths.get(from + "/" + name), Paths.get(to + "/" + name), StandardCopyOption.REPLACE_EXISTING);

        }
    }

    public static void doesFileExist(String path){
        if(!Files.exists(Paths.get(path + "/.gvt"))){
            System.out.println("Current directory is not initialized. Please use \"init\" command to initialize.");
            System.exit(-2);
        }
    }
}
