package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.control.AutonomousControl;

@Autonomous(name = "All Sides Parking", group = "Demo", preselectTeleOp = "Omni Movement")
public class DemoAutonomous extends AutonomousControl {
    private Trajectory forwardTrajectory;

    private Trajectory leftTrajectory;
    private Trajectory rightTrajectory;

    @Override
    protected void initTrajectories() {
        forwardTrajectory = robotHardware.getMecanumDriveController().trajectoryBuilder(new Pose2d())
                .forward(27)
                .build();

        leftTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(forwardTrajectory.end())
                .strafeLeft(25.6).build();

        rightTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(forwardTrajectory.end())
                .strafeRight(25.6).build();
    }

    @Override
    protected void run() {
        robotHardware.getMecanumDriveController().followTrajectory(forwardTrajectory);
        switch (getParkingSpot()) {
            case ONE:
                robotHardware.getMecanumDriveController().followTrajectory(leftTrajectory);
                break;
            case TWO:
                break;
            case THREE:
                robotHardware.getMecanumDriveController().followTrajectory(rightTrajectory);
                break;
        }
    }
}
