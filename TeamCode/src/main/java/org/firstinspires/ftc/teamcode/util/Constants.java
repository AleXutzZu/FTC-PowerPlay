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

    /**
     * Maximum velocity of the motor in theoretical conditions, expressed as ticks/second.
     */
    public static final double GOBILDA_5203_MAX_TICKS_PER_SECOND = 435 * GOBILDA_5203_TICKS_PER_REVOLUTION / 60;

    /**
     * Vuforia License Key
     */
    public static final String VUFORIA_KEY =
            "AXhQwhT/////AAABmajs48ny5UHptNvgXujuJ3spGMiMx1mw45nu5wOcYDzyfkKrQY09KXc2Nzohgc9DZFRqHyogXOSNz7DErpVP5l" +
                    "WUDe5oN6z9IequWU/mHIPbyWHwuPzqF7p8otEHk++z4lSz3/F5e/+Igpv46CP1wIgquFedrbLekYljYu5ccPn7QsWtfSNxs" +
                    "wS1DKek7Dilsc8C62LUqM6OJzeVzSVcUdzidY07bAeVpji6xpy9AbdSVhUa6N5lGlNhM20EGrLmN7eVzKjKYY4IRqF8eH5T" +
                    "aKHb/f+zUMBOGGsq98ACB9bx+XuMr0si67JpRn3y+M/iUhkkESZz3UBOwK8f4S0R0y4ypS4tGzXoETdv0EN2hB/G";

    /**
     * Default sleeve asset file
     */
    public static final String TFOD_MODEL_ASSET = "PowerPlay.tflite";

    /**
     * Our own sleeve model file path on the RC phone
     */
    public static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/signal sleeve v1.tflite";

    /**
     * Our own sleeve model labels
     */
    public static final String[] LABELS = {
            "1 ORANGE",
            "2 PICKLE",
            "3 DONUT"
    };
    /**
     * Default labels for the default sleeve
     */
    public static final String[] DEFAULT_LABELS = {
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };

}
