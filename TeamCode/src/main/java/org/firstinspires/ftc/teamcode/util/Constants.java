package org.firstinspires.ftc.teamcode.util;

public final class Constants {

    /**
     * Winch pulley diameter in centimeters.
     */
    public static final double WINCH_PULLEY_CIRCUMFERENCE = 11.2;
    /**
     * GOBILDA 5203 Series Yellow Jacket Planetary Gear Motor PPR
     */
    public static final double GOBILDA_5203_TICKS_PER_REVOLUTION = 384.5;
    /**
     * GOBILDA 5203 Series Yellow Jacket Planetary Gear Motor Ticks/cm
     */
    public static final double GOBILDA_5203_TICKS_PER_CM = GOBILDA_5203_TICKS_PER_REVOLUTION / WINCH_PULLEY_CIRCUMFERENCE;

    /**
     * The maximum height that the elevator shall reach by setting power manually.
     */
    public static final int GOBILDA_5203_MAX_HEIGHT_TICKS = (int) (GOBILDA_5203_TICKS_PER_CM * 90);

    /**
     * The minimum height that the elevator shall reach by setting power manually.
     */
    public static final int GOBILDA_5203_MIN_HEIGHT_TICKS = 0;
}
