package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.LEAP_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALENDAR_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_FEB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTH_JUN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_2018;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_MONTH;
import static seedu.address.testutil.CalendarBuilder.DEFAULT_YEAR;
import static seedu.address.testutil.TypicalCalendars.DEFAULT_CALENDAR;
import static seedu.address.testutil.TypicalCalendars.DEFAULT_CALENDAR_NAME;

import java.util.HashMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author GilgameshTC
public class CalendarModelTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CalendarModel calendarModel = new CalendarModel(new HashMap<>());

    @Test
    public void isExistingCalendar_nullYear_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        calendarModel.isExistingCalendar(null, VALID_MONTH_JAN);

    }

    @Test
    public void isExistingCalendar_nullMonth_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        calendarModel.isExistingCalendar(VALID_YEAR_2018, null);

    }

    @Test
    public void isExistingCalendar_calendarNotInCalendarModel_returnsFalse() {
        assertFalse(calendarModel.isExistingCalendar(VALID_YEAR_2018, VALID_MONTH_FEB));
    }

    @Test
    public void isExistingCalendar_calendarInCalendarModel_returnsTrue() {
        calendarModel.updateExistingCalendar(VALID_YEAR_2018, VALID_MONTH_FEB);
        assertTrue(calendarModel.isExistingCalendar(VALID_YEAR_2018, VALID_MONTH_FEB));

    }

    @Test
    public void isLoadedCalendar_nullYear_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        calendarModel.isLoadedCalendar(null, VALID_MONTH_JAN);

    }

    @Test
    public void isLoadedCalendar_nullMonth_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        calendarModel.isLoadedCalendar(VALID_YEAR_2018, null);

    }

    @Test
    public void isLoadedCalendar_calendarNotLoadedInCalendarModel_returnsFalse() {
        assertFalse(calendarModel.isLoadedCalendar(DEFAULT_YEAR, DEFAULT_MONTH));

    }

    @Test
    public void isLoadedCalendar_calendarLoadedInCalendarModel_returnsTrue() {
        calendarModel.loadCalendar(DEFAULT_CALENDAR, DEFAULT_CALENDAR_NAME);
        assertTrue(calendarModel.isLoadedCalendar(DEFAULT_YEAR, DEFAULT_MONTH));

    }

    @Test
    public void isValidDate_nullYear_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        calendarModel.isValidDate(null, VALID_MONTH_JAN, VALID_CALENDAR_DATE);

    }

    @Test
    public void isValidDate_nullMonth_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        calendarModel.isValidDate(VALID_YEAR_2018, null, VALID_CALENDAR_DATE);

    }

    @Test
    public void isValidDate() {
        //invalid date
        assertFalse(calendarModel.isValidDate(VALID_YEAR_2018, VALID_MONTH_JAN, -1)); // negative date
        assertFalse(calendarModel.isValidDate(VALID_YEAR_2018, VALID_MONTH_JAN, 0)); // 0 date
        assertFalse(calendarModel.isValidDate(VALID_YEAR_2018, VALID_MONTH_JUN, 31)); // no 31st in June
        assertFalse(calendarModel.isValidDate(VALID_YEAR_2018, VALID_MONTH_JAN, 32)); // date greater than 31
        assertFalse(calendarModel.isValidDate(VALID_YEAR_2018, VALID_MONTH_FEB, 29)); // non-leap year, feb 29

        //valid date
        assertTrue(calendarModel.isValidDate(VALID_YEAR_2018, VALID_MONTH_JAN, 1));
        assertTrue(calendarModel.isValidDate(VALID_YEAR_2018, VALID_MONTH_JAN, 31)); // Month with 31st as last day
        assertTrue(calendarModel.isValidDate(VALID_YEAR_2018, VALID_MONTH_JUN, 30)); // Month with 30th as last day
        assertTrue(calendarModel.isValidDate(LEAP_YEAR, VALID_MONTH_FEB, 29)); // leap year, feb 29

    }

    @Test
    public void isValidTime() {
        // Time format is in accordance with the 24 hour format
        // invalid time
        assertFalse(calendarModel.isValidTime(-1, 30)); // negative hour
        assertFalse(calendarModel.isValidTime(10, -1)); // negative minutes
        assertFalse(calendarModel.isValidTime(24, 30)); // latest timing is 2359, hour should not exceed 23
        assertFalse(calendarModel.isValidTime(1, 60)); // minutes should not exceed 59

        // valid time
        assertTrue(calendarModel.isValidTime(0, 0)); // 0000
        assertTrue(calendarModel.isValidTime(23, 59)); // 2359

    }

    @Test
    public void isValidTimeFrame() {
        // isValidTimeFrame assumes that date and time is valid
        // invalid time frame
        // Start date > End date
        assertFalse(calendarModel.isValidTimeFrame(2, 0,0,1,0,0));
        // Start date = End date, Start hour = End hour, Start Min > End Min
        assertFalse(calendarModel.isValidTimeFrame(1, 0,30,1,0,0));
        // Same date
        assertFalse(calendarModel.isValidTimeFrame(1, 0,0,1,0,0));
        // Start date = End date, Start hour > End hour, Start Min = End Min
        assertFalse(calendarModel.isValidTimeFrame(1, 10,0,1,0,0));
        // Start date = End date, Start hour > End hour, Start Min < End Min
        assertFalse(calendarModel.isValidTimeFrame(1, 5,0,1,0,30));

        // valid time frame
        // Start date < End date
        assertTrue(calendarModel.isValidTimeFrame(1, 0,0,5,0,0));
        // Start date = End date, Start hour < End hour, Start Min = End Min
        assertTrue(calendarModel.isValidTimeFrame(1, 5,0,1,6,0));
        // Start date = End date, Start hour < End hour, Start Min < End Min
        assertTrue(calendarModel.isValidTimeFrame(1, 5,0,1,6,30));
        // Start date = End date, Start hour = End hour, Start Min < End Min
        assertTrue(calendarModel.isValidTimeFrame(1, 5,0,1,5,30));

    }

}
