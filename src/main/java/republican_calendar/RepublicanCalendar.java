package republican_calendar;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TreeMap;

import republican_calendar.exception.RepublicanCalendarException;
import republican_calendar.types.RepublicanMonths;
import republican_calendar.types.SansCulottide;

public class RepublicanCalendar {

	private int dayNumber;
	private RepublicanMonths month;
	private int year;

	private final static LocalDate REFERENCE_DATE = LocalDate.of(1792, Month.SEPTEMBER, 22);

	private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

	static {

		map.put(1000, "M");
		map.put(900, "CM");
		map.put(500, "D");
		map.put(400, "CD");
		map.put(100, "C");
		map.put(90, "XC");
		map.put(50, "L");
		map.put(40, "XL");
		map.put(10, "X");
		map.put(9, "IX");
		map.put(5, "V");
		map.put(4, "IV");
		map.put(1, "I");

	}

	/**
	 * Default constructor <br>
	 * Return an instance of ReplublicanCalendar that's equal to the REFERENCE_DATE
	 * (or EPOCH) of the calendar (september 22, 1792)
	 */
	public RepublicanCalendar() {
		this.dayNumber = 1;
		this.month = RepublicanMonths.VENDEMIAIRE;
		this.year = 1;
	}

	private RepublicanCalendar(int year, RepublicanMonths month, int day) {
		this.dayNumber = day;
		this.month = month;
		this.year = year;
	}

	/**
	 * Return an instance of RepublicanCalendar
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws RepublicanCalendarException if the value of any field is out of range,
	 *  or if the day-of-month is invalid for the month-year
	 */
	public static RepublicanCalendar of(int year, RepublicanMonths month, int day) throws RepublicanCalendarException {
		if (month == null) {
			throw new RepublicanCalendarException("Month must be non-null");
		}
		validateDate(year, month.getMonthNumber(), day);
		return new RepublicanCalendar(year, month, day);
	}

	private static void validateDate(int year, int month, int day) throws RepublicanCalendarException {

		if (day <= 0 || RepublicanMonths.getByNumber(month) == null || year == 0) {
			throw new RepublicanCalendarException("Day must be superior to 0 ans month must be between 1 and 13 and there is no year 0");
		}
		if (day > 30) {
			throw new RepublicanCalendarException("Month only have 30 days");
		}
		if (RepublicanMonths.getByNumber(month).equals(RepublicanMonths.SANSCULOTTIDES) && day > 5) {
			if (!RepublicanCalendar.isYearSextile(year) || day > 6) {
				throw new RepublicanCalendarException("The Sans culottides only have 5 days (6 if it's a sextile year). The year "
						+ year + (isYearSextile(year) ? " is " : " is not ") + " sextile");
			}
		}
	}

	/**
	 * Convert a date into it's representation in the Republican Calendar<br>
	 * if the date is beyond january 1st, 1806, the date is just an guess of what it
	 * could have been.
	 * 
	 * @param date the date to convert
	 * @return
	 */
	public static RepublicanCalendar convertDateToRepublicanCalendar(LocalDate date) {

		long daysBetween = ChronoUnit.DAYS.between(REFERENCE_DATE, date);

		RepublicanCalendar result = addDays(daysBetween);

		return result;
	}

	/**
	 * Compare two RepublicanCalendar
	 * 
	 * @param rc
	 * @return 0 if the two dates are equale <br>
	 *         an int superior to 0 if the date given in parameter is after than the
	 *         other<br>
	 *         an int inferior to 0 if the date given in parameter is before than
	 *         the other<br>
	 * 
	 */
	public int compareTo(RepublicanCalendar rc) {
		int cmp = (this.year - rc.year);
		if (cmp == 0) {
			cmp = (this.month.getMonthNumber() - rc.month.getMonthNumber());
			if (cmp == 0) {
				cmp = (this.dayNumber - rc.dayNumber);
			}
		}
		return cmp;
	}

	/**
	 * @return true if the year correspond in a leap year in the Republican
	 *         Calendar, false otherwise.
	 */
	public boolean isYearSextile() {
		return isYearSextile(this.year);
	}

	public static boolean isYearSextile(int year) {
		if (year >= 18 && String.valueOf(year).endsWith("00") && !(year % 400 == 0)) {
			return false;
		} else if (year >= 18) {
			return year % 4 == 0;
		}
		return (year + 1) % 4 == 0;
	}

	private static RepublicanCalendar addDays(long numberOfDays) {

		RepublicanCalendar result = new RepublicanCalendar();
		for (long i = 0; i < numberOfDays; i++) {
			if (!result.month.equals(RepublicanMonths.SANSCULOTTIDES)) {

				if (result.dayNumber < 30) {
					result.dayNumber++;
				} else {
					result.dayNumber = 1;
					result.month = RepublicanMonths.getByNumber(result.month.getMonthNumber() + 1);
				}
			} else {
				if (result.dayNumber < 5) {
					result.dayNumber++;
				} else if (result.dayNumber == 5 && result.isYearSextile()) {
					result.dayNumber++;
				} else {
					result.dayNumber = 1;
					result.month = RepublicanMonths.getByNumber(1);
					result.year++;
				}
			}
		}
		return result;

	}

	/**
	 * Return a String that represent the date in the Republican Calendar <br>
	 * Example : 9 VENDEMIAIRE de l'An CCXXXIII (corresponding to october 2,
	 * 2024)<br>
	 * If the date is one of the "Sans Culottides" (i.e the five or six days at the
	 * end of the year without a month, the method will return the name of the day
	 * instead of the number<br>
	 * Example : Jour des Récompenses de l'An XI (September 22, 1803)
	 */
	@Override
	public String toString() {
		if (this.month.equals(RepublicanMonths.SANSCULOTTIDES)) {
			return "Jour " + SansCulottide.getByNumber(this.getDayNumber()).toString() + " de l'An "
					+ toRoman(this.year);
		}
		return this.dayNumber + " " + this.month + " de l'An " + toRoman(this.year);
	}

	public boolean equals(RepublicanCalendar rc) {
		return this.compareTo(rc) == 0;

	}

	private String toRoman(int year) {
		int number = year;
		int l = map.floorKey(number);
		if (number == l) {
			return map.get(number);
		}
		return map.get(l) + toRoman(number - l);

	}

	public static void main(String[] args) {

		Date start = new Date();
		LocalDate date = LocalDate.of(2024, Month.OCTOBER, 7);
		System.out.println(RepublicanCalendar.convertDateToRepublicanCalendar(date).toString());

		System.out.println((System.currentTimeMillis() - start.getTime()));
	}

	public int getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	public RepublicanMonths getMonth() {
		return month;
	}

	public void setMonth(RepublicanMonths month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public static LocalDate getREFERENCE_DATE() {
		return REFERENCE_DATE;
	}
}
