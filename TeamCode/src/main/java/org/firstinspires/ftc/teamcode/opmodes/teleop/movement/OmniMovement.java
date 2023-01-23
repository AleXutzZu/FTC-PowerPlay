package org.firstinspires.ftc.teamcode.opmodes.teleop.movement;

import org.firstinspires.ftc.teamcode.control.TeleOpControl;

//TODO: Fix up this to use the new TeleOpControl class
public class OmniMovement extends TeleOpControl {
    @Override
    protected void run() {
        double axial = mainGamepad.getLeftY();
        double lateral = mainGamepad.getLeftX();
        double yaw = mainGamepad.getRightX();

        drive(axial, lateral, yaw);
    }
}
