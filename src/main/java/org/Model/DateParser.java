package org.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    private static SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    protected static String formatDate(String inputDate) {
        try {
            Date date = inputDateFormat.parse(inputDate);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
