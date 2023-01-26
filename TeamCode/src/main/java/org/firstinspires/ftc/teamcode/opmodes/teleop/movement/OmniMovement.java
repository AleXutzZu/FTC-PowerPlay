package org.firstinspires.ftc.teamcode.opmodes.teleop.movement;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.control.TeleOpControl;

@TeleOp(name = "Omni Movement", group = "Movement")
public class OmniMovement extends TeleOpControl {
    @Override
    protected void run() {
        double axial = mainGamepad.getLeftY();
        double lateral = mainGamepad.getLeftX();
        double yaw = mainGamepad.getRightX();

        drive(axial, lateral, yaw);
    }
}
