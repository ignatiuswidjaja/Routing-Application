package com.example.routing.util;

import com.example.routing.model.enums.TimePeriod;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.core.convert.ConversionFailedException;

@RunWith(JUnit4.class)
public class TestDateUtil {
  @Test
  public void testConvertStringToDateTime_isSuccessful() {
    DateTime dateTime = new DateTime(2020, 10, 18, 12, 12);
    DateTime convertedDateTime = DateTimeUtil.convertStringToDateTime(dateTime.toString());
    Assert.assertEquals(dateTime.getMillis(), convertedDateTime.getMillis());
  }

  @Test(expected = ConversionFailedException.class)
  public void testConvertStringToDateTime_throwError() {
    DateTimeUtil.convertStringToDateTime("20A20-1A0-1A8T12:12");
  }

  @Test
  public void testConvertDateTimeToTimePeriod() {
    Assert.assertEquals(TimePeriod.OTHER, DateTimeUtil.convertDateTimeToTimePeriod(null));

    // Friday, 16 Oct 2020, 9:00 AM
    DateTime dateTime = new DateTime(2020, 10, 16, 9, 0);
    Assert.assertEquals(TimePeriod.PEAK, DateTimeUtil.convertDateTimeToTimePeriod(dateTime));

    // Friday, 16 Oct 2020, 10:00 AM
    dateTime = new DateTime(2020, 10, 16, 10, 0);
    Assert.assertEquals(TimePeriod.OTHER, DateTimeUtil.convertDateTimeToTimePeriod(dateTime));

    // Friday, 16 Oct 2020, 6:00 PM
    dateTime = new DateTime(2020, 10, 16, 18, 0);
    Assert.assertEquals(TimePeriod.PEAK, DateTimeUtil.convertDateTimeToTimePeriod(dateTime));

    // Friday, 16 Oct 2020, 10:00 PM
    dateTime = new DateTime(2020, 10, 16, 22, 0);
    Assert.assertEquals(TimePeriod.NIGHT, DateTimeUtil.convertDateTimeToTimePeriod(dateTime));

    // Saturday, 17 Oct 2020, 9:30 AM
    dateTime = new DateTime(2020, 10, 17, 9, 30);
    Assert.assertEquals(TimePeriod.OTHER, DateTimeUtil.convertDateTimeToTimePeriod(dateTime));

    // Saturday, 17 Oct 2020, 11:00 PM
    dateTime = new DateTime(2020, 10, 17, 23, 0);
    Assert.assertEquals(TimePeriod.NIGHT, DateTimeUtil.convertDateTimeToTimePeriod(dateTime));

    // Sunday, 18 Oct 2020, 02:00 AM
    dateTime = new DateTime(2020, 10, 18, 2, 0);
    Assert.assertEquals(TimePeriod.NIGHT, DateTimeUtil.convertDateTimeToTimePeriod(dateTime));

    // Sunday, 18 Oct 2020, 06:30 AM
    dateTime = new DateTime(2020, 10, 18, 6, 30);
    Assert.assertEquals(TimePeriod.OTHER, DateTimeUtil.convertDateTimeToTimePeriod(dateTime));
  }
}
