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
 * This exception is thrown to indicate that there are missing quotation marks.
 * It is a subclass of the Exception class.
 */
public class MissingQuotesException extends Exception {
    
    /**
     * Constructs a new MissingQuotesException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public MissingQuotesException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new MissingQuotesException with a default detail message indicating missing quotation marks.
     */
    public MissingQuotesException() {
        super("Missing quotation marks");
    }
}
