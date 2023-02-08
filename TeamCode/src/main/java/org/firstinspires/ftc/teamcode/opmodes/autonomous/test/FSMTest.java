package org.firstinspires.ftc.teamcode.opmodes.autonomous.test;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.control.AutonomousControl;
import org.firstinspires.ftc.teamcode.control.Elevator;
import org.firstinspires.ftc.teamcode.control.MecanumDriveController;
import org.firstinspires.ftc.teamcode.util.constants.DriveConstants;

@Autonomous(name = "FSM Test", group = "Debugging")
public class FSMTest extends AutonomousControl {

    private enum States {
        START, GO_TO_HIGH_JUNCTION, PREPARE_TO_DROP_CONE,
        DROP_CONE, ALIGN_TO_STACK_LINE, TURN_TO_STACK, GO_TO_STACK,
        GO_TO_JUNCTION,
        TAKE_CONE_FROM_STACK,
        LIFT_CONE_FROM_STACK,
        PARK, IDLE
    }

    private Trajectory goToHighJunctionTrajectory;
    private final Pose2d startPose = new Pose2d(-35, -60.5, Math.toRadians(90));

    private Trajectory alignToDropPreloadTrajectory;
    private Trajectory alignOnStackLineTrajectory;

    private Trajectory goToStackTrajectory;
    private Trajectory goToJunctionTrajectory;

    private final ElapsedTime liftConeFromStackTimer = new ElapsedTime();
    private final ElapsedTime gripConeFromStackTimer = new ElapsedTime();

    @Override

    protected void initTrajectories() {
        robotHardware.getMecanumDriveController().setPoseEstimate(startPose);

        goToHighJunctionTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(startPose)
                .lineTo(new Vector2d(-22.75, -60.5))
                .splineToConstantHeading(new Vector2d(-13, -20), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-12, -12, Math.toRadians(135)), Math.toRadians(90))
                .build();

        alignToDropPreloadTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(goToHighJunctionTrajectory.end())
                .forward(11, MecanumDriveController.getVelocityConstraint(25, DriveConstants.MAX_ANG_VEL), MecanumDriveController.getAccelerationConstraint(DriveConstants.MAX_ACCEL / 5)).build();

        alignOnStackLineTrajectory = robotHardware.getMecanumDriveController().trajectoryBuilder(alignToDropPreloadTrajectory.end())
                .back(11).build();

        goToStackTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(alignOnStackLineTrajectory.end().plus(new Pose2d(0, 0, Math.toRadians(45))))
                .forward(48.5).build();
        goToJunctionTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(goToStackTrajectory.end())
                .back(30).build();//TODO: tweak distance
    }

    @Override
    protected void run() {
        States state = States.START;

//        robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.LOW);

        while (opModeIsActive()) {
            switch (state) {
                case START:
                    robotHardware.getMecanumDriveController().followTrajectoryAsync(goToHighJunctionTrajectory);
                    robotHardware.getElevatorController().setTarget((int) DriveConstants.elevatorCmToTicks(3));
                    state = States.GO_TO_HIGH_JUNCTION;
                    break;
                case GO_TO_HIGH_JUNCTION:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        state = States.PREPARE_TO_DROP_CONE;
                        robotHardware.getMecanumDriveController().followTrajectoryAsync(alignToDropPreloadTrajectory);
                        robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.HIGH);
                    }
                    break;
                case PREPARE_TO_DROP_CONE:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        state = States.DROP_CONE;
                    }
                    break;

                case DROP_CONE:
                    robotHardware.getClawsController().useClaws();
                    state = States.ALIGN_TO_STACK_LINE;
                    robotHardware.getMecanumDriveController().followTrajectoryAsync(alignOnStackLineTrajectory);
                    break;
                case ALIGN_TO_STACK_LINE:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        robotHardware.getMecanumDriveController().turnAsync(Math.toRadians(45));
                        state = States.TURN_TO_STACK;
                    }
                case TURN_TO_STACK:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        robotHardware.getMecanumDriveController().followTrajectoryAsync(goToStackTrajectory);
                        robotHardware.getElevatorController().setTarget((int) DriveConstants.elevatorCmToTicks(14));
                        state = States.GO_TO_STACK;
                    }
                    break;
                case GO_TO_STACK:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        state = States.LIFT_CONE_FROM_STACK;
                        robotHardware.getClawsController().useClaws(false);
                        gripConeFromStackTimer.reset();
                    }
                case LIFT_CONE_FROM_STACK:
                    if (gripConeFromStackTimer.milliseconds() > 150) {
                        robotHardware.getElevatorController().setTarget((int) DriveConstants.elevatorCmToTicks(24));
                        liftConeFromStackTimer.reset();
                        state = States.TAKE_CONE_FROM_STACK;
                    }
                    break;
                case TAKE_CONE_FROM_STACK:
                    //Also check the length
                    if (liftConeFromStackTimer.milliseconds() > 300) {
                        state = States.GO_TO_JUNCTION;
                        robotHardware.getMecanumDriveController().followTrajectoryAsync(goToJunctionTrajectory);
                    }
                    break;
                case GO_TO_JUNCTION:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        state = States.IDLE;
                    }
                    break;
                case IDLE:
                    idle();
                    break;
            }
            robotHardware.getElevatorController().update();
            robotHardware.getMecanumDriveController().update();
        }
    }
}
