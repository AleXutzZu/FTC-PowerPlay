package org.firstinspires.ftc.teamcode.hardware;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.teamcode.hardware.internal.GOBILDA312RPMMotor;

import lombok.Getter;

/**
 * <h1>This class can be used to define all the specific hardware for the Perpetuum Mobile Robot.</h1>
 * This is NOT an OpMode.
 * <br>
 * <p>Used Motors (driving & lift): <a href="https://www.gobilda.com/yellow-jacket-planetary-gear-motors/">5203 Series goBILDA Planetary Gear Motors</a></p>
 * <h2>Motors</h2>
 * <h3>Motors for driving the robot</h3>
 * <pre>Right front motor:                                  <i>"right_front"</i></pre>
 * <pre>Right back motor:                                   <i>"right_back"</i></pre>
 * <pre>Left front motor:                                   <i>"left_front"</i></pre>
 * <pre>Left back motor:                                    <i>"right_back"</i></pre>
 * <h3>Motor for using the elevator</h3>
 * <pre>Elevator motor:                                 <i>"elevator_motor"</i></pre>
 * <br>
 * <h2>Sensors and misc</h2>
 * <h3>2M Distance Sensors</h3>
 * <pre>Left side sensor                                    <i>"left_2m"</i></pre>
 * <pre>Right side sensor                                   <i>"right_2m</i></pre>
 * <pre>Back sensor                                         <i>"back_2m</i></pre>
 * <h3>Misc</h3>
 * <pre>BNO55IMU Gyroscope                                  <i>"imu"</i></pre>
 */
public class RobotHardware {
    /**
     * The OpMode that requested the hardware map
     */
    private final OpMode opMode;

    public RobotHardware(OpMode opMode) {
        this.opMode = opMode;
    }

    @Getter
    private GOBILDA312RPMMotor leftFrontMotor = null;
    @Getter
    private GOBILDA312RPMMotor leftBackMotor = null;
    @Getter
    private GOBILDA312RPMMotor rightFrontMotor = null;
    @Getter
    private GOBILDA312RPMMotor rightBackMotor = null;
    @Getter
    private Rev2mDistanceSensor right2mSensor = null;
    @Getter
    private Rev2mDistanceSensor left2mSensor = null;
    @Getter
    private Rev2mDistanceSensor back2mSensor = null;
    @Getter
    private BNO055IMU imu = null;

    /**
     * This is the function that we initialize the Motors with
     **/
    public void initDrivetrainMotors() {
        leftFrontMotor = new GOBILDA312RPMMotor(opMode.hardwareMap, "left_front");
        leftBackMotor = new GOBILDA312RPMMotor(opMode.hardwareMap, "left_back");
        rightFrontMotor = new GOBILDA312RPMMotor(opMode.hardwareMap, "right_front");
        rightBackMotor = new GOBILDA312RPMMotor(opMode.hardwareMap, "right_back");


        leftFrontMotor.setInverted(true);
        leftBackMotor.setInverted(true);

        leftFrontMotor.setRunMode(Motor.RunMode.PositionControl);
        rightFrontMotor.setRunMode(Motor.RunMode.PositionControl);
        leftBackMotor.setRunMode(Motor.RunMode.PositionControl);
        rightBackMotor.setRunMode(Motor.RunMode.PositionControl);

        leftFrontMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
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
    }

    /**
     * Initialize all required hardware for the Tele Op Period
     */
    public void initTeleOp() {
        initDrivetrainMotors();
    }
}