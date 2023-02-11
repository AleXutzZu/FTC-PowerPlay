package org.firstinspires.ftc.teamcode.opmodes.autonomous.test;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.control.AutonomousControl;
import org.firstinspires.ftc.teamcode.control.Elevator;
import org.firstinspires.ftc.teamcode.util.constants.DriveConstants;

@Autonomous(name = "Third Trajectory Debugging", group = "Debugging")
public class AlternateTrajectoryTest extends AutonomousControl {

    private final Pose2d startPose = new Pose2d(-35, -60.5, Math.toRadians(90));

    private enum States {
        START, GO_TO_JUNCTION_WITH_PRELOAD,ALIGN_TO_STACK, GO_TO_STACK, IDLE;
    }

    private Trajectory goToJunctionWithPreload;
    private Trajectory alignToStack;
    private Trajectory goToStack;
    private int stackTargetCm = 11;

    @Override
    protected void initTrajectories() {
        robotHardware.getMecanumDriveController().setPoseEstimate(startPose);

        goToJunctionWithPreload = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(startPose)
                .splineToConstantHeading(new Vector2d(-35, -25), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(-28.5, -5, Math.toRadians(45)), Math.toRadians(45))
                .addSpatialMarker(new Vector2d(-35, -48), () -> robotHardware.getElevatorController().setTarget(Elevator.ElevatorLevel.HIGH))
                .build();

        alignToStack = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(goToJunctionWithPreload.end(), true)
                .splineToLinearHeading(new Pose2d(-55, -11.7, Math.toRadians(180)), Math.toRadians(180))
                .addSpatialMarker(new Vector2d(-39, -10.6), () -> {
                    robotHardware.getElevatorController().setTarget((int) DriveConstants.elevatorCmToTicks(stackTargetCm));
                    stackTargetCm -= 2.5;
                })
                .build();
        goToStack = robotHardware.getMecanumDriveController()
                .trajectoryBuilder(alignToStack.end())
                .forward(5.5)
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
                        state = States.ALIGN_TO_STACK;
                    }
                    break;
                case ALIGN_TO_STACK:
                    if (!robotHardware.getMecanumDriveController().isBusy()) {
                        robotHardware.getMecanumDriveController().followTrajectoryAsync(alignToStack);
                        state = States.GO_TO_STACK;
                    }
                case GO_TO_STACK:
                    if(!robotHardware.getMecanumDriveController().isBusy()){
                        robotHardware.getMecanumDriveController().followTrajectoryAsync(goToStack);
                        state = States.IDLE;
                    }
                case IDLE:
                    idle();
                    break;
            }

            robotHardware.getMecanumDriveController().update();
            robotHardware.getElevatorController().update();
        }
    }
}
