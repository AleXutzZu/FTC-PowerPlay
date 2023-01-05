
package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;
import lombok.Getter;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class RobotHardware {
    /**
     * The OpMode that requested the hardware map
     */
    private final OpMode opMode;

    public RobotHardware(OpMode opMode) {
        this.opMode = opMode;
    }

    @Getter
    private DcMotorEx leftFrontMotor = null;
    @Getter
    private DcMotorEx leftBackMotor = null;
    @Getter
    private DcMotorEx rightFrontMotor = null;
    @Getter
    private DcMotorEx rightBackMotor = null;
    @Getter
    private DcMotorEx leftElevatorMotor = null;
    @Getter
    private DcMotorEx rightElevatorMotor = null;
    @Getter
    private Servo leftClawServo = null;
    @Getter
    private Servo rightClawServo = null;
    @Getter
    private Rev2mDistanceSensor right2mSensor = null;
    @Getter
    private Rev2mDistanceSensor left2mSensor = null;
    @Getter
    private Rev2mDistanceSensor back2mSensor = null;
    @Getter
    private BNO055IMU imu = null;
    @Getter
    private WebcamName webcam = null;


    /**
     * This is the function that we initialize the Motors with
     **/
    public void initDrivetrainMotors() {
        leftFrontMotor = opMode.hardwareMap.get(DcMotorEx.class, "left_front");
        leftBackMotor = opMode.hardwareMap.get(DcMotorEx.class, "left_back");
        rightFrontMotor = opMode.hardwareMap.get(DcMotorEx.class, "right_front");
        rightBackMotor = opMode.hardwareMap.get(DcMotorEx.class, "right_back");
        leftElevatorMotor = opMode.hardwareMap.get(DcMotorEx.class, "left_elevator");
        rightElevatorMotor = opMode.hardwareMap.get(DcMotorEx.class, "right_elevator");

        leftClawServo = opMode.hardwareMap.get(Servo.class, "left_claw");
        rightClawServo = opMode.hardwareMap.get(Servo.class, "right_claw");

        leftClawServo.setDirection(Servo.Direction.REVERSE);

        //Approx 30 deg - 120 deg
        leftClawServo.scaleRange(1f / 60f, 1f / 15f);
        rightClawServo.scaleRange(1f / 60f, 1f / 15f);

        //TODO: Update the class documentation to contain the servos and test if any of them should be reversed

        leftFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftElevatorMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightElevatorMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftElevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightElevatorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftElevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightElevatorMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftElevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightElevatorMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * This is the function that we initialize the Sensors with
     */
    public void initSensors() {
        left2mSensor = (Rev2mDistanceSensor) opMode.hardwareMap.get(DistanceSensor.class, "left_2m");
        right2mSensor = (Rev2mDistanceSensor) opMode.hardwareMap.get(DistanceSensor.class, "right_2m");
        back2mSensor = (Rev2mDistanceSensor) opMode.hardwareMap.get(DistanceSensor.class, "back_2m");
    }

    /**
     * Initializes the Inertial Measurement Unit found in the Rev Expansion Hub
     */
    public void initIMU() {
        imu = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        //TODO Remap axis if the orientation of the hub using the IMU is not placed with the +Z upwards
    }

    /**
     * Initialize all required hardware for the Autonomous Period
     */
    public void initAutonomous() {
        initDrivetrainMotors();
        initIMU();
        initSensors();
        initWebcam();
    }

    /**
     * Initialize all required hardware for the Tele Op Period
     */
    public void initTeleOp() {
        initDrivetrainMotors();
    }

    public void initWebcam() {
        webcam = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");
    }
}
