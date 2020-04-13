package technology.scholz.bingo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpielOutbound implements Serializable {

    private UUID id;
    private String name;
    private List<Spieler> spieler;
    private List<Integer> gezogeneNummern;
    private SpielSettings settings;
    private List<AktionOutbound> aktionen;

    public static SpielOutboundBuilder builder() {
        return new SpielOutboundBuilder();
    }

    public static class SpielOutboundBuilder {
        private UUID id;
        private String name;
        private List<Spieler> spieler;
        private List<Integer> gezogeneNummern;
        private SpielSettings settings;
        private List<AktionOutbound> aktionen;

        SpielOutboundBuilder() {
        }

        public SpielOutboundBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public SpielOutboundBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SpielOutboundBuilder spieler(List<Spieler> spieler) {
            this.spieler = spieler;
            return this;
        }

        public SpielOutboundBuilder gezogeneNummern(List<Integer> gezogeneNummern) {
            this.gezogeneNummern = gezogeneNummern;
            return this;
        }

        public SpielOutboundBuilder settings(SpielSettings settings) {
            this.settings = settings;
            return this;
        }

        public SpielOutboundBuilder erfolgteAktionen(List<AktionOutbound> aktionen) {
            this.aktionen = aktionen;
            return this;
        }

        public SpielOutbound build() {
            return new SpielOutbound(id, name, spieler, gezogeneNummern, settings, aktionen);
        }

    }
}
