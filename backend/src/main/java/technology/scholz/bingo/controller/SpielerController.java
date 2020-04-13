package technology.scholz.bingo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import technology.scholz.bingo.domain.HostInput;
import technology.scholz.bingo.domain.Spieler;
import technology.scholz.bingo.domain.SpielerInbound;
import technology.scholz.bingo.exception.BingoException;
import technology.scholz.bingo.service.SpielerService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/spiel")
public class SpielerController {

    @Autowired
    private SpielerService spielerService;

    @GetMapping("/{spielId}/spieler/{spielerId}")
    ResponseEntity<Spieler> getSpielerBySpielerId(@PathVariable("spielId") UUID spielId, @PathVariable("spielerId") String spielerId) {
        Optional<Spieler> maybeSpieler = spielerService.findSpielerBySpielIdAndSpielerId(spielId, UUID.fromString(spielerId));
        if (maybeSpieler.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(maybeSpieler.get());
        }
    }

    @GetMapping("/{spielId}/spieler/{spielerId}/hasBingo")
    ResponseEntity<Object> hasSpielerBingo(@PathVariable("spielId") UUID spielId, @PathVariable("spielerId") String spielerId) {

        try {
            return ResponseEntity.ok(spielerService.spielerHasBingo(spielId, UUID.fromString(spielerId)));
        } catch (BingoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{spielId}/spieler")
    List<Spieler> findSpielerBySpielId(@PathVariable UUID spielId) {
        return spielerService.findSpielerBySpielId(spielId);
    }

    @PostMapping("/{spielId}/spieler")
    ResponseEntity<Object> postSpieler(@PathVariable("spielId") UUID spielId, @RequestBody SpielerInbound spielerInbound) {
        if (Objects.isNull(spielerInbound)) {
            return ResponseEntity.badRequest().body("Dieser Spieler kann nicht angelegt werden");
        }

        Spieler angelegterSpieler;
        try {
            angelegterSpieler = spielerService.addSpieler(spielId, spielerInbound);
        } catch (BingoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(angelegterSpieler);
    }

    @PostMapping("/{spielId}/spieler/auto")
    ResponseEntity<Object> createSpielerAndAutogenerateSpielfeld(@PathVariable UUID spielId, @RequestBody HostInput hostInput) {
        try {
            return ResponseEntity.ok(spielerService.addRandomSpieler(spielId, hostInput.getHostSecret()));
        } catch (BingoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

