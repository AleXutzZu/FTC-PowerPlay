package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.control.ElevatorControl;
import org.firstinspires.ftc.teamcode.control.TeleOpControl;

@TeleOp(name = "Test Improved Elevator", group = "Debugging")
public class TestImprovedElevator extends TeleOpControl {
    int index = 0;


    @Override
    protected void run() {

        telemetry.addData(">", "Use dpad left and dpad right to cycle through the levels");
        telemetry.addData(">", "Use X to send the level to the elevator");
        telemetry.addData("Current level", index);
        if (mainGamepad.wasJustReleased(GamepadKeys.Button.DPAD_RIGHT)) {
            index++;
            if (index >= ElevatorControl.ElevatorLevel.values().length) index = 0;
        }

        if (mainGamepad.wasJustReleased(GamepadKeys.Button.DPAD_LEFT)) {
            index--;
            if (index < 0) index = ElevatorControl.ElevatorLevel.values().length - 1;
        }

        ElevatorControl.ElevatorLevel level = ElevatorControl.ElevatorLevel.values()[index];

        if (mainGamepad.wasJustReleased(GamepadKeys.Button.X)) {
            elevator.setPosition(level);
        }

        telemetry.addData("Target left", robotHardware.getLeftElevatorMotor().getTargetPosition());
        telemetry.addData("Target right", robotHardware.getRightElevatorMotor().getTargetPosition());
        telemetry.addData("Current left", robotHardware.getLeftElevatorMotor().getCurrentPosition());
        telemetry.addData("Current right", robotHardware.getLeftElevatorMotor().getCurrentPosition());

        telemetry.addData("0 Mode right", robotHardware.getRightElevatorMotor().getZeroPowerBehavior());
        telemetry.addData("0 Mode left", robotHardware.getLeftElevatorMotor().getZeroPowerBehavior());

        telemetry.addData("Left power", robotHardware.getLeftFrontMotor().getPower());
        telemetry.addData("Right power", robotHardware.getRightFrontMotor().getPower());

        elevator.update();
    }
}
