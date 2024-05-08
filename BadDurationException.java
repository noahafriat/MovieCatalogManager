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
 * This exception is thrown to indicate that the duration of a movie has an invalid format.
 * It is a subclass of the Exception class.
 */
public class BadDurationException extends Exception {
    
    /**
     * Constructs a new BadDurationException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public BadDurationException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new BadDurationException with a default detail message indicating an invalid duration format.
     */
    public BadDurationException() {
        super("Invalid duration format");
    }
}