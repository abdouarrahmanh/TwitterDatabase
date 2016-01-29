public class TwitterUser implements Comparable<TwitterUser> {
	//static variables
	private static int MaxId;
	//instance variables
	private int id;
	private String name;

	public TwitterUser(String name) {
		this.id = MaxId++;
		this.name = name;
	}
	/**
	 * A method for getting the name of this user
	 * @return The name of this user
	 */
	public String getName() {
		return name;
	}
	/**
	 * A method for getting the ID of this user
	 * @return The ID of this user
	 */
	public int getID() {
		return id;
	}
	/**
	 * A method for comparing users
	 * @return > 0 if this.id > user.id, < 0 if this.id < user.id,
	 * and 0 if this.id = user.id
	 */
	public int compareTo(TwitterUser user) {
		return id - user.id;
	}
	/**
	 * This equals method will rely on the output of the compareTo() method.
	 * @return true only if the TwitterUsers have the same id
	 */
	public boolean equals(Object o) {
		if (o instanceof TwitterUser) {
			return compareTo((TwitterUser) o) == 0;
		} else {
			return false;
		}
	}
	/**
	 * Overrides Object's hashCode function. The hashCode function should return an
	 * int representing this userd.
	 * @return An int representing this user
	 */
	public int hashCode() {
		return getID();
	}
	/**
	 * A method for converting this user to a string
	 * @return Returns a string representing this user
	 */
	public String toString() {
		return "@" + getName();
	}
}