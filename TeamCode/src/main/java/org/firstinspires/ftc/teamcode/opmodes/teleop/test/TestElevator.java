package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

@TeleOp(name = "Test Elevator", group = "Debugging")
public class TestElevator extends OpMode {
    private final RobotHardware robotHardware = new RobotHardware(this);

    @Override
    public void init() {
        robotHardware.initDrivetrainMotors();
    }

    @Override
    public void loop() {
        double power = -gamepad1.left_stick_y * 0.25;

        robotHardware.getLeftElevatorMotor().setPower(power);
        robotHardware.getRightElevatorMotor().setPower(power);
    }
}
