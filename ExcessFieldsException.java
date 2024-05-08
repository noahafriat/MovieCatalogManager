// -----------------------------------------------------------------
// Assignment 2
// Written by: Noah Afriat (40276193) and Robert Mounsef (40279248)
// -----------------------------------------------------------------

/**
 * Noah Afriat (40276193) and Robert Mounsef (40279248)
 * COMP249
 * Assignment 2 
 * March 27, 2024
 */

/**
 * This exception is thrown to indicate that there are excess fields provided.
 * It is a subclass of the Exception class.
 */
public class ExcessFieldsException extends Exception {
    
    /**
     * Constructs a new ExcessFieldsException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public ExcessFieldsException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new ExcessFieldsException with a default detail message indicating excess fields provided.
     */
    public ExcessFieldsException() {
        super("Excess fields provided");
    }
}
