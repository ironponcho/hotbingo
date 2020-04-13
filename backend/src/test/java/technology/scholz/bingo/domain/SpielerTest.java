package technology.scholz.bingo.domain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
class SpielerTest {

    List<Integer> spalte1 = List.of( 1, 2,  3,  4,  5);
    List<Integer> spalte2 = List.of( 7, 8,  9,  10, 11);
    List<Integer> spalte3 = List.of(12, 13, 14, 15, 16);
    List<Integer> spalte4 = List.of(17, 18, 19, 20, 21);
    List<Integer> spalte5 = List.of(22, 23, 24, 25, 26);
    private List<List<Integer>> spielfeld = List.of(spalte1, spalte2, spalte3, spalte4, spalte5);

    @Test
    void testThatFullRowIsBingo() {

        List<Integer> gezogeneNummern = List.of(22,23,24,25,26);
        Spieler innocentLai = new Spieler("Innocent Lai", spielfeld);
        boolean bingo = innocentLai.hasBingo(gezogeneNummern);
        assertTrue(bingo);

    }

    @Test
    void testThatColumnIsBingo() {

        List<Integer> gezogeneNummern = List.of(5,11,16,21,26);
        Spieler innocentLai = new Spieler("Innocent Lai", spielfeld);
        assertTrue(innocentLai.hasBingo(gezogeneNummern));

    }

    @Test
    void testThatDiagonalAllignmentLeftBottom2RightTopIsBingo() {

        List<Integer> gezogeneNummern = List.of(22,18,14,10,5);
        Spieler innocentLai = new Spieler("Innocent Lai", spielfeld);
        assertTrue(innocentLai.hasBingo(gezogeneNummern));

    }

    @Test
    void testThatDiagonalAllignmentRightBottom2LeftTopIsBingo() {

        List<Integer> gezogeneNummern = List.of(26,20,14,8,1);
        Spieler innocentLai = new Spieler("Innocent Lai", spielfeld);
        assertTrue(innocentLai.hasBingo(gezogeneNummern));

    }

    @Test
    void testThatNoWinningConditionIsNotBingo() {

        List<Integer> gezogeneNummern = List.of(5,69,1,28,12);
        Spieler innocentLai = new Spieler("Innocent Lai", spielfeld);
        assertFalse(innocentLai.hasBingo(gezogeneNummern));

    }

    @Test
    void testThatBiggerSpielfeldWorksAswell() {

        List<Integer> spalte1 = List.of( 1, 2,  3,  4,  5, 59);
        List<Integer> spalte2 = List.of( 7, 8,  9,  10, 11, 60);
        List<Integer> spalte3 = List.of(12, 13, 14, 15, 16, 61);
        List<Integer> spalte4 = List.of(17, 18, 19, 20, 21, 62);
        List<Integer> spalte5 = List.of(22, 23, 24, 25, 26, 63);
        List<Integer> spalte6 = List.of(51, 52, 53, 54, 55, 64);

        // Spielfeld.SPALTEN_ZEILEN_ANZAHL = 6;
        List<Integer> gezogeneNummern = List.of(1,8,14,20,26,64);
        Spieler innocentLai = new Spieler("Innocent Lai", List.of(spalte1, spalte2, spalte3, spalte4, spalte5, spalte6));
        assertTrue(innocentLai.hasBingo(gezogeneNummern));

    }
}