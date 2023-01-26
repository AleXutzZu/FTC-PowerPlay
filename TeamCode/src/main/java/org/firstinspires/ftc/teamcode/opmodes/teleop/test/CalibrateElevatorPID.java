package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.util.Constants;

@Config
@TeleOp(name = "Calibrate Elevator PID", group = "Debugging")
public class CalibrateElevatorPID extends LinearOpMode {

    public static double kP = 0, kI = 0, kD = 0;
    public static double position = 40;
    private final RobotHardware robotHardware = new RobotHardware(this);

    @Override
    public void runOpMode() throws InterruptedException {
        robotHardware.initDrivetrainMotors();


        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();


        robotHardware.getElevatorController().setTarget((int) (position * Constants.GOBILDA_5203_TICKS_PER_CM));
        while (opModeIsActive()) {

            telemetry.addData("targetPosition", position);
            telemetry.addData("actualPosition", robotHardware.getElevatorController().getCurrentPosition());

            robotHardware.getElevatorController().getPidController().setPID(kP, kI, kD);

            PIDController pidController = robotHardware.getElevatorController().getPidController();
            PIDFCoefficients coefficients = new PIDFCoefficients(pidController.getP(), pidController.getI(), pidController.getD(), pidController.getF());

            robotHardware.getLeftElevatorMotor().setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coefficients);

            robotHardware.getElevatorController().update();
        }
    }
}
