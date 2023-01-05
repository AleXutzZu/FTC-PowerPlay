package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
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
    private WebcamName webcam = null;

    public void initWebcam() {
        webcam = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");
    }

    public void initAutonomous() {
        initWebcam();
    }
}
