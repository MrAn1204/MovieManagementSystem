package com.mms.mms_api.common;

/**
 * Shared validation and domain constants used by the API.
 */
public class AppConstant {
    /**
     * Prevents instantiation of this constants holder.
     */
    private AppConstant() {}

    public static final int USERNAME_MIN = 5;

    public static final int USERNAME_MAX = 20;

    public static final int FULLNAME_MIN = 3;

    public static final int FULLNAME_MAX = 50;

    public static final int PASSWORD_MIN = 8;

    public static final int CITIZEN_ID_MIN = 12;

    public static final int PHONE_MIN = 10;

    public static final int PHONE_MAX = 15;

    public static final int ADDRESS_MIN = 5;

    public static final int ADDRESS_MAX = 100;

    public static final int ROW_MAX = 20;

    public static final int COLUMN_MAX = 20;

    public static final int BASE_SEAT_PRICE = 20000;
}
