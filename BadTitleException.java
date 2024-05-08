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
 * This exception is thrown to indicate that a title has an invalid format.
 * It is a subclass of the Exception class.
 */
public class BadTitleException extends Exception {
    
    /**
     * Constructs a new BadTitleException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public BadTitleException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new BadTitleException with a default detail message indicating an invalid title format.
     */
    public BadTitleException() {
        super("Invalid title format");
    }
}
