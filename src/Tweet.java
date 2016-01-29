import java.util.HashSet;
import java.util.Set;


/**
 * A class to hold a tweet and it's metadata
 */

public class Tweet implements Comparable<Tweet> {
	// static variables
	private static int MaxId = 0;
	// instance variables
	private int id;
	private String content;

	public Tweet(String content) {
		this.id = MaxId++;
		this.content = content;
	}

	/**
	 * A method for getting the content of the tweet
	 *
	 * @return The content of the tweet
	 */
	public String getContent() {
		return content;
	}

	/**
	 * A method for getting the ID of the tweet
	 *
	 * @return The ID of the tweet
	 */
	public int getID() {
		return id;
	}

	/**
	 * A method for getting the words in a tweet
	 *
	 * @return The words in the tweet converted to lowercase
	 */
	public Set<String> getWords() {
		Set<String> words = new HashSet<String>();
		for (String w : getContent().toLowerCase().split("\\W+")) {
			if (!w.equals("")) {
				words.add(w);
			}
		}
		return words;
	}

	/**
	 * A method for comparing tweets
	 *
	 * @return > 0 if this.id > tweet.id, < 0 if this.id < tweet.id,
	 * and 0 if this.id == tweet.id
	 */
	public int compareTo(Tweet tweet) {
		return id - tweet.id;
	}

	/**
	 * Overrides Object's hashCode function. The hashCode function should return an
	 * int representing this tweet.
	 *
	 * @return An int reprseing this tweet
	 */
	public int hashCode() {
		return getID();
	}

	/**
	 * This equals method will rely on the output of the compareTo() method.
	 *
	 * @return true only if the Tweets have the same id
	 */
	public boolean equals(Object o) {
		if (o instanceof Tweet) {
			return compareTo((Tweet) o) == 0;
		} else {
			return false;
		}
	}

	/**
	 * A method for converting this tweet to a string
	 *
	 * @return The content of the tweet
	 */
	public String toString() {
		return getContent();
	}
}