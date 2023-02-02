package org.firstinspires.ftc.teamcode.control.limbs;

import com.arcrobotics.ftclib.controller.PIDController;
import lombok.Getter;
import org.firstinspires.ftc.teamcode.control.Elevator;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.util.constants.DriveConstants;

public class ElevatorController implements Elevator {
    private final RobotHardware robotHardware;
    private int target = 0;
    @Getter
    private final PIDController pidController;

    public ElevatorController(RobotHardware robotHardware) {
        this(robotHardware, 150);
    }

    public ElevatorController(RobotHardware robotHardware, int tolerance) {
        this.robotHardware = robotHardware;
        pidController = new PIDController(8, 8.5, 1.3);
        pidController.setTolerance(tolerance);
    }

    @Override
    public void home() {

    }

    @Override
    public void update() {
        //transform velocity to ratio
        double calculatedPower = pidController.calculate(robotHardware.getLeftElevatorMotor().getCurrentPosition(), target) / DriveConstants.ELEVATOR_MAX_TICKS_PER_SECOND;

        robotHardware.getRightElevatorMotor().setPower(calculatedPower);
        robotHardware.getLeftElevatorMotor().setPower(calculatedPower);
    }

    @Override
    public void setTarget(int target) {
        this.target = target;
    }

    @Override
    public void setTarget(ElevatorLevel level) {
        this.target = (int) DriveConstants.elevatorCmToTicks(level.getHeight());
    }

    @Override
    public int getTarget() {
        return target;
    }

    @Override
    public int getCurrentPosition() {
        return robotHardware.getLeftElevatorMotor().getCurrentPosition();
    }
}
