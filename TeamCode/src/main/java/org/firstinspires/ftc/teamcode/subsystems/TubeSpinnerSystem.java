package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.hardware.RevSmartServo;

public class TubeSpinnerSystem extends SubsystemBase {
    private RevSmartServo revSmartServo;


    public TubeSpinnerSystem(HardwareMap hw, String servoName,double homeDegrees) {
        //Rev Robotics Smart Servo
        revSmartServo = new RevSmartServo(hw, servoName);
        // turn to home degrees so that we know where Servo starts
        revSmartServo.turnToAngle(homeDegrees);
    }

    public TubeSpinnerSystem(HardwareMap hardwareMap,double homeDegree) {
        this(hardwareMap, "TubeSpinnerServo",homeDegree);
    }

    public TubeSpinnerSystem(HardwareMap hardwareMap){
        this(hardwareMap,0);
    }

    public void rotateTube(double degreeToRotate) {
        revSmartServo.rotateByAngle(degreeToRotate, AngleUnit.DEGREES);

    }

    public double getPosition() {
        return revSmartServo.getPosition();
    }

    public double getDegrees() {
        return revSmartServo.getAngle(AngleUnit.DEGREES);
    }

    public double getDegreeRange() {
        return revSmartServo.getAngleRange(AngleUnit.DEGREES);
    }

    public void setPosition(double position) {
        revSmartServo.setPosition(position);
    }

    public void setDegree(double degree) {
        revSmartServo.turnToAngle(degree, AngleUnit.DEGREES);
    }

    public void rotate(double position) {
        revSmartServo.rotateBy(position);
    }

}
