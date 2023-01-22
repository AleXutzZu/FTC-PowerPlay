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
}
