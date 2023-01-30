package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.control.AutonomousControl;

@Autonomous(name = "Blue Side Left", group = "Demo", preselectTeleOp = "Omni Movement")
public class BlueSideLeft extends AutonomousControl {

    private final double slightBackwards = 10;
    private final double goLeft = -65;
    private final double goRight = 65;
    private final double goForward = 75;

    @Override
    protected void run() {
        switch (getParkingSpot()) {
            case ONE:
                driveStraight(goForward-slightBackwards);
                driveSideways(goLeft);
                break;
            case TWO:
                driveStraight(goForward);
                break;
            case THREE:
                driveStraight(goForward-slightBackwards);
                driveSideways(goRight);
                break;
        }
        idle();
    }
}
