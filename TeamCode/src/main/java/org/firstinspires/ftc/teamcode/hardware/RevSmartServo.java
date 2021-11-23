package org.firstinspires.ftc.teamcode.hardware;


import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class RevSmartServo extends SimpleServo {
    SimpleServo simpleServo;
    // For Rev Smart Servo, assume Zero degree is position 0.
    // Rev Smart Servo has 270 degree range
    static final double REV_MIN_DEGREES = -135;
    static final double REV_MAX_DEGREES = 135;
    private double minAngleRadians, maxAngleRadians;

    public RevSmartServo(HardwareMap hardwareMap, String servoName) {
        super(hardwareMap, servoName, REV_MIN_DEGREES, REV_MAX_DEGREES);
        // v1.2.1 of FtcLib has bugs in setting min and max angles in constructors with flipping
        // min and max.
        //
        // Can work around bugs by calling setRange on SimpleServo
        // to make sure internal minAngle and maxAngle are correct.
        setRange(REV_MIN_DEGREES, REV_MAX_DEGREES, AngleUnit.DEGREES);
    }
}

