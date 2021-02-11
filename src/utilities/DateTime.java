/**
 * @author Tyler Brown
 */
package utilities;


import DAO.AppDAO;
import javafx.scene.control.Alert;
import model.Appointment;
import java.time.*;
import java.time.temporal.ChronoUnit;
import static main.Main.userZone;

/**
 * This class is used for manipulating and time objects.
 */
public class DateTime {

    public static String formatDT(String input) {
        String newInput = input;

        newInput = newInput.replace('_', 'T');
        newInput = newInput + ":00";

        return newInput;
    }

    /**
     * This method takes a UTC stored Date/Time object and converts it to the users time zone.
     * @return
     */
    public static LocalDateTime convertToZone(LocalDateTime localDT) {

        ZonedDateTime zoneUTC_DT = localDT.atZone(ZoneId.of("UTC"));
        ZonedDateTime zoneUserDT = zoneUTC_DT.withZoneSameInstant(userZone);
        LocalDateTime userDT = zoneUserDT.toLocalDateTime();

        return userDT;
    }

    /**
     * This method takes a locally stored Date/Time object and converts it to UTC.
     */
    public static LocalDateTime convertToUTC(LocalDateTime localDT) {

        ZonedDateTime zoneUserDT = localDT.atZone(userZone);
        ZonedDateTime zoneUTC_DT = zoneUserDT.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime dbUTC = zoneUTC_DT.toLocalDateTime();

        return dbUTC;
    }

    /**
     * This method takes a locally stored Date/Time object and converts it to Eastern (EST) to check against office hours.
     */
    public static LocalDateTime convertToEastern(LocalDateTime localDT) {

        ZonedDateTime zoneUserDT = localDT.atZone(userZone);
        ZonedDateTime zoneEST_DT = zoneUserDT.withZoneSameInstant(ZoneId.of("EST5EDT"));
        LocalDateTime localEST = zoneEST_DT.toLocalDateTime();

        return localEST;
    }

    /**
     * This method compares an Appointments times with the office hours to ensure the Appointment can be scheduled.
     */
    public static int checkOfficeHours(LocalDateTime reqStartDT, LocalDateTime reqEndDT) {

        int scheduleApp = 0;
        boolean monToFri = true;

        LocalTime officeOpens = LocalTime.of(8, 00);
        LocalTime officeCloses = LocalTime.of(22, 00);

        LocalDateTime reqLocalStartDT = convertToEastern(reqStartDT);
        LocalDateTime reqLocalEndDT = convertToEastern(reqEndDT);


        if (reqLocalStartDT.getDayOfWeek() == DayOfWeek.SATURDAY || reqLocalStartDT.getDayOfWeek() == DayOfWeek.SUNDAY
                || reqLocalEndDT.getDayOfWeek() == DayOfWeek.SATURDAY || reqLocalEndDT.getDayOfWeek() == DayOfWeek.SUNDAY) {
            scheduleApp = 1;
            monToFri = false;
        }
        if (reqLocalStartDT.toLocalTime().isBefore(officeOpens) || reqLocalEndDT.toLocalTime().isAfter(officeCloses)) {
            scheduleApp = 2;
        }
        if (scheduleApp == 2 && monToFri == false) {
            scheduleApp = 3;
        }
        return scheduleApp;
    }

    /**
     * This method checks a new or updated Appointments times to make sure they do not overlap with another Appointment.
     */
    public static boolean conflictingTime(LocalDateTime desiredStartDT, LocalDateTime desiredEndDT) {
        boolean noOverlap = true;

        for (int i = 0; i < AppDAO.getAllApps().size(); i++) {
            if(((desiredStartDT.isAfter(AppDAO.getAllApps().get(i).getAppUTCStart()) || desiredStartDT.isEqual(AppDAO.getAllApps().get(i).getAppUTCStart()))
                    && (desiredEndDT.isBefore(AppDAO.getAllApps().get(i).getAppUTCEnd()) || desiredEndDT.isEqual(AppDAO.getAllApps().get(i).getAppUTCEnd())))
                    || (AppDAO.getAllApps().get(i).getAppUTCStart().isAfter(desiredStartDT) || AppDAO.getAllApps().get(i).getAppUTCStart().isEqual(desiredStartDT)
                    && ((AppDAO.getAllApps().get(i).getAppUTCEnd().isBefore(desiredEndDT)) || AppDAO.getAllApps().get(i).getAppUTCEnd().isEqual(desiredEndDT)))) {
                noOverlap = false;

                Alert appOverlap = new Alert(Alert.AlertType.ERROR);
                appOverlap.setTitle("Conflicting Dates/Times");
                appOverlap.setContentText("Both the Start and End date/times entered overlap with Appointment ID: " + AppDAO.getAllApps().get(i).getAppID()
                        + " Appointment Start: " + AppDAO.getAllApps().get(i).getAppUserStart() + " Appointment End: "
                        + AppDAO.getAllApps().get(i).getAppUserEnd() + ". Please correct the error(s).");
                appOverlap.showAndWait();
            }
            else if ((desiredStartDT.isAfter(AppDAO.getAllApps().get(i).getAppUTCStart()) || desiredStartDT.isEqual(AppDAO.getAllApps().get(i).getAppUTCStart()))
                    && (desiredStartDT.isBefore(AppDAO.getAllApps().get(i).getAppUTCEnd()) || desiredStartDT.isEqual(AppDAO.getAllApps().get(i).getAppUTCEnd()))
                    && desiredEndDT.isAfter(AppDAO.getAllApps().get(i).getAppUTCEnd())) {
                noOverlap = false;

                Alert appOverlap = new Alert(Alert.AlertType.ERROR);
                appOverlap.setTitle("Conflicting Dates/Times");
                appOverlap.setContentText("The Start date/time entered overlaps with Appointment ID: " + AppDAO.getAllApps().get(i).getAppID()
                        + " Appointment Start: " + AppDAO.getAllApps().get(i).getAppUserStart() + " Appointment End: "
                        + AppDAO.getAllApps().get(i).getAppUserEnd() + ". Please correct the error(s).");
                appOverlap.showAndWait();
            }
            else if (desiredStartDT.isBefore(AppDAO.getAllApps().get(i).getAppUTCStart()) && (desiredEndDT.isAfter(AppDAO.getAllApps().get(i).getAppUTCStart())
                    || desiredEndDT.isEqual(AppDAO.getAllApps().get(i).getAppUTCStart())) && (desiredEndDT.isBefore(AppDAO.getAllApps().get(i).getAppUTCEnd())
                    || desiredEndDT.isEqual(AppDAO.getAllApps().get(i).getAppUTCEnd()))) {
                noOverlap = false;

                Alert appOverlap = new Alert(Alert.AlertType.ERROR);
                appOverlap.setTitle("Conflicting Dates/Times");
                appOverlap.setContentText("The End date/time entered overlaps with Appointment ID: " + AppDAO.getAllApps().get(i).getAppID()
                        + " Appointment Start: " + AppDAO.getAllApps().get(i).getAppUserStart() + " Appointment End: "
                        + AppDAO.getAllApps().get(i).getAppUserEnd() + ". Please correct the error(s).");
                appOverlap.showAndWait();
            }
        }
        return noOverlap;
    }

    /**
     * This method is used to filter all Appointments on the Main Menu to determine if they are within a week of the current time.
     */
    public static boolean withinWeek(Appointment appToSort) {
        boolean isWithinWeek = false;

        LocalDateTime currentDT = convertToUTC(LocalDateTime.now());
        LocalDateTime weekFromNow = convertToUTC(LocalDateTime.now().plusDays(7));
        LocalDateTime appStartDT = appToSort.getAppUTCStart();

        if(appStartDT.isAfter(currentDT) && appStartDT.isBefore(weekFromNow)) {
            isWithinWeek = true;
        }
        return isWithinWeek;
    }

    /**
     * This method is used to filter all Appointments on the Main Menu to determine if they are within a month of the current time.
     */
    public static boolean withinMonth(Appointment appToSort) {
        boolean isWithinMonth = false;

        LocalDateTime currentDT = convertToUTC(LocalDateTime.now());
        LocalDateTime monthFromNow = convertToUTC(LocalDateTime.now().plusMonths(1));
        LocalDateTime appStartDT = appToSort.getAppUTCStart();

        if(appStartDT.isAfter(currentDT) && appStartDT.isBefore(monthFromNow)) {
            isWithinMonth = true;
        }
        return isWithinMonth;
    }

    /**
     * This method is used to check all scheduled Appointments to determine if there is one within 15 minutes of logging in.
     */
    public static Appointment within15() {
        LocalDateTime localDT = convertToUTC(LocalDateTime.now());

        for (int i = 0; i < AppDAO.getAllApps().size(); i++) {
            long timeDiff = ChronoUnit.MINUTES.between(AppDAO.getAllApps().get(i).getAppUTCStart().toLocalTime(), localDT.plusMinutes(15));
            if (timeDiff <= 15 && timeDiff >= 0) {
                return AppDAO.getAllApps().get(i);
            }
            else {
                continue;
            }
        }
        return null;
    }
}
