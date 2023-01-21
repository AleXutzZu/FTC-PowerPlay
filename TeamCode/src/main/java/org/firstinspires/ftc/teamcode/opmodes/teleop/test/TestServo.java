package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Test Servo", group = "Debugging")
public class TestServo extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo = hardwareMap.get(Servo.class, "servo");
        servo.scaleRange(1f / 60f, 1f / 15f);

        GamepadEx gamepad = new GamepadEx(gamepad1);

        ToggleButtonReader directionToggle = new ToggleButtonReader(gamepad, GamepadKeys.Button.X);
        float position = 0f;

        telemetry.addData(">", "Start OpMode");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData(">", "Use dpad-left and dpad-right to decrement/increment position. Press A to send to servo.");
            telemetry.addData(">", "Press X to toggle direction");

            telemetry.addData("Calculated position", "%.2f (deg %.2f)", position, position * 1800f);

            if (gamepad.wasJustReleased(GamepadKeys.Button.DPAD_LEFT)) {
                position -= 0.01f;
                if (position < 0) position = 1;
            } else if (gamepad.wasJustReleased(GamepadKeys.Button.DPAD_RIGHT)) {
                position += 0.01f;
                if (position > 1) position = 0;
            }

            if (gamepad.wasJustReleased(GamepadKeys.Button.A)) {
                servo.setPosition(position);
            }

            if (directionToggle.getState()) {
                servo.setDirection(Servo.Direction.REVERSE);
            } else servo.setDirection(Servo.Direction.FORWARD);

            telemetry.addData("Direction", servo.getDirection());
            telemetry.addData("Position", "frac %.2f, deg %.2f", servo.getPosition(), servo.getPosition() * 1800f);

            directionToggle.readValue();
            gamepad.readButtons();
            telemetry.update();
        }
    }
}
