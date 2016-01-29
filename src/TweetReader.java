/* $Id: GradeReader.java,v 1.2 2008/01/28 17:45:08 ekstrand Exp $ */
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class for reading tweets from a data file. Adapted by
 * Jacob Thebault-Spieker <thebault@cs.umn.edu>
 * from the original GradeReader class by:
 * @author Michael Ekstrand <ekstrand@cs.umn.edu>
 *
 */
public class TweetReader {
    private Scanner scanner;
    
    private boolean valid;
    private String curID;
    private String curTweet;
    
    /**
     * Construct a new TweetReader to read tweets from a file.
     * @param filename The file to read tweets from.
     */
    public TweetReader(String filename) {
        valid = false;
        try {
            scanner = new Scanner(new File(filename),"UTF-8");
        } catch (FileNotFoundException e) {
            // convert the exception to a runtime exception so the client doesn't
            // need exception handling
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Advances the tweet reader to the next tweet.
     * @return {@code true} if there is another user/tweet pair to access, false
     * otherwise.
     */
    public boolean advance() {
        valid = false;
        try {
            curID = scanner.next();
            curTweet = scanner.nextLine();
            valid = true;
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    /**
     * Get the current student's ID.
     * @return The ID of the current twitter user
     */
    public String getTweeterID() {
        assertValid();
        return curID;
    }
    
    public String getTweet() {
    	assertValid();
    	return curTweet;
    }

    private void assertValid() {
        if (!valid)
            throw new RuntimeException("Tweet reader not in valid state.");
    }
}
