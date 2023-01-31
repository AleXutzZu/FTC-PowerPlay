package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.util.constants.DriveConstants;

@Config
@TeleOp(name = "Calibrate Elevator PID", group = "Debugging")
public class CalibrateElevatorPID extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();
    public static double kP = 10, kI = 5, kD = 0;
    public static double offset = 20;
    public static double start = 20;
    private final RobotHardware robotHardware = new RobotHardware(this);

    @Override
    public void runOpMode() throws InterruptedException {
        robotHardware.initDrivetrainMotors();


        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        boolean targetState = true;

        waitForStart();
        runtime.reset();

        int targetPosition = (int) DriveConstants.elevatorTicksPerCm(start);
        while (opModeIsActive()) {

            if (runtime.milliseconds() > 5000) {
                if (targetState) {
                    targetPosition = (int) DriveConstants.elevatorTicksPerCm(start);
                    targetState = false;
                } else {
                    targetPosition = (int) DriveConstants.elevatorTicksPerCm(start + offset);
                    targetState = true;
                }
                runtime.reset();
            }

            robotHardware.getElevatorController().setTarget((targetPosition));
            telemetry.addData("targetPosition", targetPosition);
            telemetry.addData("actualPosition", robotHardware.getElevatorController().getCurrentPosition());
            robotHardware.getElevatorController().getPidController().setPID(kP, kI, kD);

            PIDController pidController = robotHardware.getElevatorController().getPidController();
            PIDFCoefficients coefficients = new PIDFCoefficients(pidController.getP(), pidController.getI(), pidController.getD(), pidController.getF());

            robotHardware.getLeftElevatorMotor().setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coefficients);
            robotHardware.getRightElevatorMotor().setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coefficients);

            robotHardware.getElevatorController().update();
            telemetry.update();
        }
    }
}
