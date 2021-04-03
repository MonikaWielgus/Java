package com.company.test;
import com.company.main.MapGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class MapGeneratorTest {

    @Test
    void generateMapNumberOfMasts() {
        String map= MapGenerator.generateMap();
        int numberOfMasts=0;
        for(int i=0; i<100; i++){
            if(map.charAt(i)=='#')
                numberOfMasts++;
        }
        Assert.assertEquals(20,numberOfMasts);
    }

    @Test
    void generateMapNumberOfEmptyPlaces() {
        String map=MapGenerator.generateMap();
        int numberOfEmptyPlaces=0;
        for(int i=0; i<100; i++){
            if(map.charAt(i)=='.')
                numberOfEmptyPlaces++;
        }
        Assert.assertEquals(80,numberOfEmptyPlaces);
    }

    @Test
    void generateMapNotEqualBoards(){
        String map1=MapGenerator.generateMap();
        String map2=MapGenerator.generateMap();
        Assert.assertNotEquals(map1,map2);
    }
}