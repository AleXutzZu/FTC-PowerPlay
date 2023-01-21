package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

@TeleOp(name = "Test Claw", group = "Debugging")
public class TestClaw extends LinearOpMode {
    private final RobotHardware robotHardware = new RobotHardware(this);
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robotHardware.initDrivetrainMotors();
        GamepadEx gamepad = new GamepadEx(gamepad1);
        ToggleButtonReader toggleClaws = new ToggleButtonReader(gamepad, GamepadKeys.Button.X);

        telemetry.addData(">", "Start OpMode");
        telemetry.update();

        waitForStart();

        runtime.reset();
        while (opModeIsActive()) {

            telemetry.addData(">", "Press X to toggle claws");
            telemetry.addData("State", toggleClaws.getState());
            telemetry.addData("Runtime", runtime.toString());

            if (gamepad.wasJustReleased(GamepadKeys.Button.X)) {
                boolean state = toggleClaws.getState();
                if (state) {
                    robotHardware.getLeftClawServo().setPosition(0);
                    robotHardware.getRightClawServo().setPosition(0);
                } else {
                    robotHardware.getLeftClawServo().setPosition(1);
                    robotHardware.getRightClawServo().setPosition(1);
                }
            }

            telemetry.update();
            toggleClaws.readValue();
            gamepad.readButtons();
        }
    }
}
