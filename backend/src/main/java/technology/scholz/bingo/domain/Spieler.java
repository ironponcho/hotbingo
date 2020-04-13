package technology.scholz.bingo.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Data;
import lombok.NoArgsConstructor;
import technology.scholz.bingo.exception.SpielerValidationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@NoArgsConstructor
@Data
@DynamoDBDocument
public class Spieler {

    @DynamoDBAttribute
    private UUID id;
    @DynamoDBAttribute
    private String name;
    @DynamoDBAttribute
    private List<List<Integer>> spielfeld = Collections.emptyList();

    public Spieler(String name, List<List<Integer>> spielfeld) {
        this.id = UUID.randomUUID();
        this.name = name.trim();
        this.spielfeld = spielfeld;
    }

    public void validate(SpielSettings spielSettings) throws SpielerValidationException {
        if (name.isBlank()) {
            throw new SpielerValidationException("Spielername darf nicht leer sein");
        }
        validateSpielfeldRows(spielSettings);
        validateSpielfeldColumns(spielSettings);
        validateSpielfeldNummern(spielSettings);
    }

    public boolean hasBingo(List<Integer> gezogeneNummern) {
        for(int i=0; i<spielfeld.size(); i++) {
            List<Integer> row = spielfeld.get(i);
            if(gezogeneNummern.containsAll(row)) {
                return true;
            }
        }

        List<Integer> column = new ArrayList<>();
        for(int j=0; j<spielfeld.size(); j++) {
            for (int i = 0; i < spielfeld.size(); i++) {
                column.add(spielfeld.get(i).get(j));
            }
            if (gezogeneNummern.containsAll(column)) {
                return true;
            }
            column.clear();
        }

        List<Integer> diagonalDown = new ArrayList<>();
        for(int i=0; i<spielfeld.size(); i++){
            diagonalDown.add(spielfeld.get(i).get(i));
        }
        if (gezogeneNummern.containsAll(diagonalDown)) {
            return true;
        }

        List<Integer> diagonalUp = new ArrayList<>();
        for(int i=0; i<spielfeld.size(); i++){
            diagonalUp.add(spielfeld.get(i).get(spielfeld.size() -i -1));
        }
        return gezogeneNummern.containsAll(diagonalUp);
    }

    public boolean hasNumber(Integer gezogeneNummer) {
        return getConsolidatedList().stream().anyMatch(nummer -> nummer.equals(gezogeneNummer));
    }

    private void validateSpielfeldNummern(SpielSettings spielSettings) throws SpielerValidationException {
        List<Integer> result = getConsolidatedList();
        Integer lastValue = 0;
        for (Integer i : result) {
            if (i < 1 || i > spielSettings.getMaxNummer()) {
                throw new SpielerValidationException("Es sind nur positive Zahlen zwischen 1 und " + spielSettings.getMaxNummer() + " erlaubt.");
            }
            if (i.equals(lastValue)) {
                throw new SpielerValidationException("Es sind keine doppelten Nummern erlaubt. " + i + " taucht mehrfach auf");
            }
            lastValue = i;
        }

    }

    private void validateSpielfeldColumns(SpielSettings spielSettings) throws SpielerValidationException {
        AtomicBoolean isValid = new AtomicBoolean(true);
        spielfeld.forEach(
                list -> {
                    if (list.size() != spielSettings.getSpielfeldElementsPerDimension()) {
                        isValid.set(false);
                    }
                }
        );
        if (!isValid.get()) {
            throw new SpielerValidationException("Ein Spielfeld muss immer " + spielSettings.getSpielfeldElementsPerDimension() + " Spalten haben");
        }
    }

    private void validateSpielfeldRows(SpielSettings spielSettings) throws SpielerValidationException {
        if (spielfeld.size() > spielSettings.getSpielfeldElementsPerDimension()) {
            throw new SpielerValidationException("Ein Spielfeld muss immer " + spielSettings.getSpielfeldElementsPerDimension() + " Zeilen haben");
        }
    }

    /**
     * @return Gibt eine sortierte Liste mit allen Zahlen des Spielfelds zurück
     */
    private List<Integer> getConsolidatedList() {
        List<Integer> result = new ArrayList<>();
        for (List<Integer> list : spielfeld) {
            result.addAll(list);
        }
        Collections.sort(result);
        return result;
    }

}