package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import lombok.Getter;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

public abstract class AutonomousControl extends LinearOpMode {
    @Getter
    protected final RobotHardware robotHardware = new RobotHardware(this);
    @Getter
    private SampleMecanumDrive mecanumDrive = null;

    @Override
    public void runOpMode() throws InterruptedException {
        robotHardware.initAutonomous();
        mecanumDrive = new SampleMecanumDrive(this);

        enhanced_init();

        waitForStart();

        if (isStopRequested()) return;

        run();
    }

    protected abstract void run();

    protected void enhanced_init() {

    }
}
