/*
 * Copyright (c) 2019. Jannik Will und Albert Munsch
 */

package Model.Kasse;


/**
 * @author Jannik Will
 * @version 1.0
 */

public class InvalidEntryException extends Exception {

    /**
     * @param message
     */
    public InvalidEntryException(String message) {
        super(message);
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Print out the message whith the whole Stack trace
     */
    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
