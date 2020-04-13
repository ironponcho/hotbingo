package technology.scholz.bingo.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RandomServiceTest {

    private final RandomService randomService = new RandomService();

    @Test
    void thatRandomBooleanWorks(){

        for(int i = 0; i < 25; i++){
            assertFalse(randomService.getRandomBoolean(0));
        }

        for(int i = 0; i < 25; i++){
            assertTrue(randomService.getRandomBoolean(100));
        }

    }

    @Test
    void exampleForFlakyTestsHahahaha(){
        // IF THIS TEST DOES FAILS AND U DONT EXPECT THIS JUST PERFORM IT AGAIN HAHAHAH

        for(int q = 0; q <100; q++){
            int distribution = 0;
            for(int i = 0; i < 100; i++){
                if(randomService.getRandomBoolean(50)) distribution ++;
            }
            assertTrue(distribution>15 && distribution < 85);
        }
    }

}