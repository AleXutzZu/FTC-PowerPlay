package org.firstinspires.ftc.teamcode.control;

/**
 * Every implementor has to be able to operate the drive train of the robot with this method.
 */
@FunctionalInterface
public interface Drivetrain {

    /**
     * Moves the robot in the specified direction
     * @param axial the axial direction of the robot
     * @param lateral the lateral direction of the robot
     * @param yaw the yaw direction of the robot
     */
    void drive(double axial, double lateral, double yaw);
}
