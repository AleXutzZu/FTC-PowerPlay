package org.firstinspires.ftc.teamcode.control;

import com.acmerobotics.roadrunner.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MecanumDriveController extends MecanumDrive {

    private final RobotHardware robotHardware;

    public MecanumDriveController(RobotHardware robotHardware) {

        this.robotHardware = robotHardware;
    }


    public MecanumDriveController(double kV, double kA, double kStatic, double trackWidth, double wheelBase, double lateralMultiplier) {
        super(kV, kA, kStatic, trackWidth, wheelBase, lateralMultiplier);
    }

    @Override
    protected double getRawExternalHeading() {
        return 0;
    }

    @NotNull
    @Override
    public List<Double> getWheelPositions() {
        return null;
    }

    @Override
    public void setMotorPowers(double v, double v1, double v2, double v3) {

    }
}
