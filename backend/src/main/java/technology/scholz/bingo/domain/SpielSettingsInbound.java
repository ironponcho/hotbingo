package technology.scholz.bingo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import technology.scholz.bingo.config.DefaultSpielConfigurations;

@Data
@NoArgsConstructor
public class SpielSettingsInbound {

    private int maxNummer;
    private int minNummer;
    private int spielfeldElementsPerDimension;
    private int spielfeldDimensions;
    private int wahrscheinlichkeitAktionInProzent;

    SpielSettingsInbound(int maxNummer, int minNummer, int spielfeldElementsPerDimension, int spielfeldDimensions, int wahrscheinlichkeitAktionInProzent) {
        this.maxNummer = maxNummer;
        this.minNummer = minNummer;
        this.spielfeldElementsPerDimension = spielfeldElementsPerDimension;
        this.spielfeldDimensions = spielfeldDimensions;
        this.wahrscheinlichkeitAktionInProzent = wahrscheinlichkeitAktionInProzent;
    }

    public SpielSettings toSpielSettings() {
        return SpielSettings.builder().maxNumber(
                this.maxNummer)
                .minNumber(this.minNummer)
                .spielfeldElementsPerDimension(this.spielfeldElementsPerDimension)
                .spielfeldDimensions(this.spielfeldDimensions)
                .wahrscheinlichkeitAktionInProzent(this.wahrscheinlichkeitAktionInProzent)
                .moeglicheAktionen(DefaultSpielConfigurations.moeglicheAktionen())
                .build();
    }
}