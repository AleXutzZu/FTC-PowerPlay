package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import lombok.Getter;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

public abstract class AutonomousControl extends LinearOpMode {

    @Getter
    protected final RobotHardware robotHardware = new RobotHardware(this);

    protected enum ParkingSpot {
        ONE, TWO, THREE
    }

    @Getter
    private ParkingSpot parkingSpot = null;


    @Override
    public void runOpMode() throws InterruptedException {
        robotHardware.initAutonomous();
        robotHardware.getClawsController().useClaws(false);


        while (opModeInInit()) {

            telemetry.update();
        }


        if (isStopRequested()) return;

        run();
    }

    protected abstract void run();
    private final double TICKS_CENTIMETER = 537.6 / (9.6 * Math.PI);

    protected void driveStraight(double distance) {
        int target = (int) (distance * TICKS_CENTIMETER);

        robotHardware.getLeftFrontMotor().setTargetPosition(target);
        robotHardware.getLeftBackMotor().setTargetPosition(target);
        robotHardware.getRightFrontMotor().setTargetPosition(target);
        robotHardware.getRightBackMotor().setTargetPosition(target);


        robotHardware.getLeftFrontMotor().setDirection(DcMotorSimple.Direction.REVERSE);
        robotHardware.getLeftBackMotor().setDirection(DcMotorSimple.Direction.REVERSE);
        robotHardware.getRightFrontMotor().setDirection(DcMotorSimple.Direction.FORWARD);
        robotHardware.getRightBackMotor().setDirection(DcMotorSimple.Direction.FORWARD);

        setAllMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (isBusy()) {
            setAllPower(0.5);
        }
        setAllPower(0);

        setAllMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setAllMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //pos -> right
    //neg -> left
    protected void driveSideways(double distance) {
        int target = (int) (distance * TICKS_CENTIMETER);

        robotHardware.getLeftFrontMotor().setTargetPosition(target);
        robotHardware.getRightFrontMotor().setTargetPosition(-target);
        robotHardware.getLeftBackMotor().setTargetPosition(-target);
        robotHardware.getRightBackMotor().setTargetPosition(target);

        robotHardware.getLeftFrontMotor().setDirection(DcMotorSimple.Direction.REVERSE);
        robotHardware.getLeftBackMotor().setDirection(DcMotorSimple.Direction.REVERSE);
        robotHardware.getRightFrontMotor().setDirection(DcMotorSimple.Direction.FORWARD);
        robotHardware.getRightBackMotor().setDirection(DcMotorSimple.Direction.FORWARD);

        setAllMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (isBusy()) {
            setAllPower(0.5);
        }
        setAllPower(0);
        setAllMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setAllMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private boolean isBusy() {
        return (robotHardware.getLeftFrontMotor().isBusy() ||
                robotHardware.getLeftFrontMotor().isBusy() ||
                robotHardware.getRightBackMotor().isBusy() ||
                robotHardware.getRightFrontMotor().isBusy())
                && !isStopRequested();
    }

    private void setAllPower(double power) {
        robotHardware.getLeftFrontMotor().setPower(power);
        robotHardware.getLeftBackMotor().setPower(power);
        robotHardware.getRightFrontMotor().setPower(power);
        robotHardware.getRightBackMotor().setPower(power);
    }

    private void setAllMode(DcMotor.RunMode mode) {
        robotHardware.getLeftFrontMotor().setMode(mode);
        robotHardware.getLeftBackMotor().setMode(mode);
        robotHardware.getRightFrontMotor().setMode(mode);
        robotHardware.getRightBackMotor().setMode(mode);
    }
}
