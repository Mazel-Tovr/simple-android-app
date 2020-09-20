package com.tstu.dembel_counter;


import android.icu.util.DateInterval;
import android.icu.util.LocaleData;
import android.net.ParseException;
import android.text.method.DateTimeKeyListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class Utils {

    private static final Pattern datePattern = Pattern.compile("[0-9]{2}-[0-9]{2}-[0-9]{4}");

    public static final String DASH_STRING = "-";

    public static void isValidDate(String srt) throws Exception {
        if (!datePattern.matcher(srt).find()) throw new Exception("Неверный формат даты");
        String[] parts = srt.split(DASH_STRING);
        if (Integer.parseInt(parts[0]) > 31) throw new Exception("Больше чем 31 день");
        if (Integer.parseInt(parts[1]) > 12) throw new Exception("Больше чем 12 месяцев");
        if (Integer.parseInt(parts[2]) > Calendar.getInstance().get(Calendar.YEAR))
            throw new Exception("Больше чем текущего года");
    }

    public static Date getDatePlusYear(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }


}
