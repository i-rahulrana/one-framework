package org.one.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class DateUtil {
    public static final Logger LOGGER = LogManager.getLogger(DateUtil.class);

    /**
     * This method is used to get the current year.
     *
     * @return current year
     */
    public static String getCurrentYear() {
        String year;
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        return year;
    }

    /**
     * This method is used to get the Next year.
     *
     * @return next year
     */
    public static String getNextYear(int yr) {
        String year = null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, yr);
        year = String.valueOf(calendar.get(Calendar.YEAR));
        return year;
    }

    /**
     * This method is used to get the future date of given month difference.
     *
     * @param month
     * @return future date
     */
    public static String getFutureDate(int month) {
        Calendar now = Calendar.getInstance();
        // Add days to current date using Calendar.add method
        now.add(Calendar.MONTH, month);
        return ((now.get(Calendar.MONTH) + 1) + "/" + now.get(Calendar.DATE)
                + "/" + now.get(Calendar.YEAR));
    }

    /**
     * This method is used to get past date of given days before and in given
     * format.
     *
     * @param daysBefore
     * @return
     */
    public static String getPastDate(int daysBefore) {
        LocalDate now = LocalDate.now();
        LocalDate pastDate = now.minusDays(daysBefore);
        return pastDate.toString();
    }

    /**
     * This method is used to get the number of days between two given dates.
     *
     * @param dateBeforeString
     * @param dateAfterString
     * @return
     */
    public static long getNoOfDaysBetween(String dateBeforeString, String dateAfterString) {
        LocalDate dateBefore = LocalDate.parse(dateBeforeString);
        LocalDate dateAfter = LocalDate.parse(dateAfterString);
        long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
        return noOfDaysBetween;
    }

    /**
     * This method is used to get the count of days for the given month of given
     * year.
     *
     * @param strYear
     * @param strMonthName
     * @return
     */
    public static int daysCountForMonth(String strYear, String strMonthName) {
        int intYear = Integer.parseInt(strYear); // To hold the year
        int intNumDays = 0;
        if (strMonthName.contains("Feb")) {
            if ((intYear % 4 == 0) && (intYear % 400 == 0) && !(intYear % 100 == 0)) {
                intNumDays = 29;
            } else {
                intNumDays = 28;
            }
        } else if ((strMonthName.contains("Jan")) || (strMonthName.contains("Mar")) || (strMonthName.contains("May"))
                || (strMonthName.contains("Jul")) || (strMonthName.contains("Aug")) || (strMonthName.contains("Oct"))
                || (strMonthName.contains("Dec"))) {
            intNumDays = 31;
        } else {
            intNumDays = 30;
        }
        return intNumDays;
    }

    /**
     * This method is used to get the month value corresponding to the given
     * month name.
     *
     * @param strMonthName
     * @return
     */
    public static int getMonthValue(String strMonthName) {
        int intMonthValue = 0;
        if (strMonthName.contains("January")) {
            intMonthValue = 1;
        } else if (strMonthName.contains("February")) {
            intMonthValue = 2;
        } else if (strMonthName.contains("March")) {
            intMonthValue = 3;
        } else if (strMonthName.contains("April")) {
            intMonthValue = 4;
        } else if (strMonthName.contains("May")) {
            intMonthValue = 5;
        } else if (strMonthName.contains("June")) {
            intMonthValue = 6;
        } else if (strMonthName.contains("July")) {
            intMonthValue = 7;
        } else if (strMonthName.contains("August")) {
            intMonthValue = 8;
        } else if (strMonthName.contains("September")) {
            intMonthValue = 9;
        } else if (strMonthName.contains("October")) {
            intMonthValue = 10;
        } else if (strMonthName.contains("November")) {
            intMonthValue = 11;
        } else if (strMonthName.contains("December")) {
            intMonthValue = 12;
        }
        return intMonthValue;
    }

    /**
     * This method is used to get the month value of corresponding given month
     * value.
     *
     * @param monthValue
     * @return
     */
    public static String getMonthName(int monthValue) {
        String monthName = null;
        if (monthValue == 1) {
            monthName = "Jan";
        } else if (monthValue == 2) {
            monthName = "Feb";
        } else if (monthValue == 3) {
            monthName = "Mar";
        } else if (monthValue == 4) {
            monthName = "Apr";
        } else if (monthValue == 5) {
            monthName = "May";
        } else if (monthValue == 6) {
            monthName = "Jun";
        } else if (monthValue == 7) {
            monthName = "Jul";
        } else if (monthValue == 8) {
            monthName = "Aug";
        } else if (monthValue == 9) {
            monthName = "Sep";
        } else if (monthValue == 10) {
            monthName = "Oct";
        } else if (monthValue == 11) {
            monthName = "Nov";
        } else if (monthValue == 12) {
            monthName = "Dec";
        }
        return monthName;
    }

    /**
     * This method is used to get the month value of corresponding given month
     * value.
     *
     * @param monthValue
     * @return
     */
    public static String getCompleteMonthName(int monthValue) {
        String monthName = null;
        if (monthValue == 1) {
            monthName = "January";
        } else if (monthValue == 2) {
            monthName = "February";
        } else if (monthValue == 3) {
            monthName = "March";
        } else if (monthValue == 4) {
            monthName = "April";
        } else if (monthValue == 5) {
            monthName = "May";
        } else if (monthValue == 6) {
            monthName = "June";
        } else if (monthValue == 7) {
            monthName = "JULY";
        } else if (monthValue == 8) {
            monthName = "August";
        } else if (monthValue == 9) {
            monthName = "September";
        } else if (monthValue == 10) {
            monthName = "October";
        } else if (monthValue == 11) {
            monthName = "November";
        } else if (monthValue == 12) {
            monthName = "December";
        }
        return monthName;
    }

    /**
     * This method returns current month in the MMM format.
     *
     * @return string date in MMM format
     */
    public static String getCurrentMonth() {
        String month = null;
        String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Calendar cal = Calendar.getInstance();
        month = monthName[cal.get(Calendar.MONTH)];
        return month;
    }

    /**
     * This method returns current month in the MMMM format.
     *
     * @return string date in MMMM format
     */
    public static String getCurrentCompleteMonth() {
        String month = null;
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};
        Calendar cal = Calendar.getInstance();
        month = monthName[cal.get(Calendar.MONTH)];
        return month;
    }

    /**
     * This method is used to check if the date is in the valid format as per the locale.
     *
     * @param format format to check.
     * @param value  date to verify.
     * @param locale
     * @return status of valid date.
     */
    public static boolean isValidDateFormat(String format, String value, Locale locale) {
        LocalDateTime ldt = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, locale);
        try {
            ldt = LocalDateTime.parse(value, formatter);
            String result = ldt.format(formatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            try {
                LocalDate ld = LocalDate.parse(value, formatter);
                String result = ld.format(formatter);
                return result.equals(value);
            } catch (DateTimeParseException exp) {
                try {
                    LocalTime lt = LocalTime.parse(value, formatter);
                    String result = lt.format(formatter);
                    return result.equals(value);
                } catch (DateTimeParseException e2) {
                    LOGGER.info(e2);
                }
            }
        }
        return false;
    }

    /**
     * This method is used to get the date before number of days.
     *
     * @param days
     * @param format
     * @return
     */
    public static String getBeforeDateFromCurrentDate(int days, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        String daysAgo = dateFormat.format(new DateTime(date).minusDays(days).toDate());
        return daysAgo;
    }

    /**
     * This method is to change given date with format into the target format.
     *
     * @param givenDate
     * @param givenDateFormat
     * @param resultFormat
     * @return
     */
    public static String convertIntoSpecificDateFormat(String givenDate, String givenDateFormat, String resultFormat) {
        String result = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(givenDateFormat, Locale.US); // PST`
            Date startDTE = null;
            startDTE = formatter.parse(givenDate);
            SimpleDateFormat requiredFormat = new SimpleDateFormat(resultFormat, Locale.US); // PST`
            result = requiredFormat.format(startDTE);
        } catch (Exception e) {
            LOGGER.info(e);
            result = givenDate;
        }
        return result;
    }

    public static Date getDateInSpecificFormat(String givenDate, String givenFormat) {
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(givenFormat, Locale.US); // PST`
            date = formatter.parse(givenDate);
        } catch (Exception e) {
            LOGGER.info(e);
        }
        return date;
    }

    /**
     * This method returns current date in the given format.
     *
     * @param format
     * @return
     */
    public static String getCurrentDateInFormat(String format) {
        String s = null;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat(format);
        s = dateFormat.format(date);
        return s;
    }

    /**
     * This method gives number of days in the current month.
     *
     * @return
     */
    public static int getNoOfDaysInCurrentMonth() {
        Calendar c = Calendar.getInstance();
        int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        return monthMaxDays;
    }

    /**
     * This method gives last date of the last month.
     *
     * @param format
     * @return
     */
    public static String getLastDayPreviousMonth(String format) {
        String date = "";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);
        Date date1 = calendar.getTime();
        date = formatter.format(date1);
        LOGGER.info(date);
        return date;
    }

    /**
     * This method gives last date of the current month.
     *
     * @param format
     * @return
     */
    public static String getLastDayCurrentMonth(String format) {
        String date = "";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);
        Date date1 = calendar.getTime();
        date = formatter.format(date1);
        LOGGER.info(date);
        return date;
    }

    /**
     * This method provides 12 months last date.
     *
     * @param format
     * @return
     */
    public static String get12MonthsBackStartingDate(String format) {
        String date = "";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);
        int max = calendar.getActualMinimum(1);
        calendar.set(Calendar.DAY_OF_MONTH, max);
        Date date1 = calendar.getTime();
        date = formatter.format(date1);
        LOGGER.info(date);
        return date;
    }

    /**
     * This method provides back by given days.
     *
     * @param format
     * @param days
     * @return
     */
    public static String getBackDateByGivenDays(String format, int days) {
        String date = "";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        Date date1 = calendar.getTime();
        date = formatter.format(date1);
        LOGGER.info(date);
        return date;
    }

    /**
     * This method compare two dates.
     *
     * @param date1
     * @param date2
     * @param format format of the given dates.
     * @return an integer value
     */
    public static int compareTwoDates(String date1, String date2, String format) {
        SimpleDateFormat sdFormat = new SimpleDateFormat(format);
        Date d1 = null, d2 = null;
        try {
            d1 = sdFormat.parse(date1);
            d2 = sdFormat.parse(date2);
        } catch (ParseException e) {
            LOGGER.info(e);
        }
        return d1.compareTo(d2);
    }

    /**
     * This method provide all the timezone with offset.
     */
    public static void getAllTimeZoneWithOffset() {
        Set<String> allZones = ZoneId.getAvailableZoneIds();
        LocalDateTime dt = LocalDateTime.now();
        // Create a List using the set of zones and sort it.
        List<String> zoneList = new ArrayList<>(allZones);
        Collections.sort(zoneList);
        for (String s : zoneList) {
            ZoneId zone = ZoneId.of(s);
            ZonedDateTime zdt = dt.atZone(zone);
            ZoneOffset offset = zdt.getOffset();
            int secondsOfHour = offset.getTotalSeconds() % (60 * 60);
            String out = String.format("%35s %10s%n", zone, offset);
            // Write only time zones that do not have a whole hour offset
            // to standard out.
            if (secondsOfHour != 0) {
                System.out.printf(out);
            }
        }
    }
}
