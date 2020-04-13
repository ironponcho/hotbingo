package technology.scholz.bingo.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class AktionOutbound {

    public AktionOutbound(UUID id, String ueberschrift, String beschreibung) {
        this.id = id;
        this.ueberschrift = ueberschrift;
        this.beschreibung = beschreibung;
    }

    private UUID id;
    private String ueberschrift;
    private String beschreibung;

}
