package org.firstinspires.ftc.teamcode.opmodes.autonomous.test;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.teamcode.control.AutonomousControl;

@Autonomous(name = "FSM Test", group = "Debugging")
@Disabled
public class FSMTest extends AutonomousControl {

    private enum States {
        START, GO_TO_HIGH_JUNCTION, PREPARE_TO_DROP_CONE,
        DROP_CONE, GO_TO_STACK, PREPARE_TO_RETRIEVE_FROM_STACK,
        RETRIEVE_FROM_STACK, PARK, IDLE
    }

    private Trajectory goToHighJunctionTrajectory;
    private final Pose2d startPose = new Pose2d(-35, -60.5, Math.toRadians(90));

    private Trajectory alignToDropPreloadTrajectory;

    @Override

    protected void initTrajectories() {
        robotHardware.getMecanumDriveController().setPoseEstimate(startPose);

        goToHighJunctionTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(startPose)
                .lineTo(new Vector2d(-22.75, -60.5))
                .splineToConstantHeading(new Vector2d(-13, -20), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-12, -12, Math.toRadians(135)), Math.toRadians(90))
                .build();

       /* alignToDropPreloadTrajectory = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(alignToHighJunctionForPreloadTrajectory.end())
                .forward(8).build();*/
    }

    @Override
    protected void run() {
        States state = States.START;

//        robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.LOW);

        while (opModeIsActive()) {
            switch (state) {
                case START:
                    robotHardware.getMecanumDriveController().followTrajectoryAsync(goToHighJunctionTrajectory);
                    state = States.GO_TO_HIGH_JUNCTION;
                    break;
                case GO_TO_HIGH_JUNCTION:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        state = States.IDLE;
                        /*robotHardware.getMecanumDriveController().followTrajectoryAsync(alignToDropPreloadTrajectory);
                        robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.HIGH);*/

                    }
                    break;

                /*case PREPARE_TO_DROP_CONE:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        state = States.DROP_CONE;
                    }
                    break;

                case DROP_CONE:
                    robotHardware.getClawsController().useClaws();
                    state = States.IDLE;
                    break;*/
                case IDLE:
                    idle();
                    break;
            }
            robotHardware.getElevatorController().update();
            robotHardware.getMecanumDriveController().update();
        }
    }
}
