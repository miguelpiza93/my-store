package com.mapiz.mystore.integration;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
  // UNIT CONSTANTS
  public static final int CARTON_ID = 1;
  public static final int UNIT_ID = 2;

  // CONVERSION CONSTANTS
  public static final int UNITS_PER_CARTON = 30;

  // VENDOR CONSTANTS
  public static final int KIKES_ID = 1;

  // PRODUCT CONSTANTS
  public static final int EGG_ID = 1;
  public static final String EGG_NAME = "Huevos";
  public static final int MILK_ID = 2;
  public static final int SAUSAGE_ID = 3;
  public static final String SAUSAGE_NAME = "Salchicha";
  public static final int NEXT_AVAILABLE_ID = 4;

  // STOCK CONSTANTS
  public static final int UNITS_OF_MILK_IN_STOCK = 0;
  public static final int UNITS_OF_EGGS_IN_STOCK = 90;
  public static final int UNITS_OF_SAUSAGES_IN_STOCK = 5;

  // PURCHASE ORDER CONSTANTS
  public static final int PURCHASE_ORDER_ID_RECEIVED = 1;
  public static final int PURCHASE_ORDER_OF_EGGS_ID = 4;
  public static final int PURCHASE_ORDER_OF_MILK_ID = 5;
  public static final int EXPECTED_UNITS_OF_EGGS_ADDED = 120;
  public static final int EXPECTED_UNITS_OF_MILK_ADDED = 12;
}
