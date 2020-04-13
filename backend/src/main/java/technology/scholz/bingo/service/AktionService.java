package technology.scholz.bingo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technology.scholz.bingo.config.DefaultSpielConfigurations;
import technology.scholz.bingo.domain.Aktion;
import technology.scholz.bingo.domain.Spiel;
import technology.scholz.bingo.domain.SpielSettings;
import technology.scholz.bingo.domain.Spieler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AktionService {

    @Autowired
    private RandomService randomService;

    public Spiel addSpielEreignisse(Spiel spiel, List<Spieler> spielerMitNummer) {

        Optional<Aktion> maybeAktion = selectRandomAktionForSpiel(spiel);
        if (maybeAktion.isEmpty()) {
            return spiel;
        }

        getBingoAktionen(spiel, spielerMitNummer).forEach(spiel::addErfolgteAktion);
        spiel.addErfolgteAktion(maybeAktion.get());
        return spiel;
    }

    private List<Aktion> getBingoAktionen(Spiel spiel, List<Spieler> spielerMitNummer) {
        return spielerMitNummer.stream()
                .filter(spieler -> spieler.hasBingo(spiel.getGezogeneNummern()))
                .map(spieler -> new Aktion("BINGOOOOOOO!!!!!", spieler.getName() +" hat einen Bingo!"))
                .collect(Collectors.toList());

    }

    private Optional<Aktion> selectRandomAktionForSpiel(Spiel spiel) {

        List<Aktion> moeglicheAktionen = DefaultSpielConfigurations.moeglicheAktionen();
        SpielSettings settings = spiel.getSettings();

        log.info("Calculation of Random-Aktion with the Seeds moeglicheAktionen.size()='{}', Wahrscheinlichkeitsfaktor='{}'", moeglicheAktionen.size(),settings.getWahrscheinlichkeitAktionInProzent());

        boolean performAktion = randomService.getRandomBoolean(settings.getWahrscheinlichkeitAktionInProzent());

        if (!performAktion || moeglicheAktionen.isEmpty()) {
            log.info("Do not add a  Random-Aktion to Spiel " +spiel.getId());
            return Optional.empty();
        }

        Aktion randomAktion = randomService.getRandomListItem(moeglicheAktionen);
        log.info("Adding Random-Aktion '{}' to Spiel '{}'", randomAktion.getUeberschrift(), spiel.getId());
        return Optional.of(randomAktion);

    }

}
