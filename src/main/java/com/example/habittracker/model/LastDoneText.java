package com.example.habittracker.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Describes when a habit was last completed, for showing on a habit row.
 */
public class LastDoneText {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH);

    private LastDoneText() {
    }

    /**
     * Gets the text for a habit's last completion, e.g. "Done today" or "Last done 1 Oct".
     */
    public static String describe(LocalDate lastDone, LocalDate today) {
        if (lastDone == null) {
            return "Not done yet";
        }
        if (lastDone.equals(today)) {
            return "Done today";
        }
        if (lastDone.equals(today.minusDays(1))) {
            return "Last done yesterday";
        }
        return "Last done " + lastDone.format(DATE_FORMAT);
    }
}
