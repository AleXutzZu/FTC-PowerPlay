package org.firstinspires.ftc.teamcode.control.limbs;

import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.control.ClawsControl;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public class Claws implements ClawsControl {
    private final RobotHardware robotHardware;
    private boolean clawState = false;
    public Claws(RobotHardware robotHardware) {
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
}
