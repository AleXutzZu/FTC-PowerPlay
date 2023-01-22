package org.firstinspires.ftc.teamcode.opmodes.teleop.test;

import android.graphics.Bitmap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.*;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.openftc.easyopencv.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@TeleOp(name = "TFOD + EOCV Test", group = "Debugging")
public class TFODEOCVTest extends LinearOpMode {

    private TFObjectDetector tfObjectDetector;
    private final RobotHardware robotHardware = new RobotHardware(this);
    private static final String[] LABELS = {
            "1 Bolt",
            "2 Bulb",
            "3 Panel"
    };

    private static final int WIDTH = 800;
    private static final int HEIGHT = 448;
    private static final float FOCAL_LENGTH_X = 676.128f;
    private static final float FOCAL_LENGTH_Y = 676.128f;

    @Override
    public void runOpMode() throws InterruptedException {
        robotHardware.initWebcam();
        EOCVFrameGenerator pipeline = new EOCVFrameGenerator(WIDTH, HEIGHT, FOCAL_LENGTH_X, FOCAL_LENGTH_Y);
        OpenCvWebcam webcam = OpenCvCameraFactory.getInstance().createWebcam(robotHardware.getWebcam());

        webcam.setPipeline(pipeline);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(WIDTH, HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });

        initTFOD(pipeline);

        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            List<Recognition> updatedRecognitions = tfObjectDetector.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());

                Recognition topLeft = null;
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData(
                            String.format("%s (%.02f)", recognition.getLabel(), recognition.getConfidence()),
                            String.format("%d, %d",
                                    (int) (recognition.getLeft() + recognition.getWidth() / 2),
                                    (int) (recognition.getTop() + recognition.getHeight() / 2)));

                    if (topLeft == null) {
                        topLeft = recognition;
                    } else {
                        if (recognition.getRight() < topLeft.getLeft() ||
                                recognition.getBottom() < topLeft.getTop()) {
                            topLeft = recognition;
                        }
                    }
                }
                if (topLeft != null) {
                    telemetry.addData("TopLeft", topLeft.getLabel());
                }
                telemetry.update();
            }
        }

    }

    private void initTFOD(FrameGenerator frameGenerator) {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.75f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfObjectDetector = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, frameGenerator);

        // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
        // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
        tfObjectDetector.loadModelFromAsset("PowerPlay.tflite", LABELS);
    }


    private static class EOCVFrameGenerator extends OpenCvPipeline implements FrameGenerator {

        private final CameraInformation cameraInformation;
        private volatile Bitmap bitmap;
        private final AtomicReference<FrameConsumer> frameConsumerHolder = new AtomicReference<>();

        public EOCVFrameGenerator(int width, int height, float focalLengthX, float focalLengthY) {
            cameraInformation = new CameraInformation(width, height, 0, focalLengthX, focalLengthY);
        }

        @Override
        public CameraInformation getCameraInformation() {
            return cameraInformation;
        }

        @Override
        public void setFrameConsumer(FrameConsumer frameConsumer) {
            frameConsumerHolder.set(frameConsumer);
        }

        @Override
        public Mat processFrame(Mat input) {
            FrameConsumer frameConsumer = frameConsumerHolder.get();
            if (frameConsumer != null) {
                if (bitmap == null) {
                    bitmap = Bitmap.createBitmap(input.width(), input.height(), Bitmap.Config.ARGB_8888);
                    frameConsumer.init(bitmap);
                }
                Utils.matToBitmap(input, bitmap);
                frameConsumer.processFrame();
            }
            return input;
        }
    }
}
