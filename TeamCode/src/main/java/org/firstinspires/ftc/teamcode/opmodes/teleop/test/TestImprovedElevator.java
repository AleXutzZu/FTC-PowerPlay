package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.control.Elevator;
import org.firstinspires.ftc.teamcode.control.TeleOpControl;
import org.firstinspires.ftc.teamcode.util.constants.DriveConstants;

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
            if (index >= Elevator.ElevatorLevel.values().length) index = 0;
        }

        if (mainGamepad.wasJustReleased(GamepadKeys.Button.DPAD_LEFT)) {
            index--;
            if (index < 0) index = Elevator.ElevatorLevel.values().length - 1;
        }

        Elevator.ElevatorLevel level = Elevator.ElevatorLevel.values()[index];

        if (mainGamepad.wasJustReleased(GamepadKeys.Button.X)) {
            robotHardware.getElevatorController().setTarget(level);
        }

        telemetry.addData("Calculated target", DriveConstants.elevatorCmToTicks(level.getHeight()));
        telemetry.addData("Target", robotHardware.getElevatorController().getTarget());
        telemetry.addData("Current", robotHardware.getElevatorController().getCurrentPosition());

        telemetry.addData("Left power", robotHardware.getLeftFrontMotor().getPower());
        telemetry.addData("Right power", robotHardware.getRightFrontMotor().getPower());

        robotHardware.getElevatorController().update();
    }
}
