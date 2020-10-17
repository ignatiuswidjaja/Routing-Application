package com.example.routing.util;

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
    DateTime convertedDateTime = DateUtil.convertStringToDateTime(dateTime.toString());
    Assert.assertEquals(dateTime.getMillis(), convertedDateTime.getMillis());
  }

  @Test(expected = ConversionFailedException.class)
  public void testConvertStringToDateTime_throwError() {
    DateUtil.convertStringToDateTime("20A20-1A0-1A8T12:12");
  }
}
