package org.firstinspires.ftc.teamcode.opmodes.teleop.movement;

import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.control.Elevator;
import org.firstinspires.ftc.teamcode.control.TeleOpControl;
import org.firstinspires.ftc.teamcode.util.Constants;

@TeleOp(name = "Omni Movement", group = "Movement")
public class OmniMovement extends TeleOpControl {
    private static final int ELEVATOR_SCALE = 1;

    @Override
    protected void run() {
        double axial = mainGamepad.getLeftY();
        double lateral = mainGamepad.getLeftX();
        double yaw = mainGamepad.getRightX();

        drive(axial, lateral, yaw);

        if (secondaryGamepad.wasJustReleased(GamepadKeys.Button.A)) {
            robotHardware.getClawsController().useClaws();
        }

        if (secondaryGamepad.wasJustReleased(GamepadKeys.Button.DPAD_UP)) {
            robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.HIGH);
        }
        if (secondaryGamepad.wasJustReleased(GamepadKeys.Button.DPAD_DOWN)) {
            robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.BASE);
        }
        if (secondaryGamepad.wasJustReleased(GamepadKeys.Button.DPAD_RIGHT)) {
            robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.MIDDLE);
        }
        if (secondaryGamepad.wasJustReleased(GamepadKeys.Button.DPAD_LEFT)) {
            robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.LOW);
        }

        double elevatorTarget = robotHardware.getElevatorController().getTarget() + (secondaryGamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - secondaryGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)) * Constants.GOBILDA_5203_TICKS_PER_CM * ELEVATOR_SCALE;
        elevatorTarget = Range.clip(elevatorTarget, Constants.GOBILDA_5203_MIN_HEIGHT_TICKS, Constants.GOBILDA_5203_MAX_HEIGHT_TICKS) ;
        robotHardware.getElevatorController().setTarget((int) elevatorTarget);

        robotHardware.getElevatorController().update();
    }
}
