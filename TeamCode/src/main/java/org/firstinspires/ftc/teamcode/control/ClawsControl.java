package org.firstinspires.ftc.teamcode.control;

/**
 * Every claw's implementor is provided with this method.
 */
public interface ClawsControl {
    /**
     * Invokes the claws to close or open
     *
     * @return true if the claws opened or false if the claws closed
     */
    boolean useClaws();
}
