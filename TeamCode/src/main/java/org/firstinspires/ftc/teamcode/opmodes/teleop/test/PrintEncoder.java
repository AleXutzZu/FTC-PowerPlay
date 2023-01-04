package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

@TeleOp(name = "Print Encoder Outputs", group = "Debugging")
public class PrintEncoder extends OpMode {
    private RobotHardware robotHardware;

    @Override
    public void init() {
        robotHardware = new RobotHardware(this);
        robotHardware.initTeleOp();
    }

    @Override
    public void loop() {
        double power = 0.05D;

        if (gamepad1.x) robotHardware.getLeftBackMotor().setPower(power);

        if (gamepad1.b) robotHardware.getLeftFrontMotor().setPower(power);

        if (gamepad1.y) robotHardware.getRightFrontMotor().setPower(power);

        if (gamepad1.a) robotHardware.getRightBackMotor().setPower(power);

        if (gamepad1.dpad_left) robotHardware.getLeftElevatorMotor().setPower(power);

        if (gamepad1.dpad_right) robotHardware.getRightElevatorMotor().setPower(power);
    }

    public void stop() {
        telemetry.addData("(X)Left back motor encoder output:", robotHardware.getLeftBackMotor().getCurrentPosition());
        telemetry.addData("(B)Left front motor encoder output:", robotHardware.getLeftFrontMotor().getCurrentPosition());
        telemetry.addData("(A)Right back motor encoder output:", robotHardware.getRightBackMotor().getCurrentPosition());
        telemetry.addData("(Y)Right front motor encoder output:", robotHardware.getRightFrontMotor().getCurrentPosition());
        telemetry.addData("(dpad_left)Left elevator motor encoder output:", robotHardware.getLeftElevatorMotor().getCurrentPosition());
        telemetry.addData("(dpad_right)Right elevator motor encoder output:", robotHardware.getRightElevatorMotor().getCurrentPosition());
    }

}