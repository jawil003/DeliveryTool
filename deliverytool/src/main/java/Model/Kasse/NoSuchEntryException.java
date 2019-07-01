package Model.Kasse;

/**
 * @author Jannik Will
 * @version 1.0
 */

public class NoSuchEntryException extends Exception {
    public NoSuchEntryException(String message){
        super(message);
    }
}
