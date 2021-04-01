package com.company.main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Calculator {

    BigDecimal calculate(String start, String end, BigDecimal dailyRate) {
        long timeInMinutes=getTimeInMinutes(start,end);
        return getRateHelper(timeInMinutes,dailyRate);
    }
    private long getTimeInMinutes(String start, String end){
        ZonedDateTime startTimeZoned=timeFromStringToZonedDateTime(start);
        ZonedDateTime endTimeZoned=timeFromStringToZonedDateTime(end);
        return getTimeDifference(startTimeZoned,endTimeZoned);
    }
    private static ZonedDateTime timeFromStringToZonedDateTime(String time){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm z");
        return ZonedDateTime.parse(time,formatter);
    }

    private long getTimeDifference(ZonedDateTime start, ZonedDateTime end){
        ChronoUnit unit= ChronoUnit.MINUTES;
        return unit.between(start,end);
    }
    private BigDecimal getRateHelper(long timeInMinutes, BigDecimal dailyRate){
        if(timeInMinutes<=0){
            return (BigDecimal.ZERO).setScale(2, RoundingMode.HALF_EVEN);
        }
        long hours = minutesToFullHours(timeInMinutes);
        long minutes = minutesWithoutFullHours(timeInMinutes);

        return getTotalRate(hours,minutes,dailyRate).setScale(2,RoundingMode.HALF_EVEN);
    }
    private long minutesToFullHours(long minutes){
        return Duration.ofMinutes(minutes).toHours();
    }
    private long minutesWithoutFullHours(long minutes){
        return minutes-hoursToMinutes(minutesToFullHours(minutes));
    }
    private long hoursToMinutes(long hours){
        return Duration.ofHours(hours).toMinutes();
    }
    private long hoursToDays(long hours){
        return Duration.ofHours(hours).toDays();
    }
    private long daysToHours(long days){
        return Duration.ofDays(days).toHours();
    }
    private BigDecimal rateForFullDays(long days, BigDecimal rate){
        return rate.multiply(new BigDecimal(String.valueOf(days)));
    }
    private BigDecimal rateFor8To12Hours(long hours,long minutes, BigDecimal rate){
        if((hours==8&&minutes>0)||hours>8)
            return rate.divide(new BigDecimal(2),2,RoundingMode.HALF_EVEN);
        else
            return new BigDecimal(0);
    }
    private BigDecimal rateForTo8Hours(long hours, long minutes, BigDecimal rate){
        if((hours<8&&hours>0)||(hours==0&&minutes>0)||(hours==8&&minutes==0))
            return rate.divide(new BigDecimal(3),2,RoundingMode.HALF_UP);
        else
            return new BigDecimal(0);
    }

    private BigDecimal getTotalRate(long hours, long minutes, BigDecimal rate){
        long numberOfDays=hoursToDays(hours);

        hours-=daysToHours(numberOfDays);
        if(hours >= 12){
            numberOfDays+=1;
            hours=0;
            minutes=0;
        }
        BigDecimal forFullDays=rateForFullDays(numberOfDays,rate);
        BigDecimal for8To12Hours=rateFor8To12Hours(hours,minutes,rate);
        BigDecimal forTo8Hours=rateForTo8Hours(hours,minutes,rate);

        return forFullDays.add(for8To12Hours).add(forTo8Hours);
    }
}
