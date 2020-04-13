package technology.scholz.bingo.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RandomService {

    private final Random r = new Random();

    public int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return r.nextInt((max - min) + 1) + min;
    }

    public boolean getRandomBoolean(int wahrscheinlichkeit) {

        if (wahrscheinlichkeit<0 || wahrscheinlichkeit > 100) {
            throw new IllegalArgumentException("possibility must be between 0 and 100.");
        }

        return getRandomNumberInRange(0, 100) < wahrscheinlichkeit;
    }

    public <T> T getRandomListItem(List<T> list) {
        return list.get(r.nextInt(list.size()));
    }
}
