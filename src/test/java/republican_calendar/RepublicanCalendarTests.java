package republican_calendar;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.Month;
import java.util.zip.DataFormatException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import republican_calendar.exception.RepublicanCalendarException;
import republican_calendar.types.RepublicanMonths;

public class RepublicanCalendarTests {
	@BeforeAll
	static void initAll() {
	}

	@BeforeEach
	void init() {
	}

	@Test
	void instentiationTest() {
		assertThrows(RepublicanCalendarException.class,
				() -> RepublicanCalendar.of(1, RepublicanMonths.VENDEMIAIRE, 0));
		assertThrows(RepublicanCalendarException.class,
				() -> RepublicanCalendar.of(1, RepublicanMonths.VENDEMIAIRE, 31));
		assertThrows(RepublicanCalendarException.class, () -> RepublicanCalendar.of(1, null, 1));
		assertThrows(RepublicanCalendarException.class,
				() -> RepublicanCalendar.of(2, RepublicanMonths.SANSCULOTTIDES, 6));
		assertThrows(RepublicanCalendarException.class,
				() -> RepublicanCalendar.of(3, RepublicanMonths.SANSCULOTTIDES, 7));

		assertDoesNotThrow(() -> RepublicanCalendar.of(1, RepublicanMonths.VENDEMIAIRE, 1));
	}

	@Test
	void convertDateToRepublicanCalendarTest() {
		LocalDate date = LocalDate.of(1792, Month.SEPTEMBER, 22);
		RepublicanCalendar rc = RepublicanCalendar.convertDateToRepublicanCalendar(date);

		assertEquals(1, rc.getDayNumber());
		assertEquals(RepublicanMonths.VENDEMIAIRE, rc.getMonth());
		assertEquals(1, rc.getYear());

		date = LocalDate.of(2024, Month.OCTOBER, 7);
		rc = RepublicanCalendar.convertDateToRepublicanCalendar(date);

		assertEquals(16, rc.getDayNumber());
		assertEquals(RepublicanMonths.VENDEMIAIRE, rc.getMonth());
		assertEquals(233, rc.getYear());
	}

	@Test
	void compareToTest() throws DataFormatException {
		try {
			RepublicanCalendar date1 = RepublicanCalendar.of(1, RepublicanMonths.VENDEMIAIRE, 1);
			RepublicanCalendar date2 = RepublicanCalendar.of(2, RepublicanMonths.SANSCULOTTIDES, 5);
			RepublicanCalendar date3 = new RepublicanCalendar();

			assertEquals(0, date1.compareTo(date3));
			assertTrue(date2.compareTo(date3) > 0);
			assertTrue(date1.compareTo(date2) < 0);
		} catch (RepublicanCalendarException e) {
			fail();
		}
	}

	@AfterEach
	void tearDown() {
	}

	@AfterAll
	static void tearDownAll() {
	}

}
