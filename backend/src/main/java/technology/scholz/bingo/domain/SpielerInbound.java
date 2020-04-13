package technology.scholz.bingo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import technology.scholz.bingo.exception.SpielerValidationException;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Data
public class SpielerInbound {

    private String name;
    private List<List<Integer>> spielfeld = Collections.emptyList();

    public SpielerInbound(String name, List<List<Integer>> spielfeld) {
        this.name = name.trim();
        this.spielfeld = spielfeld;
    }

    public Spieler toSpieler(SpielSettings spielSettings) throws SpielerValidationException {
        Spieler spieler = new Spieler(name, spielfeld);
        spieler.validate(spielSettings);
        return spieler;
    }

}