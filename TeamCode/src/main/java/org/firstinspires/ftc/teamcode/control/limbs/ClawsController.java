package org.firstinspires.ftc.teamcode.control.limbs;

import org.firstinspires.ftc.teamcode.control.Claws;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.util.constants.DriveConstants;

public class ClawsController implements Claws {
    private final RobotHardware robotHardware;
    private boolean clawState = false;

    public ClawsController(RobotHardware robotHardware) {
        this.robotHardware = robotHardware;
    }

    @Override
    public boolean useClaws() {
        if (clawState) {
            robotHardware.getClawServo().setPosition(DriveConstants.CLAW_CLOSE_POSITION);
            clawState = false;
        } else {
            robotHardware.getClawServo().setPosition(DriveConstants.CLAW_OPEN_POSITION);
            clawState = true;
        }

        return clawState;
    }

    @Override
    public void useClaws(boolean state) {
        if (state) {
            robotHardware.getClawServo().setPosition(DriveConstants.CLAW_OPEN_POSITION);
            clawState = true;
        } else {
            robotHardware.getClawServo().setPosition(DriveConstants.CLAW_CLOSE_POSITION);
            clawState = false;
        }
    }

    @Override
    public boolean isClaws() {
        return clawState;
    }
}
