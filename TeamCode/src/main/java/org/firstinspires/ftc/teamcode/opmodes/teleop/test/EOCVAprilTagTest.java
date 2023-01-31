package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.util.AprilTagPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.List;

@TeleOp(name = "EOCV April Tag Test", group = "Debugging")
public class EOCVAprilTagTest extends LinearOpMode {
    private final RobotHardware robotHardware = new RobotHardware(this);

    //dim - meters
    private static final double APRIL_TAG_SIZE = 0.045;

    @Override
    public void runOpMode() throws InterruptedException {
        robotHardware.initWebcam();

        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(robotHardware.getWebcam());

        AprilTagPipeline pipeline = new AprilTagPipeline(APRIL_TAG_SIZE);
        camera.setPipeline(pipeline);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(800, 448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        waitForStart();

        while (opModeIsActive()) {
            List<AprilTagDetection> detections = pipeline.getLatestDetections();

            for (AprilTagDetection detection : detections) {
                telemetry.addData("Detection ID", detection.id);
            }

            telemetry.update();
        }
    }
}
