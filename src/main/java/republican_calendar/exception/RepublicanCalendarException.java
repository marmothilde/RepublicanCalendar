package republican_calendar.exception;

public class RepublicanCalendarException extends Exception {

	@java.io.Serial
	private static final long serialVersionUID = -8312645534567038254L;

	/**
	 * Constructs a RepublicanCalendarException with no detail message.
	 */
	public RepublicanCalendarException() {
		super();
	}

	/**
	 * Constructs a RepublicanCalendarException with the specified detail message.
	 * A detail message is a String that describes this particular exception.
	 * @param s the String containing a detail message
	 */
	public RepublicanCalendarException(String s) {
		super(s);
	}

}
