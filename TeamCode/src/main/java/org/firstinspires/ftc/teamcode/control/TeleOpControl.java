package org.firstinspires.ftc.teamcode.control;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.RobotHardware;

/**
 * This class provides enhanced functionality for TeleOp.
 * All the important parts are already initialized (gamepads and hardware).
 * You can skip the start and init phases and jump straight into the running phase. 2 debug buttons are provided for the
 * 2 controllers, and you can assign commands to them. They act as toggle buttons. Everything that happens during the OpMode
 * is in the {@link TeleOpControl#run()} method and this is the only method you have to implement.
 * Telemetry, gamepads and debug keys are automatically updated unless stated otherwise.
 * {@link Telemetry#update()} is called in the main loop after all method calls. That includes {@link TeleOpControl#run()},
 * {@link TeleOpControl#debugMainGamepad()} and {@link  TeleOpControl#debugSecondaryGamepad()}. Only after the execution of
 * these 3 methods is the telemetry updated.
 */
public abstract class TeleOpControl extends LinearOpMode implements Limbs, Drivetrain {
    /**
     * Main gamepad or the "driver gamepad". Assigned by default to gamepad1 of the LinearOpMode.
     *
     * @see TeleOpControl#invertedGamepads
     */
    protected GamepadEx mainGamepad;
    /**
     * Secondary gamepad usually for things that are not driving related.
     * Assigned by default to gamepad2 of the LinearOpMode
     *
     * @see TeleOpControl#invertedGamepads
     */
    protected GamepadEx secondaryGamepad;

    /**
     * Should gamepad1 and gamepad2 be reverted? Set to true to invert the roles.
     *
     * @see TeleOpControl#mainGamepad
     * @see TeleOpControl#secondaryGamepad
     */
    protected boolean invertedGamepads = false;

    /**
     * Skip initialization and instantly call {@link LinearOpMode#waitForStart()} after {@link TeleOpControl} internal
     * initialization is complete. Defaults to true.
     */
    protected boolean skipInit = true;

    /**
     * Skip the start phase and instantly go to the looping body of {@link LinearOpMode}.
     * {@link TeleOpControl#enhanced_start()} will not be called if set to true. Defaults to true.
     */
    protected boolean skipStart = true;

    /**
     * The robot hardware. This is the only hardware that is initialized by default.
     */
    protected final RobotHardware robotHardware = new RobotHardware(this);

    @Override
    public final void runOpMode() throws InterruptedException {
        robotHardware.initTeleOp();
        mainGamepad = new GamepadEx(gamepad1);
        secondaryGamepad = new GamepadEx(gamepad2);
        if (invertedGamepads) {
            GamepadEx aux = secondaryGamepad;
            secondaryGamepad = mainGamepad;
            mainGamepad = aux;
        }

        ToggleButtonReader mainDebugKey = new ToggleButtonReader(mainGamepad, GamepadKeys.Button.Y);
        ToggleButtonReader secondaryDebugKey = new ToggleButtonReader(secondaryGamepad, GamepadKeys.Button.Y);

        if (skipInit) waitForStart();
        else {
            enhanced_init();
            telemetry.update();
            while (opModeInInit()) {
                enhanced_init_loop();
                telemetry.update();
            }
        }

        if (!skipStart) {
            if (!isStopRequested()) enhanced_start();
        }

        while (opModeIsActive()) {
            run();

            if (mainDebugKey.getState()) {
                debugMainGamepad();
            }

            if (secondaryDebugKey.getState()) {
                debugSecondaryGamepad();
            }

            //Updates
            telemetry.update();
            mainGamepad.readButtons();
            secondaryGamepad.readButtons();
            mainDebugKey.readValue();
            secondaryDebugKey.readValue();
        }

        if (isStopRequested()) {
            enhanced_stop();
        }
    }

    /**
     * This method can be overwritten to provide functionality to the debug key on the main gamepad (Y key)
     */
    protected void debugMainGamepad() {

    }

    /**
     * This method can be overwritten to provide functionality to the debug key on the secondary gamepad (Y key)
     */
    protected void debugSecondaryGamepad() {

    }

    /**
     * Override this method to execute initialization code ONCE. Make sure that {@link TeleOpControl#skipInit} is false
     * otherwise this method will never be called.
     * A call to {@link Telemetry#update()} will be made after this method is called.
     */
    protected void enhanced_init() {

    }

    /**
     * Override this method to execute initialization code repeatedly until START is played. Make sure that {@link TeleOpControl#skipInit} is false
     * otherwise this method will never be called.
     * A call to {@link Telemetry#update()} will be made in the while-loop after each call to this method.
     */
    protected void enhanced_init_loop() {

    }

    /**
     * Override this method to execute code ONCE when the opmode is to shut down.
     * Keep in mind this time you will have to call {@link Telemetry#update()} as this is no longer covered by {@link TeleOpControl}.
     */
    protected void enhanced_stop() {

    }

    /**
     * Override this method to execute code ONCE when the OpMode START button is pressed. Keep in mind this time you
     * will have to call {@link Telemetry#update()} as this is no longer covered by {@link TeleOpControl}.
     * For this method to be called make sure that {@link TeleOpControl#skipStart} is set to false.
     */
    protected void enhanced_start() {

    }

    /**
     * Actual body of the OpMode. This will be repeatedly run until STOP is pressed or a crash occurs.
     * In each iteration this method is called before the debug methods are called.
     */
    protected abstract void run();

    //TODO: Bretan - implement this method.
    //Hints: Add a private state boolean for the claws (named clawsState) to this class.
    //Add a getter for this state
    //Now implement the useClaws method to make use of that state and update it internally
    //At the initialization of the robot,set the claws to the open position for now (and update the state)
    //Use true to mark that the claws are open and false to mark that the claws are closed
    //Make sure to read the docs and return true of false as it says in the docs
    @Override
    public boolean useClaws() {
        return false;
    }

    //TODO: Alex & Luca - implement this method after measuring the required values
    @Override
    public void useElevator(ElevatorLevel level) {

    }

    //TODO: Alex - implement this method
    @Override
    public void drive(double axial, double lateral, double yaw) {

    }
}
