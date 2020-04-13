package technology.scholz.bingo.domain;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import technology.scholz.bingo.config.DefaultSpielConfigurations;
import technology.scholz.bingo.exception.SpielerValidationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpielerValidationTest {

    private SpielSettings twoDimensionalGame = new DefaultSpielConfigurations().defaultTwoDimensionalGame();

    @Test
    public void testThatNumberOneIsDuplicate() {
        List<Integer> spalte1 = List.of(1, 2, 3, 4, 5);
        List<Integer> spalte2 = List.of(6, 7, 8, 9, 10);
        List<Integer> spalte3 = List.of(11, 12, 13, 14, 15);
        List<Integer> spalte4 = List.of(16, 7, 9, 15, 16);
        List<Integer> spalte5 = List.of(1, 7, 9, 15, 16);

        Assertions.assertThrows(SpielerValidationException.class, () -> {
            new Spieler("valdi", List.of(spalte1, spalte2, spalte3, spalte4, spalte5)).validate(twoDimensionalGame);
        });
    }

    @Test
    public void testThatNumber76IsTooBig() {
        List<Integer> spalte1 = List.of(76, 2, 3, 4, 5);
        List<Integer> spalte2 = List.of(6, 7, 8, 9, 10);
        List<Integer> spalte3 = List.of(11, 12, 13, 14, 15);
        List<Integer> spalte4 = List.of(16, 7, 9, 15, 16);
        List<Integer> spalte5 = List.of(1, 7, 9, 15, 16);

        Assertions.assertThrows(SpielerValidationException.class, () -> {
            new Spieler("valdi", List.of(spalte1, spalte2, spalte3, spalte4, spalte5)).validate(twoDimensionalGame);
        });
    }

    @Test
    public void testThatTooMuchRowsArePresent() {
        List<Integer> spalte1 = List.of(76, 2, 3, 4, 5);
        List<Integer> spalte2 = List.of(6, 7, 8, 9, 10);
        List<Integer> spalte3 = List.of(11, 12, 13, 14, 15);
        List<Integer> spalte4 = List.of(16, 7, 9, 15, 16);
        List<Integer> spalte5 = List.of(1, 7, 9, 15, 16);
        List<Integer> spalte6 = List.of(1, 7, 9, 15, 16);

        Assertions.assertThrows(SpielerValidationException.class, () -> {
            new Spieler("valdi", List.of(spalte1, spalte2, spalte3, spalte4, spalte5)).validate(twoDimensionalGame);
        });
    }

    @Test
    public void testThatTooMuchColumsArePresent() {
        List<Integer> spalte1 = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> spalte2 = List.of(6, 7, 8, 9, 10);
        List<Integer> spalte3 = List.of(11, 12, 13, 14, 15);
        List<Integer> spalte4 = List.of(16, 7, 9, 15, 16);
        List<Integer> spalte5 = List.of(1, 7, 9, 15, 16);

        Assertions.assertThrows(SpielerValidationException.class, () -> {
            new Spieler("valdi", List.of(spalte1, spalte2, spalte3, spalte4, spalte5)).validate(twoDimensionalGame);
        });
    }

    @Test
    public void testThatNegativeOrZeroIsForbidden() {
        List<Integer> spalte1 = List.of(-1, 2, 3, 4, 5, 6);
        List<Integer> spalte2 = List.of(6, 7, 8, 9, 10);
        List<Integer> spalte3 = List.of(11, 12, 13, 14, 15);
        List<Integer> spalte4 = List.of(16, 7, 9, 15, 16);
        List<Integer> spalte5 = List.of(1, 7, 9, 15, 16);

        Assertions.assertThrows(SpielerValidationException.class, () -> {
            new Spieler("valdi", List.of(spalte1, spalte2, spalte3, spalte4, spalte5)).validate(twoDimensionalGame);
        });

    }

    @Test
    public void testThatEmptyNameCausesSpielerValidationException() {
        List<Integer> spalte1 = List.of(1, 2, 3, 4, 5);
        List<Integer> spalte2 = List.of(7, 8, 9, 10, 11);
        List<Integer> spalte3 = List.of(12, 13, 14, 15, 16);
        List<Integer> spalte4 = List.of(17, 18, 19, 20, 21);
        List<Integer> spalte5 = List.of(22, 23, 24, 25, 26);

        Assertions.assertThrows(SpielerValidationException.class, () -> {
            new Spieler("", List.of(spalte1, spalte2, spalte3, spalte4, spalte5)).validate(twoDimensionalGame);
        });

    }

    @SneakyThrows
    @Test
    public void testThatDefaultSpielfeldGenerationWorks() {
        List<Integer> spalte1 = List.of(1, 2, 3, 4, 5);
        List<Integer> spalte2 = List.of(7, 8, 9, 10, 11);
        List<Integer> spalte3 = List.of(12, 13, 14, 15, 16);
        List<Integer> spalte4 = List.of(17, 18, 19, 20, 21);
        List<Integer> spalte5 = List.of(22, 23, 24, 25, 26);
        Spieler valdi = new Spieler("valdi", List.of(spalte1, spalte2, spalte3, spalte4, spalte5));
        valdi.validate(twoDimensionalGame);
        assertEquals(twoDimensionalGame.getSpielfeldElementsPerDimension(), valdi.getSpielfeld().size());
    }
}