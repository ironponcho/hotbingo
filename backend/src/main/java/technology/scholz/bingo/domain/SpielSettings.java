package technology.scholz.bingo.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class defines the boundaries for each individual game
 */
@Data
@NoArgsConstructor
@DynamoDBDocument
public class SpielSettings {

    @DynamoDBAttribute
    private int maxNummer;

    @DynamoDBAttribute
    private int minNummer;

    @DynamoDBAttribute
    private int spielfeldElementsPerDimension;

    @DynamoDBAttribute
    private int spielfeldDimensions;

    @DynamoDBAttribute
    private int wahrscheinlichkeitAktionInProzent;

    SpielSettings(int maxNummer, int minNummer, int spielfeldElementsPerDimension, int spielfeldDimensions, int wahrscheinlichkeitAktionInProzent, List<Aktion> moeglicheAktionen) {
        this.maxNummer = maxNummer;
        this.minNummer = minNummer;
        this.spielfeldElementsPerDimension = spielfeldElementsPerDimension;
        this.spielfeldDimensions = spielfeldDimensions;
        this.wahrscheinlichkeitAktionInProzent = wahrscheinlichkeitAktionInProzent;
    }

    public static SpielSettingsBuilder builder() {
        return new SpielSettingsBuilder();
    }

    public List<Integer> assembleAllNummern() {
        return IntStream.range(getMinNummer(), getMaxNummer() + 1).boxed().collect(Collectors.toList());
    }

    public static class SpielSettingsBuilder {
        private int maxNumber;
        private int minNumber;
        private int spielfeldElementsPerDimension;
        private int spielfeldDimensions;
        private int wahrscheinlichkeitAktionInProzent;
        private List<Aktion> moeglicheAktionen;

        SpielSettingsBuilder() {
        }

        public SpielSettingsBuilder maxNumber(int maxNumber) {
            this.maxNumber = maxNumber;
            return this;
        }

        public SpielSettingsBuilder minNumber(int minNumber) {
            this.minNumber = minNumber;
            return this;
        }

        public SpielSettingsBuilder spielfeldElementsPerDimension(int spielfeldElementsPerDimension) {
            this.spielfeldElementsPerDimension = spielfeldElementsPerDimension;
            return this;
        }

        public SpielSettingsBuilder spielfeldDimensions(int spielfeldDimensions) {

            if(spielfeldDimensions!=2){
                throw new IllegalArgumentException("Es sind nur zwei SpielDimensionen aktuell erlaubt");
            }
            this.spielfeldDimensions = spielfeldDimensions;
            return this;
        }

        public SpielSettingsBuilder wahrscheinlichkeitAktionInProzent(int wahrscheinlichkeitAktionInProzent) {

            if(wahrscheinlichkeitAktionInProzent<0 || wahrscheinlichkeitAktionInProzent>100){
                throw new IllegalArgumentException("wahrscheinlichkeitAktionInProzent muss zwischen 0 und 100 liegen. Übergeben wurde " +wahrscheinlichkeitAktionInProzent);
            }

            this.wahrscheinlichkeitAktionInProzent = wahrscheinlichkeitAktionInProzent;
            return this;
        }

        public SpielSettingsBuilder moeglicheAktionen(List<Aktion>moeglicheAktionen) {
            if(moeglicheAktionen == null){
                moeglicheAktionen = Collections.emptyList();
            }
            this.moeglicheAktionen = moeglicheAktionen;
            return this;
        }

        public SpielSettings build() {
            return new SpielSettings(maxNumber, minNumber, spielfeldElementsPerDimension, spielfeldDimensions, wahrscheinlichkeitAktionInProzent, moeglicheAktionen);
        }

        public String toString() {
            return "SpielSettings.SpielSettingsBuilder(maxNumber=" + this.maxNumber + ", minNumber=" + this.minNumber + ", spielfeldElementsPerDimension=" + this.spielfeldElementsPerDimension + ", spielfeldDimensions=" + this.spielfeldDimensions + ")";
        }
    }
}
