package org.firstinspires.ftc.teamcode.control;

/**
 * Every implementor has to be able to operate the limbs of the robot with these 2 methods.
 */
public interface Limbs {

    /**
     * Elevator levels for the robot
     */
    enum ElevatorLevel {
        /**
         * The lowest level of the elevator (at the base of the robot)
         */
        BASE,
        /**
         * The low level of the elevator (slightly above the shortest bar)
         */
        LOW,
        /**
         * The middle level of the elevator (slightly above the medium bar)
         */
        MIDDLE,
        /**
         * The highest level of the elevator (slightly above the tallest bar)
         */
        HIGH
    }

    /**
     * Invokes the claws to close or open
     *
     * @return true if the claws opened or false if the claws closed
     */
    boolean useClaws();

    /**
     * Moves the elevator to the specified level
     *
     * @param level the level to move the elevator to
     */
    void useElevator(ElevatorLevel level);

    /**
     * Moves the robot by power. Before calling this method {@link #isSafeToMove()} should be called to ensure that the robot is not at one of the limits.
     *
     * @param power the power to move the robot. Positive power will drive the motor forward (i.e. raise the elevator), whilst negative power will drive the motor backwards (i.e. lower the elevator)
     */
    void useElevator(double power);

    /**
     * Checks if the robot is within the allowed range to be manually moved using power.
     *
     * @return true if the robot is safe to move, false otherwise
     */
    boolean isSafeToMove();

    /**
     * Homes the elevator at the base of the robot.
     */
    void home();
}
