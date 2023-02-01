package org.firstinspires.ftc.teamcode.control.limbs;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
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
        pidController = new PIDController(2.3, 1.4, 0.09);
        pidController.setTolerance(tolerance);
    }

    @Override
    public void home() {

    }

    @Override
    public void update() {
        //transform velocity to ratio
        double calculatedVelocity = pidController.calculate(robotHardware.getLeftElevatorMotor().getCurrentPosition(), target);

        robotHardware.getRightElevatorMotor().setVelocity(calculatedVelocity);
        robotHardware.getLeftElevatorMotor().setVelocity(calculatedVelocity);
    }

    @Override
    public void setTarget(int target) {
        this.target = target;
    }

    @Override
    public void setTarget(ElevatorLevel level) {
        this.target = (int) DriveConstants.elevatorTicksPerCm(level.getHeight());
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
