package org.firstinspires.ftc.teamcode.opmodes.autonomous.test;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.control.AutonomousControl;
import org.firstinspires.ftc.teamcode.control.Elevator;
import org.firstinspires.ftc.teamcode.util.constants.DriveConstants;

@Autonomous(name = "New Right Side Autonomous", group = "Debugging")
public class NewRightSideAutonomous extends AutonomousControl {

    private final Pose2d startPose = new Pose2d(35, -60.5, Math.toRadians(90));

    private enum States {
        START, GO_TO_JUNCTION_WITH_PRELOAD, ALIGN_TO_STACK, GO_TO_STACK, IDLE,
        GRIP_CONE_FROM_STACK, LIFT_CONE_FROM_STACK, GO_TO_JUNCTION
    }

    private Trajectory goToJunctionWithPreloadTrajectory;
    private Trajectory alignToStackTrajectory;
    private Trajectory goToStackTrajectory;

    private Trajectory goToJunctionTrajectory;


    private int stackTargetCm = 11;

    private final ElapsedTime liftConeFromStackTimer = new ElapsedTime();
    private final ElapsedTime gripConeFromStackTimer = new ElapsedTime();
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    protected void initTrajectories() {
        robotHardware.getMecanumDriveController().setPoseEstimate(startPose);

        goToJunctionWithPreloadTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(startPose)
                .splineToConstantHeading(new Vector2d(35, -25), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(28.5, -5, Math.toRadians(135)), Math.toRadians(135))
                .addSpatialMarker(new Vector2d(35, -48), () -> robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.HIGH))
                .build();

        alignToStackTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(goToJunctionWithPreloadTrajectory.end(), true)
                .splineToLinearHeading(new Pose2d(55, -11.7, Math.toRadians(0)), Math.toRadians(0))
                .addSpatialMarker(new Vector2d(39, -10.6), () -> {
                    robotHardware.getElevatorController().setTarget((int) DriveConstants.elevatorCmToTicks(stackTargetCm));
                    stackTargetCm -= 2.5;
                })
                .build();
        goToStackTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(alignToStackTrajectory.end())
                .forward(5)
                .build();

        goToJunctionTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(goToStackTrajectory.end(), true)
                .splineToLinearHeading(new Pose2d(28.5, -5, Math.toRadians(135)), Math.toRadians(90))
                .addSpatialMarker(new Vector2d(-47, -13), () -> robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.HIGH))
                .build();
    }

    @Override
    protected void run() {
        States state = States.START;
        runtime.reset();

        while (opModeIsActive()) {
            switch (state) {

                case START:
                    robotHardware.getMecanumDriveController().followTrajectoryAsync(goToJunctionWithPreloadTrajectory);
                    state = States.GO_TO_JUNCTION_WITH_PRELOAD;
                    break;
                case GO_TO_JUNCTION_WITH_PRELOAD:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        robotHardware.getClawsController().useClaws();
                        robotHardware.getMecanumDriveController().followTrajectoryAsync(alignToStackTrajectory);

                        state = States.ALIGN_TO_STACK;
                    }
                    break;
                case ALIGN_TO_STACK:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        robotHardware.getMecanumDriveController().followTrajectoryAsync(goToStackTrajectory);
                        state = States.GO_TO_STACK;
                    }
                    break;
                case GO_TO_STACK:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        state = States.GRIP_CONE_FROM_STACK;
                        gripConeFromStackTimer.reset();
                        robotHardware.getClawsController().useClaws();
                    }
                    break;
                case GRIP_CONE_FROM_STACK:
                    if (gripConeFromStackTimer.milliseconds() > 350) {
                        robotHardware.getElevatorController().setTarget((int) DriveConstants.elevatorCmToTicks(stackTargetCm + 25));
                        liftConeFromStackTimer.reset();
                        state = States.LIFT_CONE_FROM_STACK;
                    }
                    break;
                case LIFT_CONE_FROM_STACK:
                    if (liftConeFromStackTimer.milliseconds() > 600) {
                        state = States.GO_TO_JUNCTION;
                        robotHardware.getMecanumDriveController().followTrajectoryAsync(goToJunctionTrajectory);
                    }
                    break;
                case GO_TO_JUNCTION:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        robotHardware.getClawsController().useClaws();

                        if (30 - runtime.seconds() > 5) {
                            state = States.ALIGN_TO_STACK;
                            robotHardware.getMecanumDriveController().followTrajectoryAsync(alignToStackTrajectory);
                        } else {
                            state = States.IDLE;
                        }
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
