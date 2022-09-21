package org.firstinspires.ftc.teamcode.hardware.internal;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class GOBILDA435RPMMotor extends MotorEx {

    public GOBILDA435RPMMotor(HardwareMap hardwareMap, String motorName) {
        super(hardwareMap, motorName, GoBILDA.RPM_435);
    }
}
