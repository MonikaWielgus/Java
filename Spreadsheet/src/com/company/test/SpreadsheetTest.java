package com.company.test;

import com.company.main.Spreadsheet;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


class SpreadsheetTest {

    @Test
    void calculateOne() {
        Spreadsheet sheet=new Spreadsheet();
        String[][] input={{"1","2","3"},
                {"4","5","6"},
                {"$A1","$C1","$B3"},
                {"=ADD(10,$A1)","=SUB($C3,$A1)","0"}};
        String[][] expectedResult={{"1","2","3"},
                                {"4","5","6"},
                                {"1","3","3"},
                                {"11","2","0"}};
        String[][]result=sheet.calculate(input);
        Assert.assertTrue(Arrays.deepEquals(expectedResult,result));
    }

    @Test
    void calculateTwo() {
        Spreadsheet sheet=new Spreadsheet();
        String[][] input={{"1","2","3"},
                {"4","5","6"}};
        String[][] expectedResult={{"1","2","3"},
                {"4","5","6"}};
        String[][]result=sheet.calculate(input);
        Assert.assertTrue(Arrays.deepEquals(expectedResult,result));
    }

    @Test
    void calculateThree() {
        Spreadsheet sheet=new Spreadsheet();
        String[][] input={{"5","3","=ADD($A1,$B1)"},
                {"5","3","=SUB($A2,$B2)"},
                {"5","3","=MUL($A3,$B3)"},
                {"5","3","=DIV($A4,$B4)"},
                {"5","3","=MOD($A5,$B5)"},
                {"10","2","=MOD($A6,$B6)"}};
        String[][] expectedResult={{"5","3","8"},
                {"5","3","2"},
                {"5","3","15"},
                {"5","3","1"},
                {"5","3","2"},
                {"10","2","0"}};
        String[][]result=sheet.calculate(input);
        Assert.assertTrue(Arrays.deepEquals(expectedResult,result));
    }

    @Test
    void calculateFour() {
        Spreadsheet sheet=new Spreadsheet();
        String[][] input={{"1","2","3"},
                {"4","5","6"},
                {"$C3","$C1","$A1"},
                {"=ADD(10,$A1)","=SUB($C3,$A1)","0"}};
        String[][] expectedResult={{"1","2","3"},
                {"4","5","6"},
                {"1","3","1"},
                {"11","0","0"}};
        String[][]result=sheet.calculate(input);
        Assert.assertTrue(Arrays.deepEquals(expectedResult,result));
    }
}