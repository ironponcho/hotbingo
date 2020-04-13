package technology.scholz.bingo.exception;

import java.util.UUID;

public class SpielNotFoundException extends BingoException {

    public SpielNotFoundException(UUID spielId) {
        super("Das Spiel mit der ID " +spielId + " wurde nicht gefunden");
    }
}
