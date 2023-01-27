package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.control.AutonomousControl;

@Autonomous(name = "Red Side Far", group = "Demo", preselectTeleOp = "Omni Movement")
public class RedSideClose extends AutonomousControl {

    private final double slightForward = 5;
    private final double goLeft = -50;
    private final double goRight = 50;
    private final double goForward = 75;

    @Override
    protected void run() {
        switch (getParkingSpot()) {
            case ONE:
                driveStraight(slightForward);
                driveSideways(goLeft);
                driveStraight(goForward);
                break;
            case TWO:
                driveStraight(goForward);
                break;
            case THREE:
                driveStraight(slightForward);
                driveSideways(goRight);
                driveStraight(goForward);
                break;
        }
        idle();
    }
}
