package com.company.main;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


class CalculatorTest {

    @Test
    void calculateStartDateAfterEndDate() {
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 11:00 Europe/Warsaw","2019-01-02 00:00 Europe/London",new BigDecimal(41));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("0")));
    }

    @Test
    void calculateStartDateAfterEndDateWithTimeDifference() {
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 11:00 Europe/Warsaw","2019-01-02 09:00 Europe/London",new BigDecimal(41));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("0")));
    }

    @Test
    void calculateTheSameStartAndEndDate(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 11:00 Europe/Warsaw","2019-01-02 11:00 Europe/Warsaw",new BigDecimal(41));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("0")));
    }

    @Test
    void calculateTheSameStartAndEndDateWithTimeDifference(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 11:00 Europe/Warsaw","2019-01-02 10:00 Europe/London",new BigDecimal(41));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("0")));
    }

    @Test
    void calculateLessThan8Hours(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 13:00 Europe/Warsaw","2019-01-02 15:00 Europe/Berlin",new BigDecimal(49));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("16.33")));
    }

    @Test
    void calculateLessThan8HoursWithTimeDifference(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 00:00 Europe/Warsaw","2019-01-02 00:00 Europe/London",new BigDecimal(33));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("11")));
    }

    @Test
    void calculate8Hours(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 12:00 Europe/Warsaw","2019-01-02 20:00 Europe/Warsaw",new BigDecimal(33));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("11")));
    }
    @Test
    void calculate8HoursWithTimeDifference(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 12:00 Europe/Warsaw","2019-01-02 19:00 Europe/London",new BigDecimal(33));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("11")));
    }

    @Test
    void calculate8To12Hours(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 12:00 Europe/Warsaw","2019-01-02 20:01 Europe/Warsaw",new BigDecimal(30));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("15")));
    }

    @Test
    void calculate8To12HoursWithTimeDifference(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 12:00 Europe/Warsaw","2019-01-02 21:00 Europe/London",new BigDecimal(30));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("15")));
    }

    @Test
    void calculateMoreThan12Hours(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 10:00 Europe/Warsaw","2019-01-02 22:01 Europe/Warsaw",new BigDecimal(30));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("30")));
    }

    @Test
    void calculateMoreThan12HoursWithTimeDifference(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 10:00 Europe/Warsaw","2019-01-02 21:01 Europe/London",new BigDecimal(30));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("30")));
    }

    @Test
    void calculateTwoDaysAndLessThan8Hours(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 10:00 Europe/Warsaw","2019-01-04 10:01 Europe/Warsaw",new BigDecimal(49));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("114.33")));
    }

    @Test
    void calculateTwoDaysAndLessThan8HoursWithTimeDifference(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 10:00 Europe/Warsaw","2019-01-04 09:01 Europe/London",new BigDecimal(49));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("114.33")));
    }

    @Test
    void calculateTwoDaysAnd8To12Hours(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 10:00 Europe/Warsaw","2019-01-04 18:01 Europe/Warsaw",new BigDecimal(50));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("125")));
    }

    @Test
    void calculateTwoDaysAnd8To12HoursWithTimeDifference(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 10:00 Europe/Warsaw","2019-01-04 17:01 Europe/London",new BigDecimal(50));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("125")));
    }

    @Test
    void calculateTwoDaysAndMoreThan12Hours(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 10:00 Europe/Warsaw","2019-01-04 22:17 Europe/Warsaw",new BigDecimal(50));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("150")));
    }

    @Test
    void calculateTwoDaysAndMoreThan12HoursWithTimeDifference(){
        Calculator calc=new Calculator();
        BigDecimal result=calc.calculate("2019-01-02 10:00 Europe/Warsaw","2019-01-04 21:17 Europe/London",new BigDecimal(50));
        Assert.assertEquals(0, result.compareTo(new BigDecimal("150")));
    }

}