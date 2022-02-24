package vn.cmc.du21.orderservice.common;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeUtil {
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final String DATETIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
    private static final String DATETIME_PATTERN2 = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.getDefault());
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN, Locale.getDefault());
    private static final DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern(DATETIME_PATTERN2, Locale.getDefault());

    private DateTimeUtil() {
        super();
    }

    public static Date stringToDateSql(String dateString) {
        LocalDate localDate = LocalDate.parse(dateString, dateFormatter);
        return Date.valueOf(localDate);
    }

    public static LocalDateTime stringToLocalDateTime(String dateString) {
        return LocalDateTime.parse(dateString, dateTimeFormatter);
    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatter);
    }

    public static String dateSqlToString(Date date) {
        LocalDate localDate = date.toLocalDate();
        return localDate.format(dateFormatter);
    }

    public static Date localDateToSqlDate(LocalDate localDate) {
        return Date.valueOf(localDate);
    }

    public static LocalDate sqlDateToLocalDate(Date sqlDate) {
        return sqlDate.toLocalDate();
    }

    public static Timestamp localDateTimeToSqlTimestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    public static LocalDateTime sqlTimestampToLocalDateTime(Timestamp timestamp)
    {
        return timestamp.toLocalDateTime();
    }

    public static String timestampToString(Timestamp expireTime) {
        return String.format(expireTime.toString(), dateTimeFormatter);
    }

    public static Timestamp stringToTimeStamp(String date) {
        return Timestamp.valueOf(String.format(date, dateTimeFormatter2));
    }

    public static Timestamp getTimeNow(){
        return Timestamp.from(Instant.now());
    }
}
