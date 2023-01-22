package org.firstinspires.ftc.teamcode.opmodes.teleop.movement;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
//TODO: Fix up this to use the new TeleOpControl class
public class OmniMovement extends LinearOpMode {
    private RobotHardware robotHardware;

    protected void drive() {
        double max;

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateral =  gamepad1.left_stick_x;
        double yaw     =  gamepad1.right_stick_x;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double targetLeftFrontPower  = axial + lateral + yaw;
        double targetRightFrontPower = axial - lateral - yaw;
        double targetLeftBackPower   = axial - lateral + yaw;
        double targetRightBackPower  = axial + lateral - yaw;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(targetLeftFrontPower), Math.abs(targetRightFrontPower));
        max = Math.max(max, Math.abs(targetLeftBackPower));
        max = Math.max(max, Math.abs(targetRightBackPower));

        if (max > 1.0) {
            targetLeftFrontPower  /= max;
            targetRightFrontPower /= max;
            targetLeftBackPower   /= max;
            targetRightBackPower  /= max;
        }

        // Send calculated power to wheels
        robotHardware.getLeftFrontMotor().setPower(targetLeftFrontPower);
        robotHardware.getRightFrontMotor().setPower(targetRightFrontPower);
        robotHardware.getLeftBackMotor().setPower(targetLeftBackPower);
        robotHardware.getRightBackMotor().setPower(targetRightBackPower);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        //Init Phase
        robotHardware = new RobotHardware(this) ;
        robotHardware.initTeleOp();
        while (opModeIsActive()) {
            drive();
        }
    }
}
