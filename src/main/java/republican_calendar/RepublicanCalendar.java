package republican_calendar;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TreeMap;

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

	public RepublicanCalendar() {

		this.dayNumber = 1;
		this.month = RepublicanMonths.getByNumber(1);
		this.year = 1;
	}

	public static RepublicanCalendar convertDateToRepublicanCalendar(LocalDate date) {

		long daysBetween = ChronoUnit.DAYS.between(REFERENCE_DATE, date);

		RepublicanCalendar result = addDays(daysBetween);

		return result;

	}

	private boolean isYearSextile() {
		return this.year % 4 == 0;
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

	public String toString() {
		if (this.month.equals(RepublicanMonths.SANSCULOTTIDES)) {
			return "Jour " + SansCulottide.getByNumber(this.getDayNumber()).toString() + " de l'An "
					+ toRoman(this.year) + " (" + this.year + ")";
		}
		return this.dayNumber + " " + this.month.name() + " de l'An " + toRoman(this.year) + " (" + this.year + ")";
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
		LocalDate date = LocalDate.of(2024, Month.OCTOBER, 2);
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
