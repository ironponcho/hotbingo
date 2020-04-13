package technology.scholz.bingo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technology.scholz.bingo.config.DefaultSpielConfigurations;
import technology.scholz.bingo.domain.Spiel;
import technology.scholz.bingo.domain.Spieler;
import technology.scholz.bingo.domain.SpielerInbound;
import technology.scholz.bingo.exception.BingoException;
import technology.scholz.bingo.exception.NotAuthorizedException;
import technology.scholz.bingo.exception.SpielNotFoundException;
import technology.scholz.bingo.exception.SpielerValidationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SpielerService {

    @Autowired
    private SpielService spielService;

    @Autowired
    private DefaultSpielConfigurations defaultSpielConfigurations;

    @Autowired
    private RandomService randomService;

    public Spieler addRandomSpieler(UUID spielId, UUID hostSecret) throws BingoException {
        log.info("Trying to add random Spieler for Spiel '{}' with hostSecret '{}'" ,spielId, hostSecret);
        String spielerName = randomService.getRandomListItem(defaultSpielConfigurations.randomPhysicist());
        return addSpieler(spielId, new Spieler(spielerName, spielService.generateSpielfeld(spielId)), hostSecret);
    }

    public Spieler addSpieler(UUID spielId, Spieler newSpieler, UUID hostSecret) throws BingoException {

        Optional<Spiel> maybeSpiel = spielService.findById(spielId);
        Spiel spiel = maybeSpiel.orElseThrow(() -> new SpielNotFoundException(spielId));
        if( !spiel.getHostSecret().equals(hostSecret) ) {
            throw new NotAuthorizedException(spielId, hostSecret);
        }

        return addSpieler(spiel, newSpieler);
    }

    public Spieler addSpieler(UUID spielId, SpielerInbound spielerInbound) throws BingoException {

        Optional<Spiel> maybeSpiel = spielService.findById(spielId);
        Spiel spiel = maybeSpiel.orElseThrow(() -> new SpielNotFoundException(spielId));
        Spieler spieler = spielerInbound.toSpieler(spiel.getSettings());
        return addSpieler(spiel, spieler);
    }

    public Spieler addSpieler(Spiel spiel, Spieler newSpieler) throws SpielerValidationException {
        if (spiel.getSpieler().stream().anyMatch(spieler -> spieler.getName().equals(newSpieler.getName()))) {
            throw new SpielerValidationException("Es existiert bereits ein Spieler mit dem Namen " + newSpieler.getName());
        }

        newSpieler.validate(spiel.getSettings());
        spiel.addSpieler(newSpieler);
        spielService.save(spiel);

        log.info("Adding spieler with id '{}' to spiel '{}'", newSpieler.getId(), newSpieler.getId());
        return newSpieler;
    }

    public Optional<Spieler> findSpielerBySpielIdAndSpielerId(UUID spielId, UUID spielerId) {
        Optional<Spiel> maybeSpiel = spielService.findById(spielId);
        if(maybeSpiel.isEmpty()) return Optional.empty();
        return maybeSpiel.get().getSpieler(spielerId);
    }

    public List<Spieler> findSpielerBySpielId(UUID spielId) {

        Optional<Spiel> maybeSpiel = spielService.findById(spielId);
        if (maybeSpiel.isEmpty()) {
            return Collections.emptyList();
        } else {
            return maybeSpiel.get().getSpieler();
        }
    }

    public List<Spieler> findSpielerByNummerInSpielfeld(Spiel spiel, Integer nummer) {
        return spiel.getSpieler().stream()
                .filter(spieler -> spieler.hasNumber(nummer))
                .collect(Collectors.toList());
    }

    public boolean spielerHasBingo(UUID spielId, UUID spielerId) throws BingoException {

        Optional<Spiel> maybeSpiel = spielService.findById(spielId);
        Spiel spiel = maybeSpiel.orElseThrow(() -> new SpielNotFoundException(spielId));

        Optional<Spieler> maybeSpieler = spiel.getSpieler(spielerId);
        Spieler spieler = maybeSpieler.orElseThrow(() -> new SpielNotFoundException(spielId));

        return spieler.hasBingo(spiel.getGezogeneNummern());

    }

}
