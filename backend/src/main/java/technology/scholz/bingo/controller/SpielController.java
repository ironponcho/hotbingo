package technology.scholz.bingo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import technology.scholz.bingo.domain.*;
import technology.scholz.bingo.exception.SpielNotFoundException;
import technology.scholz.bingo.service.SpielService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Component
@RequestMapping("/spiel")
public class SpielController {

    @Autowired
    private SpielService spielService;

    @Autowired
    private SpielSettings defaultTwoDimensionalGame;

    @Autowired
    private List<Aktion> moeglicheAktionen;

    @PostMapping
    ResponseEntity<Spiel> createSpiel(@RequestBody SpielSettingsInbound spielSettingsInbound) {
        return ResponseEntity.ok(spielService.createNewSpiel(spielSettingsInbound));
    }

    @DeleteMapping("/{spielId}")
    ResponseEntity<String> deleteSpiel(@PathVariable UUID spielId) {

        if (spielService.deleteSpiel(spielId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SpielNotFoundException(spielId).getMessage());
        }
        return ResponseEntity.ok("Spiel mit Id " + spielId + " wurde gelöscht");
    }

    @GetMapping("/{spielId}")
    public ResponseEntity<SpielOutbound> getSpielById(@PathVariable UUID spielId) {
        Optional<Spiel> maybeSpiel = spielService.findById(spielId);
        if (maybeSpiel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(maybeSpiel.get().toOutbound());
    }

    @GetMapping("/all")
    public List<SpielOutbound> getAllSpiele() {
        return spielService.findAll().stream().map(Spiel::toOutbound).collect(Collectors.toList());
    }

    @GetMapping("/{spielId}/generatedSpielfeld")
    ResponseEntity<List<List<Integer>>> generateSpielfeld(@PathVariable UUID spielId) {
        try {
            return ResponseEntity.ok(spielService.generateSpielfeld(spielId));
        } catch (SpielNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/defaultSettings")
    ResponseEntity<SpielSettings> getDefaultSpielSettings() {
        return ResponseEntity.ok(defaultTwoDimensionalGame);
    }

    @GetMapping("/moeglicheAktionen")
    ResponseEntity<List<Aktion>> getPossibleActions() {
        return ResponseEntity.ok(moeglicheAktionen);
    }

    @RequestMapping(method = RequestMethod.OPTIONS, value = "/{spielId}")
    ResponseEntity<Object> getOptions(){
        log.info("OPTION_REQUEST_ANSWERED");
        return ResponseEntity.ok().build();
    }
}

