package technology.scholz.bingo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class HostInput {

    private UUID hostSecret;
    private int gezogeneNummer;

}
