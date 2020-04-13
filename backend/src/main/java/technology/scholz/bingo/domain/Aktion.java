package technology.scholz.bingo.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.*;

import java.util.UUID;

@Data
@DynamoDBDocument
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Aktion {

    public Aktion(String ueberschrift, String beschreibung) {
        this.id = UUID.randomUUID();
        this.ueberschrift = ueberschrift;
        this.beschreibung = beschreibung;
    }

    @DynamoDBAttribute
    private UUID id;
    @DynamoDBAttribute
    private String ueberschrift;
    @DynamoDBAttribute
    private String beschreibung;

    public AktionOutbound toOutbound(){
        return new AktionOutbound(id, ueberschrift, beschreibung);
    }

}
