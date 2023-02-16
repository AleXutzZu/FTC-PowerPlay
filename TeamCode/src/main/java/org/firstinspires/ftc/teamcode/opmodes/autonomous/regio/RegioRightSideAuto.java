package org.firstinspires.ftc.teamcode.opmodes.autonomous.regio;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.control.AutonomousControl;
import org.firstinspires.ftc.teamcode.control.Elevator;
import org.firstinspires.ftc.teamcode.control.MecanumDriveController;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.util.constants.DriveConstants;

@Autonomous(name = "Right Side Auto Nebuna", group = "Regio", preselectTeleOp = "Omni Movement")
public class RegioRightSideAuto extends AutonomousControl {

    private final Pose2d startPose = new Pose2d(35, -60.5, Math.toRadians(90));

    private enum States {
        START, GO_TO_JUNCTION_WITH_PRELOAD, ALIGN_TO_STACK, GO_TO_STACK, IDLE, PARK,
        GRIP_CONE_FROM_STACK, LIFT_CONE_FROM_STACK, GO_TO_JUNCTION
    }

    private TrajectorySequence goToJunctionWithPreloadTrajectory;
    private TrajectorySequence alignToStackTrajectory;
    private TrajectorySequence goToStackTrajectory;
    private TrajectorySequence goToJunctionTrajectory;
    private TrajectorySequence parkingSpot1;
    private TrajectorySequence parkingSpot2;
    private TrajectorySequence parkingSpot3;
    private TrajectorySequence park;


    private int stackTargetCm = 12;

    private final ElapsedTime liftConeFromStackTimer = new ElapsedTime();
    private final ElapsedTime gripConeFromStackTimer = new ElapsedTime();
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    protected void initTrajectories() {
        robotHardware.getMecanumDriveController().setPoseEstimate(startPose);

        goToJunctionWithPreloadTrajectory = robotHardware.getMecanumDriveController()
                .trajectorySequenceBuilder(startPose)
                .splineToConstantHeading(new Vector2d(35, -25), Math.toRadians(90))
                .setAccelConstraint(MecanumDriveController.getAccelerationConstraint(DriveConstants.MAX_ACCEL * 0.7))
                .splineToSplineHeading(new Pose2d(27, -7, Math.toRadians(135)), Math.toRadians(135))
                .addSpatialMarker(new Vector2d(35, -57), () -> robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.HIGH))
                .build();

        alignToStackTrajectory = robotHardware.getMecanumDriveController()
                .trajectorySequenceBuilder(goToJunctionWithPreloadTrajectory.end())
                .setReversed(true)
                .setAccelConstraint(MecanumDriveController.getAccelerationConstraint(DriveConstants.MAX_ACCEL * 0.5))
                .splineToLinearHeading(new Pose2d(55, -11.7, Math.toRadians(0)), Math.toRadians(0))
                .addSpatialMarker(new Vector2d(39, -10.6), () -> {
                    robotHardware.getElevatorController().setTarget((int) DriveConstants.elevatorCmToTicks(stackTargetCm));
                    stackTargetCm -= 2.5;
                })
                .build();
        goToStackTrajectory = robotHardware.getMecanumDriveController()
                .trajectorySequenceBuilder(alignToStackTrajectory.end())
                .forward(5)
                .build();

        goToJunctionTrajectory = robotHardware.getMecanumDriveController()
                .trajectorySequenceBuilder(goToStackTrajectory.end())
                .setReversed(true)
                .setAccelConstraint(MecanumDriveController.getAccelerationConstraint(DriveConstants.MAX_ACCEL * 0.4))
                .setVelConstraint(MecanumDriveController.getVelocityConstraint(DriveConstants.MAX_VEL * 0.7, DriveConstants.MAX_ANG_VEL))
                .splineToLinearHeading(new Pose2d(26, -6, Math.toRadians(135)), Math.toRadians(90))
                .addSpatialMarker(new Vector2d(47, -13), () -> robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.HIGH))
                .build();
        parkingSpot3 = robotHardware.getMecanumDriveController()
                .trajectorySequenceBuilder(alignToStackTrajectory.end())
                .forward(0.1)
                .build();
        parkingSpot2 = robotHardware.getMecanumDriveController()
                .trajectorySequenceBuilder(alignToStackTrajectory.end())
                .back(23)
                .build();
        parkingSpot1 = robotHardware.getMecanumDriveController()
                .trajectorySequenceBuilder(alignToStackTrajectory.end())
                .back(47)
                .build();
        park = robotHardware.getMecanumDriveController()
                .trajectorySequenceBuilder(goToJunctionTrajectory.end())
                .splineToLinearHeading(new Pose2d(40, -11.7, Math.toRadians(0)), Math.toRadians(0))
                .build();
    }

    @Override
    protected void run() {
        States state = States.START;
        runtime.reset();

        while (opModeIsActive()) {
            switch (state) {

                case START:
                    robotHardware.getMecanumDriveController().followTrajectorySequenceAsync(goToJunctionWithPreloadTrajectory);
                    state = States.GO_TO_JUNCTION_WITH_PRELOAD;
                    break;
                case GO_TO_JUNCTION_WITH_PRELOAD:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        robotHardware.getClawsController().useClaws();
                        robotHardware.getMecanumDriveController().followTrajectorySequenceAsync(alignToStackTrajectory);
                        state = States.ALIGN_TO_STACK;

                    }
                    break;
                case ALIGN_TO_STACK:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        if (30000 - runtime.milliseconds() > 5500) {
                            robotHardware.getMecanumDriveController().followTrajectorySequenceAsync(goToStackTrajectory);
                            state = States.GO_TO_STACK;
                        } else {
                            state = States.PARK;
                            robotHardware.getMecanumDriveController().followTrajectorySequenceAsync(park);
                            switch (getParkingSpot()) {
                                case ONE:
                                    robotHardware.getClawsController().useClaws();
                                    robotHardware.getMecanumDriveController().followTrajectorySequenceAsync(parkingSpot1);
                                    robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.BASE);
                                    break;
                                case TWO:
                                    robotHardware.getMecanumDriveController().followTrajectorySequenceAsync(parkingSpot2);
                                    robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.BASE);
                                    break;
                                case THREE:
                                    robotHardware.getMecanumDriveController().followTrajectorySequenceAsync(parkingSpot3);
                                    robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.BASE);
                                    break;
                            }
                        }
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
                    if (gripConeFromStackTimer.milliseconds() > 250) {
                        state = States.LIFT_CONE_FROM_STACK;
                        robotHardware.getElevatorController().setTarget((int) DriveConstants.elevatorCmToTicks(stackTargetCm + 29));
                        liftConeFromStackTimer.reset();
                    }
                    break;
                case LIFT_CONE_FROM_STACK:
                    if (liftConeFromStackTimer.milliseconds() > 400) {
                        state = States.GO_TO_JUNCTION;
                        robotHardware.getMecanumDriveController().followTrajectorySequenceAsync(goToJunctionTrajectory);
                    }
                    break;
                case GO_TO_JUNCTION:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        robotHardware.getClawsController().useClaws();
                        robotHardware.getMecanumDriveController().followTrajectorySequenceAsync(alignToStackTrajectory);
                        state = States.ALIGN_TO_STACK;
                    }
                    break;
                case PARK:
                    if (!robotHardware.getMecanumDriveController().isBusy()) state = States.IDLE;
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
