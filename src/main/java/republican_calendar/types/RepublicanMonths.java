package republican_calendar.types;

public enum RepublicanMonths {

	VENDEMIAIRE(1), BRUMAIRE(2), FRIMAIRE(3), NIVOSE(4), PLUVIOSE(5), VENTOSE(6), GERMINAL(7), FLOREAL(8), PRAIRIAL(9),
	MESSIDOR(10), THERMIDOR(11), FRUCTIDOR(12), SANSCULOTTIDES(13);

	private int monthNumber;

	RepublicanMonths(int number) {
		this.monthNumber = number;
	}

	public int getMonthNumber() {
		return monthNumber;
	}

	public static RepublicanMonths getByNumber(int monthNumber) {
		for (RepublicanMonths e : values()) {
			if (e.monthNumber == monthNumber) {
				return e;
			}
		}
		return null;
	}

}
