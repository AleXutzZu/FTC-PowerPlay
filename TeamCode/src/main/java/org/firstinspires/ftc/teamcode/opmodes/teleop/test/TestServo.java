package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Test Servo", group = "Debugging")
public class TestServo extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo = hardwareMap.get(Servo.class, "servo");

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
                position -= 0.05f;
            } else if (gamepad.wasJustReleased(GamepadKeys.Button.DPAD_RIGHT)) {
                position += 0.05f;
            }

            if (gamepad.wasJustReleased(GamepadKeys.Button.A)) {
                //Clip position to 0-1
                position = Range.clip(position, 0f, 1f);
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
