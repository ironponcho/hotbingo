package technology.scholz.bingo.config;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import technology.scholz.bingo.domain.Aktion;
import technology.scholz.bingo.domain.SpielSettings;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class DefaultSpielConfigurations {

    public static final int DEFAULT_MIN_NUMBER = 1;
    public static final int DEFAULT_MAX_NUMBER = 75;
    public static final int DEFAULT_SPIELFELD_CELLS_PER_DIMENSION = 5;
    public static final int DEFAULT_SPIELFELD_DIMENSIONS = 2;

    @Bean
    public SpielSettings defaultTwoDimensionalGame() {
        return SpielSettings.builder()
                .maxNumber(DEFAULT_MAX_NUMBER)
                .minNumber(DEFAULT_MIN_NUMBER)
                .spielfeldElementsPerDimension(DEFAULT_SPIELFELD_CELLS_PER_DIMENSION)
                .spielfeldDimensions(DEFAULT_SPIELFELD_DIMENSIONS)
                .wahrscheinlichkeitAktionInProzent(15)
                .moeglicheAktionen(moeglicheAktionen())
                .build();
    }

    @Bean
    public List<String> randomPhysicist() {
        return List.of(
                "Ernest Rutherford",
                "Albert Einstein",
                "Emmy Noether",
                "Max Born",
                "James Franck",
                "Otto Stern",
                "Peter Debye",
                "Niels Bohr",
                "Erwin Schrödinger",
                "Isidor Isaac Rabi",
                "Wolfgang Pauli",
                "Werner Heisenberg",
                "Enrico Fermi",
                "Paul Dirac",
                "Pascual Jordan",
                "Eugene Paul Wigner",
                "Hans Bethe",
                "Lew Landau",
                "John Bardeen",
                "John Archibald Wheeler",
                "Charles Hard Townes",
                "Richard Feynman",
                "Julian Schwinger",
                "Chen Ning Yang",
                "Murray Gell-Mann",
                "Steven Weinberg",
                "David Gross",
                "Stephen Hawking",
                "Gerardus ’t Hooft"
        );
    }
    
    public static List<Aktion> moeglicheAktionen() {

        return IntStream.range(0, 100).boxed()
                .map(i -> new Aktion(RandomStringUtils.random(10), RandomStringUtils.random(20)))
                .collect(Collectors.toList());

    }

}
