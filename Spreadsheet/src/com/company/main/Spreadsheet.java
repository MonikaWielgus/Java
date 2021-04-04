package com.company.main;

public class Spreadsheet {
    public String[][] calculate(String[][] input) { //columns,rows
        String [][]result=new String[input.length][input[0].length];
        for(int i=0;i<input[0].length;i++)
            for(int j=0;j<input.length;j++)
                result[j][i]=Integer.toString(read(input[j][i],input));
        return result;
    }
    private int read(String string,String[][] input){
        if(string.charAt(0)=='$')
            return getNumberFromCell(string.substring(1),input);
        else if(string.charAt(0)=='=')
            return getNumberFromExpression(string.substring(1),input);
        else
            return Integer.parseInt(string);
    }
    private int getNumberFromCell(String string,String[][] input){
        int row=string.charAt(0)-'A';
        int column=Integer.parseInt(string.substring(1))-1;
        return read(input[column][row],input);
    }
    private int getNumberFromExpression(String string,String[][] input){
        String task=string.substring(0,3);
        String firstExpression=string.substring(string.indexOf("(")+1,string.indexOf(","));
        String secondExpression=string.substring(string.indexOf(",")+1,string.indexOf(")"));
        switch(task){
            case "ADD":
                return read(firstExpression,input)+read(secondExpression,input);
            case "SUB":
                return read(firstExpression,input)-read(secondExpression,input);
            case "MUL":
                return read(firstExpression,input)*read(secondExpression,input);
            case "DIV":
                return read(firstExpression,input)/read(secondExpression,input);
            case "MOD":
                return read(firstExpression,input)%read(secondExpression,input);
        }
        return 0;
    }
}
