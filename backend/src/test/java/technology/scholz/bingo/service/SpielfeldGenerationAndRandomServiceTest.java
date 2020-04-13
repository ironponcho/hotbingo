package technology.scholz.bingo.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import technology.scholz.bingo.domain.SpielSettings;
import technology.scholz.bingo.domain.Spieler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpielfeldGenerationAndRandomServiceTest {

    private SpielfeldGenerationService spielfeldGenerationService;

    @BeforeEach
    void setUp() {
        spielfeldGenerationService = new SpielfeldGenerationService(new RandomService());
    }

    @Test
    void ensureThatTwoDimensionalSpielfeldWorks() {

        final int testDurchlaeufe = 5;

        final int maxNumber = 75;
        final int minNumber = 1;
        final int spielfeldDimensions = 2;
        final int spielfeldElementsPerDimension = 5;

        SpielSettings twoDimensionalSpielfeldSettings = SpielSettings.builder()
                .maxNumber(maxNumber)
                .minNumber(minNumber)
                .spielfeldDimensions(spielfeldDimensions)
                .spielfeldElementsPerDimension(spielfeldElementsPerDimension)
                .build();

        for (int i = 0; i < testDurchlaeufe; i++) {
            performGeneration(twoDimensionalSpielfeldSettings);
        }
    }

    @SneakyThrows
    void performGeneration(SpielSettings spielSettings) {


        List<List<Integer>> rows = spielfeldGenerationService.generateSpielfeld(spielSettings);

        Spieler valdi = new Spieler("valdi", rows);

        assertEquals(spielSettings.getSpielfeldElementsPerDimension(), rows.size());

        for (List<Integer> row : rows) {
            assertEquals(spielSettings.getSpielfeldElementsPerDimension(), row.size());

            for (Integer valueInColumn : row) {
                assertTrue(valueInColumn >= spielSettings.getMinNummer());
                assertTrue(valueInColumn <= spielSettings.getMaxNummer());
            }
        }

        valdi.validate(spielSettings);

    }

}