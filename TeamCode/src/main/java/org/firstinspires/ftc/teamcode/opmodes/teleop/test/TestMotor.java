package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Test Motor", group = "Debugging")
public class TestMotor extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "motor");
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        GamepadEx gamepad = new GamepadEx(gamepad1);
        ToggleButtonReader switchDirection = new ToggleButtonReader(gamepad, GamepadKeys.Button.X);
        boolean direction = true;

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Change Direction", "Press X");
            if (switchDirection.getState()) {
                direction = !direction;
            }

            if (direction) motor.setDirection(DcMotorSimple.Direction.FORWARD);
            else motor.setDirection(DcMotorSimple.Direction.REVERSE);

            double power = -gamepad1.left_stick_y;

            motor.setPower(power);

            telemetry.addData("Encoder count", motor.getCurrentPosition());

            String directionString;

            if (direction && power > 0) directionString = "FORWARD";
            else if (direction && power < 0) directionString = "REVERSE";
            else if (!direction && power > 0) directionString = "REVERSE";
            else if (!direction && power < 0) directionString = "FORWARD";
            else directionString = "STOPPED";

            telemetry.addData("Direction", "%s (act. %s)", motor.getDirection(), directionString);
            telemetry.update();
            switchDirection.readValue();
        }
    }
}
