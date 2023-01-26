package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class AutonomousControl extends LinearOpMode implements Limbs {
    @Override
    public void runOpMode() throws InterruptedException {

    }

    @Override
    public boolean useClaws() {
        return false;
    }

    @Override
    public void useElevator(ElevatorLevel level) {

    }

    @Override
    public void useElevator(double power) {

    }

    @Override
    public boolean isSafeToMove() {
        return false;
    }

    @Override
    public void home() {

    }
}
