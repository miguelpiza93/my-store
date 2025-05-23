package com.mapiz.mystore.integration;

import com.mapiz.mystore.util.BigDecimalUtils;
import java.math.BigDecimal;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
  // UNIT CONSTANTS
  public static final int CARTON_ID = 1;
  public static final String CARTON_NAME = "Panal";
  public static final int UNIT_ID = 2;
  public static final String UNIT_NAME = "Unidad";
  public static final String UNIT_SYMBOL = "und";
  public static final int HALF_CARTON_ID = 3;
  public static final String HALF_CARTON_NAME = "Medio Panal";
  public static final int NEXT_AVAILABLE_UNIT_ID = 8;

  // CONVERSION CONSTANTS
  public static final BigDecimal UNITS_PER_CARTON = BigDecimalUtils.valueOf(30);

  // VENDOR CONSTANTS
  public static final int KIKES_ID = 1;
  public static final String KIKES_NAME = "Kikes";
  public static final int ALQUERIA_ID = 2;
  public static final String ALQUERIA_NAME = "Alqueria";
  public static final int ZENU_ID = 3;
  public static final String ZENU_NAME = "Zenu";

  // PRODUCT CONSTANTS
  public static final int EGG_ID = 1;
  public static final String EGG_NAME = "Huevos";
  public static final String EGG_DESCRIPTION = "AA";
  public static final int MILK_ID = 2;
  public static final String MILK_NAME = "Leche";
  public static final String MILK_DESCRIPTION = "Entera";
  public static final int SAUSAGE_ID = 6;
  public static final String SAUSAGE_NAME = "Salchicha";
  public static final String SAUSAGE_DESCRIPTION = "x6";
  public static final int NEXT_AVAILABLE_PRODUCT_ID = 7;

  // PRODUCT VENDOR CONSTANTS
  public static final int KIKES_EGGS = 1;
  public static final int ALQUERIA_MILKS = 2;
  public static final int ZENU_SAUSAGES = 3;

  // STOCK CONSTANTS
  public static final BigDecimal UNITS_OF_MILK_IN_STOCK = BigDecimalUtils.valueOf(12);
  public static final BigDecimal UNITS_OF_EGGS_IN_STOCK = BigDecimalUtils.valueOf(237);
  public static final BigDecimal UNITS_OF_SAUSAGES_IN_STOCK = BigDecimalUtils.valueOf(5);

  // PURCHASE ORDER CONSTANTS
  public static final int PURCHASE_ORDER_ID_RECEIVED = 1;
  public static final int PURCHASE_ORDER_OF_EGGS_ID = 4;
  public static final int PURCHASE_ORDER_OF_MILK_ID = 5;
  public static final BigDecimal EXPECTED_UNITS_OF_EGGS_ADDED = BigDecimalUtils.valueOf(120);
  public static final BigDecimal EXPECTED_UNITS_OF_MILK_ADDED = BigDecimalUtils.valueOf(12);
}
