package org.firstinspires.ftc.teamcode.sample;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

class EmptyPipeline extends OpenCvPipeline {
    private Mat lastResult;

    @Override
    public Mat processFrame(Mat input) {
        input.copyTo(lastResult);
        return input;
    }

    public Mat getLastResult() {
        return lastResult;
    }
}

@TeleOp(name = "Webcam Test", group = "Sample")
public class WebcamTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        final WebcamName webcam = hardwareMap.get(WebcamName.class, "Webcam 1");
        final OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(webcam, cameraMonitorViewId);
        EmptyPipeline pipeline = new EmptyPipeline();

        camera.setPipeline(pipeline);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        waitForStart();

        int count = 0;

        while (opModeIsActive()) {
            sleep(20);
            if (gamepad1.a) {
                pipeline.saveMatToDiskFullPath(pipeline.getLastResult(), "sdcard/FIRST/data/" + count + "-saved-frame.png");
                count++;
            }
        }
    }
}
