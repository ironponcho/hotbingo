package technology.scholz.bingo.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@DynamoDBTable(tableName = "bingo-spiel")
@Data
@NoArgsConstructor
@ToString
public class Spiel implements Serializable {

    public Spiel(SpielSettings settings) {
        this.name = RandomStringUtils.randomAlphabetic(10);
        this.id = UUID.randomUUID();
        this.gezogeneNummern = Collections.emptyList();
        this.aktionen = Collections.emptyList();
        this.spieler = Collections.emptyList();
        this.settings = settings;
        this.hostSecret = UUID.randomUUID();
        log.info("Created Spiel '{}' with hostSecret '{}'", id, hostSecret);
    }

    @DynamoDBHashKey
    @DynamoDBAttribute
    private UUID id;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private List<Spieler> spieler;

    @DynamoDBAttribute
    private List<Integer> gezogeneNummern;

    @DynamoDBAttribute
    private SpielSettings settings;

    @DynamoDBAttribute
    private List<Aktion> aktionen;

    @DynamoDBAttribute
    private UUID hostSecret;

    public void addSpieler(Spieler spieler) {
        this.spieler.add(spieler);
    }

    public void addGezogeneNummer(Integer gezogeneNummer) {
        gezogeneNummern.add(gezogeneNummer);
    }

    public void removeGezogeneNummer(Integer gezogeneNummer) {
        this.gezogeneNummern = gezogeneNummern.stream()
                .filter(nummer -> !nummer.equals(gezogeneNummer))
                .collect(Collectors.toList());
    }

    public List<Integer> assembleVerfuegbareNummern(){
        List<Integer> nummernToSelectFrom = this.settings.assembleAllNummern();
        nummernToSelectFrom.removeAll(this.getGezogeneNummern());
        return nummernToSelectFrom;
    }

    public void addErfolgteAktion(Aktion aktion) {
        aktionen.add(aktion);
    }

    public Optional<Spieler> getSpieler(UUID spielerId) {
        return getSpieler().stream().filter(spieler -> spieler.getId().equals(spielerId)).findFirst();
    }

    public SpielOutbound toOutbound() {
        return SpielOutbound.builder()
                .name(this.getName())
                .id(this.getId())
                .gezogeneNummern(this.getGezogeneNummern())
                .erfolgteAktionen(toAktionenOutbound(this.getAktionen()))
                .spieler(this.getSpieler())
                .settings(this.getSettings())
                .build();
    }

    private List<AktionOutbound> toAktionenOutbound(List<Aktion>aktionen){
        return aktionen.stream().map(Aktion::toOutbound).collect(Collectors.toList());
    }
}
