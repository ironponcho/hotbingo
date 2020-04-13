package technology.scholz.bingo.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import technology.scholz.bingo.domain.Aktion;
import technology.scholz.bingo.domain.Spiel;
import technology.scholz.bingo.domain.SpielSettings;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class AktionServiceTest {

    @Mock
    private SpielService spielService;

    @Mock
    private RandomService randomService;

    @InjectMocks
    private AktionService aktionService;

    private Spiel spiel;
    private int wahrscheinlichkeit = 15;
    private List<Aktion> moeglicheAktionen;

    @BeforeAll
    void setUp(){

        moeglicheAktionen = List.of(
                new Aktion("Test1","Test1"),
                new Aktion("Test2","Test2"),
                new Aktion("Test3","Test3"),
                new Aktion("Test4","Test4")
        );

        SpielSettings spielSettings = SpielSettings.builder()
                .maxNumber(75)
                .minNumber(0)
                .spielfeldElementsPerDimension(5)
                .spielfeldDimensions(2)
                .wahrscheinlichkeitAktionInProzent(wahrscheinlichkeit)
                .moeglicheAktionen(moeglicheAktionen)
                .build();

        spiel = new Spiel(spielSettings);
    }

    @Test
    void selectRandomAktionForSpiel() {
        /*

        aktionService.addSpielEreignisse(spiel, spielerMitNummer);

        when(randomService.getRandomBoolean(wahrscheinlichkeit)).thenReturn(false);
        Spiel s1 = aktionService.addSpielEreignisse(spiel, spielerMitNummer);
        assertEquals(0, s1.getAktionen().size());

        when(randomService.getRandomBoolean(wahrscheinlichkeit)).thenReturn(true);
        Spiel s2 = aktionService.addSpielEreignisse(spiel, spielerMitNummer);
        assertEquals(1, s2.getAktionen().size());

        verify(spielService).save(s2);

         */

    }


}