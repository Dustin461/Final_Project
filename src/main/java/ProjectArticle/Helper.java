package ProjectArticle;

import javafx.application.Application;
import javafx.stage.Stage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Helper extends Application {
    private static final Helper myInstance = new Helper();

    public static Helper getInstance() {
        return myInstance;
    }

    public static String timeDiff(String date) {
        StringBuilder res = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date startDate = sdf.parse(unixToTime(date));
            Date endDate = new Date();
            long difference_In_Time = endDate.getTime() - startDate.getTime();

            long difference_In_Seconds = (difference_In_Time / 1000) % 60;
            long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
            long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
            long difference_In_Years = (difference_In_Time / (1000L * 60 * 60 * 24 * 365));
            long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
            long difference_In_Months = (difference_In_Time / (1000L * 60 * 60 * 24 * 30));

            if (difference_In_Years != 0) {
                if (difference_In_Years != 1) {
                    res.append(difference_In_Years).append(" years ago");
                } else {
                    res.append(difference_In_Years).append(" year ago");
                }
            } else if (difference_In_Months != 0) {
                if (difference_In_Months != 1) {
                    res.append(difference_In_Months).append(" months ago");
                } else {
                    res.append(difference_In_Months).append(" month ago");
                }
            } else if (difference_In_Days != 0) {
                if (difference_In_Days != 1) {
                    res.append(difference_In_Days).append(" days ago");
                } else {
                    res.append(difference_In_Days).append(" day ago");
                }
            } else if (difference_In_Hours != 0) {
                if (difference_In_Hours != 1) {
                    res.append(difference_In_Hours).append(" hours ago");
                } else {
                    res.append(difference_In_Hours).append(" hour ago");
                }
            } else if (difference_In_Minutes != 0) {
                if (difference_In_Minutes != 1) {
                    res.append(difference_In_Minutes).append(" minutes ago");
                } else {
                    res.append(difference_In_Minutes).append(" minute ago");
                }
            } else if (difference_In_Seconds != 0) {
                if (difference_In_Seconds != 1) {
                    res.append(difference_In_Seconds).append(" seconds ago");
                } else {
                    res.append(difference_In_Seconds).append(" second ago");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    public static String unixToTime(String unixTime) {
        ZonedDateTime dateTime = Instant.ofEpochMilli(Long.parseLong(unixTime)*1000).atZone(ZoneId.of("GMT+7"));
        String formatted = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        return formatted;
    }

    public static String timeToUnixString3(String time){
        long unixTime = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = sdf.parse(time);
            unixTime = date.getTime() / 1000;

        } catch (ParseException e){
            e.printStackTrace();
        }
        return String.valueOf(unixTime);
    }

    // This function will convert from readable date (E, dd MMM yyyy HH:mm:ss Z) to unix time String (Sun, 15 Aug 2021 19:00:00 +0700)
    public static String timeToUnixString2(String time){
        long unixTime = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
            Date date = sdf.parse(time);
            unixTime = date.getTime() / 1000;

        } catch (ParseException e){
            e.printStackTrace();
        }
        return String.valueOf(unixTime);
    }

    // // This function will convert from readable date (HH:mm dd/MM/yyyy) to unix time String (20:02 15/8/2021)
    public static String timeToUnixString5(String time){
        long unixTime = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            Date date = sdf.parse(time);
            unixTime = date.getTime() / 1000;

        } catch (ParseException e){
            e.printStackTrace();
        }
        return String.valueOf(unixTime);
    }

    public static String timeToUnixString3Reverse(String time){
        long unixTime = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            Date date = sdf.parse(time);
            unixTime = date.getTime() / 1000;

        } catch (ParseException e){
            e.printStackTrace();
        }
        return String.valueOf(unixTime);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
