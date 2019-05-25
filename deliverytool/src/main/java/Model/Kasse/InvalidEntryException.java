package Model.Kasse;

public class InvalidEntryException extends Exception {

    /**
     * @author Jannik Will
     * @version 1.0*/

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
