package com.example.routing.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtil {
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
}
