package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import lombok.Getter;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.Constants;

import java.util.List;

public abstract class AutonomousControl extends LinearOpMode {

    private static final double MAGNIFICATION = 1.0;

    @Getter
    protected final RobotHardware robotHardware = new RobotHardware(this);
    @Getter
    private SampleMecanumDrive mecanumDrive = null;
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    protected enum ParkingSpot {
        ONE, TWO, THREE
    }

    @Getter
    private ParkingSpot parkingSpot = null;


    @Override
    public void runOpMode() throws InterruptedException {
        robotHardware.initAutonomous();
        robotHardware.getClawsController().useClaws(false);
        mecanumDrive = new SampleMecanumDrive(this);
        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();

            tfod.setZoom(MAGNIFICATION, 16.0 / 9.0);
        }

        Recognition sleeveRecognition = null;

        while (opModeInInit()) {
            if (tfod != null) {
                List<Recognition> recognitions = tfod.getRecognitions();

                for (Recognition recognition : recognitions) {
                    sleeveRecognition = recognition;
                }

            }
        }

        if (sleeveRecognition == null) {
            parkingSpot = ParkingSpot.TWO;
        } else {
            switch (sleeveRecognition.getLabel()) {
                case "1 ORANGE":
                    parkingSpot = ParkingSpot.ONE;
                    break;
                case "3 DONUT":
                    parkingSpot = ParkingSpot.THREE;
                    break;
                default:
                    parkingSpot = ParkingSpot.TWO;
                    break;
            }
        }

        if (isStopRequested()) return;

        run();
    }

    protected abstract void run();


    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = Constants.VUFORIA_KEY;
        parameters.cameraName = robotHardware.getWebcam();

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.60f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);

        // Use loadModelFromAsset() if the TF Model is built in as an asset by Android Studio
        // Use loadModelFromFile() if you have downloaded a custom team model to the Robot Controller's FLASH.
//        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
        tfod.loadModelFromFile(Constants.TFOD_MODEL_FILE, Constants.LABELS);
    }

    private final double TICKS_CENTIMETER = 537.6 / (9.6 * Math.PI);

    protected void driveStraight(double distance) {
        telemetry.addData("Function", "driveStraight");
        telemetry.addData("Dist", distance);
        telemetry.update();
        int target = (int) (distance * TICKS_CENTIMETER);

        robotHardware.getLeftFrontMotor().setTargetPosition(target);
        robotHardware.getLeftBackMotor().setTargetPosition(target);
        robotHardware.getRightFrontMotor().setTargetPosition(target);
        robotHardware.getRightBackMotor().setTargetPosition(target);


        robotHardware.getLeftFrontMotor().setDirection(DcMotorSimple.Direction.REVERSE);
        robotHardware.getLeftBackMotor().setDirection(DcMotorSimple.Direction.REVERSE);
        robotHardware.getRightFrontMotor().setDirection(DcMotorSimple.Direction.FORWARD);
        robotHardware.getRightBackMotor().setDirection(DcMotorSimple.Direction.FORWARD);

        setAllMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (isBusy()) {
            setAllPower(0.5);
        }
        setAllPower(0);

        setAllMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setAllMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //pos -> right
    //neg -> left
    protected void driveSideways(double distance) {
        int target = (int) (distance * TICKS_CENTIMETER);

        robotHardware.getLeftFrontMotor().setTargetPosition(target);
        robotHardware.getRightFrontMotor().setTargetPosition(-target);
        robotHardware.getLeftBackMotor().setTargetPosition(-target);
        robotHardware.getRightBackMotor().setTargetPosition(target);

        robotHardware.getLeftFrontMotor().setDirection(DcMotorSimple.Direction.REVERSE);
        robotHardware.getLeftBackMotor().setDirection(DcMotorSimple.Direction.REVERSE);
        robotHardware.getRightFrontMotor().setDirection(DcMotorSimple.Direction.FORWARD);
        robotHardware.getRightBackMotor().setDirection(DcMotorSimple.Direction.FORWARD);

        setAllMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (isBusy()) {
            setAllPower(0.5);
        }
        setAllPower(0);
        setAllMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setAllMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private boolean isBusy() {
        return (robotHardware.getLeftFrontMotor().isBusy() ||
                robotHardware.getLeftFrontMotor().isBusy() ||
                robotHardware.getRightBackMotor().isBusy() ||
                robotHardware.getRightFrontMotor().isBusy())
                && !isStopRequested();
    }

    private void setAllPower(double power) {
        robotHardware.getLeftFrontMotor().setPower(power);
        robotHardware.getLeftBackMotor().setPower(power);
        robotHardware.getRightFrontMotor().setPower(power);
        robotHardware.getRightBackMotor().setPower(power);
    }

    private void setAllMode(DcMotor.RunMode mode) {
        robotHardware.getLeftFrontMotor().setMode(mode);
        robotHardware.getLeftBackMotor().setMode(mode);
        robotHardware.getRightFrontMotor().setMode(mode);
        robotHardware.getRightBackMotor().setMode(mode);
    }
}
