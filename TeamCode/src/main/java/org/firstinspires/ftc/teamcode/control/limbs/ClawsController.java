package org.firstinspires.ftc.teamcode.control.limbs;

import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.control.Claws;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class ClawsController implements Claws {
    private final RobotHardware robotHardware;
    private boolean clawState = false;

    public ClawsController(RobotHardware robotHardware) {
        this.robotHardware = robotHardware;
    }

    @Override
    public boolean useClaws() {
        if (clawState) {
            robotHardware.getLeftClawServo().setPosition(Servo.MIN_POSITION);
            robotHardware.getRightClawServo().setPosition(Servo.MIN_POSITION);
            clawState = false;
        } else {
            robotHardware.getLeftClawServo().setPosition(Servo.MAX_POSITION);
            robotHardware.getRightClawServo().setPosition(Servo.MAX_POSITION);
            clawState = true;
        }

        return clawState;
    }

    @Override
    public void useClaws(boolean state) {
        if (state) {
            robotHardware.getLeftClawServo().setPosition(Servo.MAX_POSITION);
            robotHardware.getRightClawServo().setPosition(Servo.MAX_POSITION);
            clawState = true;
        } else {
            robotHardware.getRightClawServo().setPosition(Servo.MIN_POSITION);
            robotHardware.getLeftClawServo().setPosition(Servo.MIN_POSITION);
            clawState = false;
        }
    }

    @Override
    public boolean isClaws() {
        return clawState;
    }
}
