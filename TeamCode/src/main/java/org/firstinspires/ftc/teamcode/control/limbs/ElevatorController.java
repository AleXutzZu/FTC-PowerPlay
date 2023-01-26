package org.firstinspires.ftc.teamcode.control.limbs;

import com.arcrobotics.ftclib.controller.PIDController;
import org.firstinspires.ftc.teamcode.control.Elevator;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.util.Constants;

public class ElevatorController implements Elevator {
    private final RobotHardware robotHardware;
    private int position = 0;
    private final PIDController pidController;

    public ElevatorController(RobotHardware robotHardware) {
        this.robotHardware = robotHardware;
        //TODO: Find these values
        pidController = new PIDController(0, 0, 0);
        pidController.setTolerance(150);
    }

    @Override
    public void home() {

    }

    @Override
    public void update() {
        //transform velocity to ratio
        double calculatedPower = pidController.calculate(robotHardware.getLeftElevatorMotor().getCurrentPosition(), position) / Constants.GOBILDA_5203_MAX_TICKS_PER_SECOND;

        robotHardware.getRightElevatorMotor().setPower(calculatedPower);
        robotHardware.getLeftElevatorMotor().setPower(calculatedPower);
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void setPosition(ElevatorLevel level) {
        this.position = (int) (level.getHeight() * Constants.GOBILDA_5203_TICKS_PER_CM);
    }

    @Override
    public int getPosition() {
        return position;
    }
}
