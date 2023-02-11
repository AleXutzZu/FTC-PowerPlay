package org.firstinspires.ftc.teamcode.opmodes.autonomous.test;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.control.AutonomousControl;
import org.firstinspires.ftc.teamcode.control.Elevator;

@Autonomous(name = "Third Trajectory Debugging", group = "Debugging")
public class AlternateTrajectoryTest extends AutonomousControl {

    private final Pose2d startPose = new Pose2d(-35, -60.5, Math.toRadians(90));

    private enum States {
        START, GO_TO_JUNCTION_WITH_PRELOAD, IDLE;
    }

    private Trajectory goToJunctionWithPreload;

    @Override
    protected void initTrajectories() {
        robotHardware.getMecanumDriveController().setPoseEstimate(startPose);

        goToJunctionWithPreload = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(startPose)
                .splineToConstantHeading(new Vector2d(-35, -25), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-28.5, -4, Math.toRadians(45)), Math.toRadians(45))
                .addSpatialMarker(new Vector2d(-35, -48), () -> robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.HIGH))
                .build();
    }

    @Override
    protected void run() {
        States state = States.START;

        while (opModeIsActive()) {
            switch (state) {

                case START:
                    robotHardware.getMecanumDriveController().followTrajectoryAsync(goToJunctionWithPreload);
                    state = States.GO_TO_JUNCTION_WITH_PRELOAD;
                    break;
                case GO_TO_JUNCTION_WITH_PRELOAD:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        robotHardware.getClawsController().useClaws();
                        state = States.IDLE;
                    }
                    break;
                case IDLE:
                    idle();
                    break;
            }

            robotHardware.getMecanumDriveController().update();
            robotHardware.getElevatorController().update();
        }
    }
}
