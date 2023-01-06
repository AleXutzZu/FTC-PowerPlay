package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

@TeleOp(name = "Print Encoder Outputs", group = "Debugging")
public class PrintEncoder extends OpMode {
    private final RobotHardware robotHardware = new RobotHardware(this);

    @Override
    public void init() {
        robotHardware.initTeleOp();
    }

    @Override
    public void loop() {
        double power = 0.05D;

        if (gamepad1.x) robotHardware.getLeftBackMotor().setPower(power);
        else robotHardware.getLeftBackMotor().setPower(0);

        if (gamepad1.b) robotHardware.getLeftFrontMotor().setPower(power);
        else robotHardware.getLeftFrontMotor().setPower(0);

        if (gamepad1.y) robotHardware.getRightFrontMotor().setPower(power);
        else robotHardware.getRightFrontMotor().setPower(0);

        if (gamepad1.a) robotHardware.getRightBackMotor().setPower(power);
        else robotHardware.getRightBackMotor().setPower(0);

        if (gamepad1.dpad_left) robotHardware.getLeftElevatorMotor().setPower(power);
        else robotHardware.getLeftElevatorMotor().setPower(0);

        if (gamepad1.dpad_right) robotHardware.getRightElevatorMotor().setPower(power);
        else robotHardware.getRightElevatorMotor().setPower(0);

        telemetry.addData("(X)Left back motor encoder output:", robotHardware.getLeftBackMotor().getCurrentPosition());
        telemetry.addData("(B)Left front motor encoder output:", robotHardware.getLeftFrontMotor().getCurrentPosition());
        telemetry.addData("(A)Right back motor encoder output:", robotHardware.getRightBackMotor().getCurrentPosition());
        telemetry.addData("(Y)Right front motor encoder output:", robotHardware.getRightFrontMotor().getCurrentPosition());
        telemetry.addData("(dpad_left)Left elevator motor encoder output:", robotHardware.getLeftElevatorMotor().getCurrentPosition());
        telemetry.addData("(dpad_right)Right elevator motor encoder output:", robotHardware.getRightElevatorMotor().getCurrentPosition());
    }

    public void stop() {

    }

}