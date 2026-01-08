package main.loantrackingbackend.util;

import java.time.LocalDate;

/**
 * A utility class to simulate and manipulate dates for testing purposes.
 * Useful for testing payments, installment terms, and entry dateFullyPaid logic.
 */
public class TestDateManager {

    private static LocalDate simulatedToday = LocalDate.now();

    /**
     * Returns the current "simulated" date.
     */
    public static LocalDate today() {
        return simulatedToday;
    }

    /**
     * Advance the simulated date by a number of days.
     *
     * @param days number of days to advance
     */
    public static void advanceDays(int days) {
        simulatedToday = simulatedToday.plusDays(days);
    }

    /**
     * Go back the simulated date by a number of days.
     *
     * @param days number of days to rewind
     */
    public static void rewindDays(int days) {
        simulatedToday = simulatedToday.minusDays(days);
    }

    /**
     * Set the simulated date to a specific date.
     *
     * @param date the date to set as "today"
     */
    public static void setDate(LocalDate date) {
        simulatedToday = date;
    }

    /**
     * Reset the simulated date to the real system date.
     */
    public static void reset() {
        simulatedToday = LocalDate.now();
    }
}