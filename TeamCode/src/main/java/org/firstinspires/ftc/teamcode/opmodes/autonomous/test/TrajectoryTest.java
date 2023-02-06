package org.firstinspires.ftc.teamcode.opmodes.autonomous.test;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

@Autonomous(name = "Trajectory Test", group = "Debugging")
public class TrajectoryTest extends LinearOpMode {
    private final RobotHardware robotHardware = new RobotHardware(this);

    @Override
    public void runOpMode() throws InterruptedException {
        robotHardware.initMecanumDriveController();

        Pose2d startPose = new Pose2d(-35, -60.5, Math.toRadians(90));

        robotHardware.getMecanumDriveController().setPoseEstimate(startPose);

        Trajectory trajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(startPose)
                .lineTo(new Vector2d(-22.75, -60.5))
                .splineToConstantHeading(new Vector2d(-13, -20), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-12, -12, Math.toRadians(135)), Math.toRadians(90))
                .build();

        waitForStart();

        if (isStopRequested()) return;

        robotHardware.getMecanumDriveController().followTrajectory(trajectory);

        idle();
    }
}
