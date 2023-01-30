package org.firstinspires.ftc.teamcode.control.limbs;

import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import lombok.Getter;
import org.firstinspires.ftc.teamcode.control.Elevator;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.util.Constants;

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

        PIDFCoefficients coefficients = new PIDFCoefficients(pidController.getP(), pidController.getI(), pidController.getD(), pidController.getF());

        robotHardware.getLeftElevatorMotor().setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coefficients);
        robotHardware.getRightElevatorMotor().setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coefficients);
    }

    @Override
    public void home() {

    }

    @Override
    public void update() {
        //transform velocity to ratio
        double calculatedPower = pidController.calculate(robotHardware.getLeftElevatorMotor().getCurrentPosition(), target) / Constants.GOBILDA_5203_MAX_TICKS_PER_SECOND;

        robotHardware.getRightElevatorMotor().setPower(calculatedPower);
        robotHardware.getLeftElevatorMotor().setPower(calculatedPower);
    }

    @Override
    public void setTarget(int target) {
        this.target = target;
    }

    @Override
    public void setTarget(ElevatorLevel level) {
        this.target = (int) (level.getHeight() * Constants.GOBILDA_5203_TICKS_PER_CM);
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
