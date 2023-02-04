package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.teamcode.control.AutonomousControl;

@Autonomous(name = "Red Side Left", group = "Demo", preselectTeleOp = "Omni Movement")
@Disabled
public class RedSideLeft extends AutonomousControl {

    private final double slightBackwards = 10;
    private final double goLeft = -70;
    private final double goRight = 70;
    private final double goForward = 75;

    @Override
    protected void initTrajectories() {

    }

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
