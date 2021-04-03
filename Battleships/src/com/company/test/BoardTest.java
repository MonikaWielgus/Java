package com.company.test;

import com.company.main.Board;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void checkIfInTheBoardWhereBeyond() {
        Assert.assertEquals(Board.checkIfInTheBoard(10,10),false);
    }

    @Test
    void checkIfInTheBoardWhereNotBeyond() {
        Assert.assertEquals(Board.checkIfInTheBoard(0,0),true);
    }

    @Test
    void checkIfInTheBoardWithMinusValues() {
        Assert.assertEquals(Board.checkIfInTheBoard(-1,0),false);
    }

    @Test
    void getPlaceOne() {
        Assert.assertEquals(Board.getPlace(0,0),"A1");
    }

    @Test
    void getPlaceTwo() {
        Assert.assertEquals(Board.getPlace(3,2),"D3");
    }

    @Test
    void getColumnOne() {
        Assert.assertEquals(Board.getColumn("A1"),0);
    }

    @Test
    void getColumnTwo() {
        Assert.assertEquals(Board.getColumn("D3"),3);
    }

    @Test
    void getRowOne() {
        Assert.assertEquals((Board.getRow("A1")),0);
    }

    @Test
    void getRowTwo() {
        Assert.assertEquals((Board.getRow("D3")),2);
    }
}