package com.example.routing.util;

import com.example.routing.model.enums.TimePeriod;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtil {
  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm";

  public static DateTime convertStringToDateTime(String dateTime) {
    try {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
      return new DateTime(simpleDateFormat.parse(dateTime));
    } catch (ParseException exception) {
      throw new ConversionFailedException(
          TypeDescriptor.valueOf(String.class),
          TypeDescriptor.valueOf(DateTime.class),
          dateTime,
          exception
      );
    }
  }

  public static TimePeriod convertDateTimeToTimePeriod(DateTime current) {
    if (current == null) {
      return TimePeriod.OTHER;
    }

    // check for PEAK
    DateTime sixAm = current.withTime(new LocalTime(6, 0));
    DateTime nineAm = current.withTime(new LocalTime(9, 0));
    DateTime sixPm = current.withTime(new LocalTime(18, 0));
    DateTime ninePm = current.withTime(new LocalTime(21, 0));
    if (current.isAfter(sixAm) && current.isBefore(nineAm)
        || current.isAfter(sixPm) && current.isBefore(ninePm)
        || current.equals(sixAm)
        || current.equals(sixPm)
        || current.equals(nineAm)
        || current.equals(ninePm)) {
      int dayOfWeek = current.getDayOfWeek();
      if (dayOfWeek > 0 && dayOfWeek < 6) {
        return TimePeriod.PEAK;
      }
    }

    // check for NIGHT
    DateTime tenPm = current.withTime(new LocalTime(22, 0));
    if (current.isAfter(tenPm) || current.isBefore(sixAm)
        || current.equals(tenPm)
        || current.equals(sixAm)) {
      return TimePeriod.NIGHT;
    }

    return TimePeriod.OTHER;
  }
}
