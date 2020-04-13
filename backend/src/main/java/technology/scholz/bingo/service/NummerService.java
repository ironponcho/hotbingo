package technology.scholz.bingo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technology.scholz.bingo.domain.HostInput;
import technology.scholz.bingo.domain.Spiel;
import technology.scholz.bingo.domain.Spieler;
import technology.scholz.bingo.exception.BingoException;
import technology.scholz.bingo.exception.NotAuthorizedException;
import technology.scholz.bingo.exception.SpielNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NummerService {

    @Autowired
    private SpielService spielService;

    @Autowired
    private AktionService aktionService;

    @Autowired
    private RandomService randomService;

    @Autowired
    private SpielerService spielerService;

    public List<Spieler> addGezogeneNummer(UUID spielId, HostInput hostInput) throws BingoException {

        Optional<Spiel> maybeSpiel = spielService.findById(spielId);
        if (maybeSpiel.isEmpty()) {
            throw new SpielNotFoundException(spielId);
        }
        Spiel spiel = maybeSpiel.get();

        if ( !hostInput.getHostSecret().equals(spiel.getHostSecret())) {
            log.error("Input '{}' and SpielSecret '{}' do not match", hostInput.getHostSecret(), spiel.getHostSecret());
            throw new NotAuthorizedException(spielId, hostInput.getHostSecret());
        }

        if (spiel.getGezogeneNummern().contains(hostInput.getGezogeneNummer())) {
            throw new BingoException("Die Nummer " + hostInput + " wurde bereits einmal gezogen");
        }
        spiel.addGezogeneNummer(hostInput.getGezogeneNummer());
        List<Spieler> spielerMitNummer = spielerService.findSpielerByNummerInSpielfeld(spiel, hostInput.getGezogeneNummer());
        spiel = aktionService.addSpielEreignisse(spiel,spielerMitNummer);
        spielService.save(spiel);
        log.info("Attached Number '{}' to game with spielId '{}'", hostInput, spielId);

        return spielerMitNummer;
    }

    public void removeGezogeneNummer(UUID spielId, Integer gezogeneNummer) {

        spielService.findById(spielId).ifPresentOrElse(spiel -> {

            if (!spiel.getGezogeneNummern().contains(gezogeneNummer)) {
                throw new IllegalArgumentException("Die Nummer " + gezogeneNummer + " wurde noch nicht gezogen");
            }

            spiel.removeGezogeneNummer(gezogeneNummer);
            spielService.save(spiel);
            log.info("Removed Number '{}' from game with spielId '{}'", gezogeneNummer, spielId);
        }, () -> {
            throw new IllegalArgumentException("Spiel mit Id " + spielId + " wurde nicht gefunden.");
        });

    }

    public Optional<Integer> getRandomGezogeneNummer(UUID spielId) throws BingoException {

        Optional<Spiel> maybeSpiel = spielService.findById(spielId);
        if( maybeSpiel.isEmpty() ){
            throw new SpielNotFoundException(spielId);
        }
        List<Integer> verfuegbareNummern = maybeSpiel.get().assembleVerfuegbareNummern();
        if( verfuegbareNummern.isEmpty() ) {
            return Optional.empty();
        }
        return Optional.of(randomService.getRandomListItem(verfuegbareNummern));
    }
}
