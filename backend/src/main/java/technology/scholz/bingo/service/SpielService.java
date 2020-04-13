package technology.scholz.bingo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technology.scholz.bingo.config.DefaultSpielConfigurations;
import technology.scholz.bingo.domain.Spiel;
import technology.scholz.bingo.domain.SpielSettings;
import technology.scholz.bingo.domain.SpielSettingsInbound;
import technology.scholz.bingo.exception.SpielNotFoundException;
import technology.scholz.bingo.repository.SpielRespository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SpielService {

    @Autowired
    private SpielRespository spielRespository;

    @Autowired
    private DefaultSpielConfigurations defaultSpielConfigurations;

    @Autowired
    private SpielfeldGenerationService spielfeldGenerationService;

    public List<Spiel> findAll() {
        return (List<Spiel>) spielRespository.findAll();
    }

    public Optional<Spiel> findById(UUID spielId) {
        return spielRespository.findById(spielId);
    }

    public Spiel createNewSpiel(SpielSettingsInbound spielSettingsInbound) {
        Spiel spiel = new Spiel(spielSettingsInbound.toSpielSettings());
        save(spiel);
        log.info("Spiel created " +spiel.toString());
        return spiel;
    }

    public Optional<Spiel> deleteSpiel(UUID spielId) {
        Optional<Spiel> maybeSpiel = findById(spielId);
        if(maybeSpiel.isEmpty()) return Optional.empty();
        spielRespository.deleteById(spielId);
        return maybeSpiel;
    }

    public List<List<Integer>> generateSpielfeld(UUID spielId) throws SpielNotFoundException {
        Optional<Spiel> maybeSpiel = findById(spielId);
        if(maybeSpiel.isEmpty()) throw new SpielNotFoundException(spielId);
        return spielfeldGenerationService.generateSpielfeld(maybeSpiel.get().getSettings());
    }

    public void save(Spiel spiel) {
        spielRespository.save(spiel);
    }
}

