package org.firstinspires.ftc.teamcode.opmodes.teleop.movement;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

@TeleOp(name = "Omni Movement", group = "Movement")
public class OmniMovement extends LinearOpMode {
    private final RobotHardware robotHardware = new RobotHardware(this);

    protected void drive() {
        double max;

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateral = gamepad1.left_stick_x;
        double yaw = gamepad1.right_stick_x;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double targetLeftFrontPower = axial + lateral + yaw;
        double targetRightFrontPower = axial - lateral - yaw;
        double targetLeftBackPower = axial - lateral + yaw;
        double targetRightBackPower = axial + lateral - yaw;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(targetLeftFrontPower), Math.abs(targetRightFrontPower));
        max = Math.max(max, Math.abs(targetLeftBackPower));
        max = Math.max(max, Math.abs(targetRightBackPower));

        if (max > 1.0) {
            targetLeftFrontPower /= max;
            targetRightFrontPower /= max;
            targetLeftBackPower /= max;
            targetRightBackPower /= max;
        }

        // This is test code:
        //
        // Uncomment the following code to test your motor directions.
        // Each button should make the corresponding motor run FORWARD.
        //   1) First get all the motors to take to correct positions on the robot
        //      by adjusting your Robot Configuration if necessary.
        //   2) Then make sure they run in the correct direction by modifying the
        //      the setDirection() calls above.
        // Once the correct motors move in the correct direction re-comment this code.

            /*
            targetLeftFrontPower  = gamepad1.x ? 1.0 : 0.0;  // X gamepad
            targetLeftBackPower   = gamepad1.a ? 1.0 : 0.0;  // A gamepad
            targetRightFrontPower = gamepad1.y ? 1.0 : 0.0;  // Y gamepad
            targetRightBackPower  = gamepad1.b ? 1.0 : 0.0;  // B gamepad
            */

        // Send calculated power to wheels
        robotHardware.getLeftFrontMotor().setPower(targetLeftFrontPower);
        robotHardware.getRightFrontMotor().setPower(targetRightFrontPower);
        robotHardware.getLeftBackMotor().setPower(targetLeftBackPower);
        robotHardware.getRightBackMotor().setPower(targetRightBackPower);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        //Init Phase
        robotHardware.initTeleOp();

        waitForStart();

        while (opModeIsActive()) {
            drive();

            telemetry.addData("Left back motor encoder output:", robotHardware.getLeftBackMotor().getCurrentPosition());
            telemetry.addData("Left front motor encoder output:", robotHardware.getLeftFrontMotor().getCurrentPosition());
            telemetry.addData("Right back motor encoder output:", robotHardware.getRightBackMotor().getCurrentPosition());
            telemetry.addData("Right front motor encoder output:", robotHardware.getRightFrontMotor().getCurrentPosition());

            telemetry.update();
        }
    }
}
