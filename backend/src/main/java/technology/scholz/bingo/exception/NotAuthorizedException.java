package technology.scholz.bingo.exception;

import java.util.UUID;

public class NotAuthorizedException extends BingoException{

    public NotAuthorizedException(UUID spielId, UUID spielerId) {
        super("Das AdminSecret " +spielerId +" ist nicht gültig für das Spiel " +spielId);
    }
}
