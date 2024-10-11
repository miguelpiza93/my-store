package com.mapiz.mystore.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BigDecimalUtils {

  private static final int DEFAULT_SCALE = 1;
  private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

  public static BigDecimal setScale(BigDecimal value) {
    if (value == null) {
      return BigDecimal.ZERO;
    }
    return value.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
  }

  public static BigDecimal add(BigDecimal a, BigDecimal b) {
    return setScale(a).add(setScale(b));
  }

  public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
    return setScale(a).multiply(setScale(b)).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
  }

  public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
    return setScale(a).subtract(setScale(b));
  }

  public static BigDecimal divide(BigDecimal a, BigDecimal b) {
    return setScale(a).divide(setScale(b), DEFAULT_SCALE, DEFAULT_ROUNDING_MODE);
  }

  public static BigDecimal min(BigDecimal a, BigDecimal b) {
    return setScale(a).min(setScale(b));
  }

  public static BigDecimal valueOf(double value) {
    return setScale(BigDecimal.valueOf(value));
  }

  public static BigDecimal valueOf(String value) {
    return setScale(new BigDecimal(value));
  }

  public static int compare(BigDecimal a, BigDecimal b) {
    return setScale(a).compareTo(setScale(b));
  }
}
