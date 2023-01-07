package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * This OpMode tests a motor that is assigned the name "motor" in the active Robot Configuration.
 * It checks the encoder output and the direction of the rotation. You can change the direction of the motor by
 * pressing X on the controller (Logitech F310) and this will change how the motor responds to receiving power.
 * Push forward on the left stick to give a positive power and backward to input a negative power. Leaving the stick
 * untouched will yield no power (0f). The actual direction of the motor is taken in reference to an outside observer
 */
@TeleOp(name = "Test Motor", group = "Debugging")
public class TestMotor extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "motor");
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        GamepadEx gamepad = new GamepadEx(gamepad1);
        ToggleButtonReader switchDirection = new ToggleButtonReader(gamepad, GamepadKeys.Button.X);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Change Direction", "Press X");
            boolean state = !switchDirection.getState();

            if (state) motor.setDirection(DcMotorSimple.Direction.FORWARD);
            else motor.setDirection(DcMotorSimple.Direction.REVERSE);

            double power = -gamepad1.left_stick_y;

            motor.setPower(power);

            telemetry.addData("Encoder count", motor.getCurrentPosition());

            String directionString;

            if (state && power > 0) directionString = "FORWARD";
            else if (state && power < 0) directionString = "REVERSE";
            else if (!state && power > 0) directionString = "REVERSE";
            else if (!state && power < 0) directionString = "FORWARD";
            else directionString = "STOPPED";

            telemetry.addData("Direction", "%s (act. %s)", motor.getDirection(), directionString);
            telemetry.update();
            switchDirection.readValue();
        }
    }
}
