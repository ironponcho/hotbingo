package technology.scholz.bingo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import technology.scholz.bingo.domain.HostInput;
import technology.scholz.bingo.exception.BingoException;
import technology.scholz.bingo.service.NummerService;

import java.util.Optional;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/spiel")
public class NummerController {

    @Autowired
    private NummerService nummerService;

    @PostMapping("{spielId}/nummer")
    ResponseEntity<Object> postGezogeneNummer(@PathVariable("spielId") UUID spielId, @RequestBody HostInput hostInput) {
        try {
            return ResponseEntity.ok(nummerService.addGezogeneNummer(spielId, hostInput));
        } catch (BingoException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping("{spielId}/nummer/{zuLoeschen}")
    void removeGezogeneNummer(@PathVariable("spielName") UUID spielId, @PathVariable("zuLoeschen") Integer zuLoeschen) {
        nummerService.removeGezogeneNummer(spielId, zuLoeschen);
    }

    @GetMapping("/{spielId}/randomNummer")
    ResponseEntity<Object> getGezogeneNummer(@PathVariable("spielId") UUID spielId) {
        try {
            Optional<Integer> maybeRandomNummer = nummerService.getRandomGezogeneNummer(spielId);
            return maybeRandomNummer.<ResponseEntity<Object>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.badRequest().body("Es ist keine Nummer mehr verfügbar"));
        } catch (BingoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}

