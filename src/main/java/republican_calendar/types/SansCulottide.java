package republican_calendar.types;

public enum SansCulottide {

	Vertu(1), Genie(2), Travail(3), Opinion(4), Recompenses(5), Revolution(6);

	private int dayNumber;

	SansCulottide(int number) {
		this.dayNumber = number;
	}

	public static SansCulottide getByNumber(int dayNumber) {
		for (SansCulottide e : values()) {
			if (e.dayNumber == dayNumber) {
				return e;
			}
		}
		return null;
	}

	public String toString() {
		switch (this) {
		case Genie:
			return "du Génie";
		case Opinion:
			return "de l'Opinion";
		case Recompenses:
			return "des Récompenses";
		case Revolution:
			return "de la Révolution";
		case Travail:
			return "du Travail";
		case Vertu:
			return "de la Vertu";
		default:
			return "";

		}
	}
}
