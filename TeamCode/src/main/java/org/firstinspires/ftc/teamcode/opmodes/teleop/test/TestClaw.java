package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
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

        telemetry.addData(">", "Start OpMode");
        telemetry.update();

        waitForStart();

        runtime.reset();

        boolean state = false;

        while (opModeIsActive()) {

            telemetry.addData(">", "Press X to toggle claws");

            if (gamepad.wasJustReleased(GamepadKeys.Button.X)) {
                if (state) {
                    robotHardware.getClawServo().setPosition(0);
                    state=false;
                } else {
                    robotHardware.getClawServo().setPosition(1);
                    state=true;
                }
            }

            telemetry.update();
            gamepad.readButtons();
        }
    }
}
