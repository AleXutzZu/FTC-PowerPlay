package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.control.AutonomousControl;

@Autonomous(name = "FSM Test", group = "Debugging")
public class FSMTest extends AutonomousControl {

    private enum States {
        START, ALIGN_TO_STACK
    }

    private Trajectory alignToStackTrajectory;
    private final Pose2d startPose = new Pose2d(-35, -60.5, Math.toRadians(90));

    @Override

    protected void initTrajectories() {
        robotHardware.getMecanumDriveController().setPoseEstimate(startPose);

        alignToStackTrajectory = robotHardware.getMecanumDriveController().trajectoryBuilder(startPose).splineToSplineHeading(new Pose2d(-12, -60.5, Math.toRadians(90)), 0)
                .splineToSplineHeading(new Pose2d(-12, -12, Math.toRadians(180)), Math.toRadians(90)).build();

    }

    @Override
    protected void run() {

        States state = States.START;

        while (opModeIsActive()) {
            switch (state) {
                case START:
                    robotHardware.getMecanumDriveController().followTrajectoryAsync(alignToStackTrajectory);
                    state = States.ALIGN_TO_STACK;
                    break;
                case ALIGN_TO_STACK:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {

                    }
            }

            robotHardware.getElevatorController().update();
        }
    }
}
