package org.firstinspires.ftc.teamcode.opmodes.autonomous.test;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.control.AutonomousControl;
import org.firstinspires.ftc.teamcode.control.Elevator;
import org.firstinspires.ftc.teamcode.control.MecanumDriveController;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.util.constants.DriveConstants;

@Autonomous(name = "FSM Test", group = "Debugging")
public class FSMTest extends AutonomousControl {

    private enum States {
        START, GO_TO_HIGH_JUNCTION_WITH_PRELOAD, ALIGN_TO_DROP_PRELOAD, DROP_PRELOAD, ALIGN_TO_STACK_PRELOAD,
        TURN_TO_STACK_PRELOAD,
        GO_TO_STACK,
        GRIP_CONE_FROM_STACK, LIFT_CONE_FROM_STACK,
        GO_TO_HIGH_JUNCTION, ALIGN_TO_DROP_CONE, DROP_CONE, ALIGN_TO_STACK, TURN_TO_STACK,
        PARK, IDLE
    }

    private Trajectory goToHighJunctionWithPreloadTrajectory;
    private final Pose2d startPose = new Pose2d(-35, -60.5, Math.toRadians(90));

    private Trajectory alignToDropPreloadTrajectory;
    private Trajectory alignOnStackLineAfterPreloadTrajectory;

    private Trajectory goToStackAfterPreloadTrajectory;
    private TrajectorySequence goToHighJunctionTrajectorySequence;

    private Trajectory alignToDropConeTrajectory;

    private Trajectory alignToStackLine;
    private Trajectory goToStack;

    private Trajectory parkingSpot1;
    private Trajectory parkingSpot2;
    private Trajectory parkingSpot3;

    private final ElapsedTime liftConeFromStackTimer = new ElapsedTime();
    private final ElapsedTime gripConeFromStackTimer = new ElapsedTime();
    private final ElapsedTime runtime = new ElapsedTime();

    private double stackTargetCm = 11;

    @Override

    protected void initTrajectories() {
        robotHardware.getMecanumDriveController().setPoseEstimate(startPose);

        goToHighJunctionWithPreloadTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(startPose)
                .lineTo(new Vector2d(-22.75, -60.5))
                .splineToConstantHeading(new Vector2d(-13, -20), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-12, -12, Math.toRadians(135)), Math.toRadians(90))
                .build();

        alignToDropPreloadTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(goToHighJunctionWithPreloadTrajectory.end())
                .forward(11.23, MecanumDriveController.getVelocityConstraint(25, DriveConstants.MAX_ANG_VEL), MecanumDriveController.getAccelerationConstraint(DriveConstants.MAX_ACCEL / 5))
                .build();

        alignOnStackLineAfterPreloadTrajectory = robotHardware.getMecanumDriveController().trajectoryBuilder(alignToDropPreloadTrajectory.end())
                .back(11.23)
                .build();

        goToStackAfterPreloadTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(alignOnStackLineAfterPreloadTrajectory.end().plus(new Pose2d(0, 0, Math.toRadians(45))))
                .forward(49)
                .build();

        goToHighJunctionTrajectorySequence = robotHardware.getMecanumDriveController()
                .trajectorySequenceBuilder(goToStackAfterPreloadTrajectory.end())
                .back(25)//24.5
                .turn(Math.toRadians(-135))
                .build();//TODO: tweak distance

        alignToDropConeTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(goToHighJunctionTrajectorySequence.end())
                .forward(11.1, MecanumDriveController.getVelocityConstraint(25, DriveConstants.MAX_ANG_VEL), MecanumDriveController.getAccelerationConstraint(DriveConstants.MAX_ACCEL / 5))
                .build();

        alignToStackLine = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(alignToDropConeTrajectory.end())
                .back(11.1)
                .build();

        goToStack = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(alignToStackLine.end().plus(new Pose2d(0, 0, Math.toRadians(135))))
                .forward(26)//24.5
                .build();

        parkingSpot1 = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(goToStack.start())
                .forward(20)
                .build();

        parkingSpot2 = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(goToStack.start())
                .forward(2)//TODO
                .build();

        parkingSpot3 = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(goToStack.start())
                .back(21)//TODO
                .build();
    }

    @Override
    protected void run() {
        States state = States.START;


        while (opModeIsActive()) {
            switch (state) {
                case START:
                    runtime.reset();
                    robotHardware.getElevatorController().setTarget((int) DriveConstants.elevatorCmToTicks(4));
                    robotHardware.getMecanumDriveController().followTrajectoryAsync(goToHighJunctionWithPreloadTrajectory);
                    state = States.GO_TO_HIGH_JUNCTION_WITH_PRELOAD;
                    break;
                case GO_TO_HIGH_JUNCTION_WITH_PRELOAD:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        state = States.ALIGN_TO_DROP_PRELOAD;
                        robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.HIGH);
                        robotHardware.getMecanumDriveController().followTrajectoryAsync(alignToDropPreloadTrajectory);
                    }
                    break;
                case ALIGN_TO_DROP_PRELOAD:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        state = States.DROP_PRELOAD;
                        robotHardware.getClawsController().useClaws();
                    }
                    break;
                case DROP_PRELOAD:
                    state = States.ALIGN_TO_STACK_PRELOAD;
                    robotHardware.getMecanumDriveController().followTrajectoryAsync(alignOnStackLineAfterPreloadTrajectory);
                    break;

                case ALIGN_TO_STACK_PRELOAD:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        state = States.TURN_TO_STACK_PRELOAD;
                        robotHardware.getMecanumDriveController().turnAsync(Math.toRadians(45));
                    }
                    break;
                case TURN_TO_STACK_PRELOAD:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        robotHardware.getElevatorController().setTarget((int) DriveConstants.elevatorCmToTicks(stackTargetCm));
                        robotHardware.getMecanumDriveController().followTrajectoryAsync(goToStackAfterPreloadTrajectory);
                        state = States.GO_TO_STACK;
                        stackTargetCm -= 2.5;//TODO
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
                    //TODO
                    if (gripConeFromStackTimer.milliseconds() > 350) {
                        robotHardware.getElevatorController().setTarget((int) DriveConstants.elevatorCmToTicks(stackTargetCm + 25));
                        state = States.LIFT_CONE_FROM_STACK;
                        liftConeFromStackTimer.reset();
                    }
                    break;

                case LIFT_CONE_FROM_STACK:
                    //TODO
                    if (liftConeFromStackTimer.milliseconds() > 600) {
                        robotHardware.getMecanumDriveController().followTrajectorySequenceAsync(goToHighJunctionTrajectorySequence);
                        state = States.GO_TO_HIGH_JUNCTION;
                    }
                    break;

                case GO_TO_HIGH_JUNCTION:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.HIGH);
                        robotHardware.getMecanumDriveController().followTrajectoryAsync(alignToDropConeTrajectory);
                        state = States.ALIGN_TO_DROP_CONE;
                    }
                    break;
                case ALIGN_TO_DROP_CONE:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        state = States.DROP_CONE;
                        robotHardware.getClawsController().useClaws();
                    }
                    break;
                case DROP_CONE:
                    robotHardware.getMecanumDriveController().followTrajectoryAsync(alignToStackLine);
                    state = States.ALIGN_TO_STACK;
                    break;

                case ALIGN_TO_STACK:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        robotHardware.getMecanumDriveController().turnAsync(Math.toRadians(135));
                        state = States.TURN_TO_STACK;
                    }
                    break;
                case TURN_TO_STACK:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        if (30f - runtime.seconds() > 5) {
                            robotHardware.getElevatorController().setTarget((int) DriveConstants.elevatorCmToTicks(stackTargetCm));
                            robotHardware.getMecanumDriveController().followTrajectoryAsync(goToStack);
                            state = States.GO_TO_STACK;
                            stackTargetCm -= 2.5;
                        } else {
                            state = States.PARK;
                            switch (getParkingSpot()) {
                                case ONE:
                                    robotHardware.getMecanumDriveController().followTrajectoryAsync(parkingSpot1);
                                    break;
                                case TWO:
                                    robotHardware.getMecanumDriveController().followTrajectoryAsync(parkingSpot2);
                                    break;
                                case THREE:
                                    robotHardware.getMecanumDriveController().followTrajectoryAsync(parkingSpot3);
                                    break;
                            }
                        }
                    }
                    break;
                case PARK:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        state = States.IDLE;
                        robotHardware.getClawsController().useClaws(true);
                        robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.BASE);
                    }
                case IDLE:
                    break;
            }
            robotHardware.getElevatorController().update();
            robotHardware.getMecanumDriveController().update();
        }
    }
}
