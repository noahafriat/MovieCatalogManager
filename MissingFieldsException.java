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
 * This exception is thrown to indicate that there are missing required fields.
 * It is a subclass of the Exception class.
 */
public class MissingFieldsException extends Exception {
    
    /**
     * Constructs a new MissingFieldsException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public MissingFieldsException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new MissingFieldsException with a default detail message indicating missing required fields.
     */
    public MissingFieldsException() {
        super("Missing required fields");
    }
}
