package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.MatchTeleop;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.hardware.RevSmartServo;

import java.text.MessageFormat;

public class TubeSpinnerSystem extends SubsystemBase {
    static double DEFAULT_HOME_DEGREES=-125.0;
    static double CARRY_DEGREES=80.0;
    private RevSmartServo revSmartServo;
    // homeDegrees will be the tube position pointing down for pickup
    private double homeDegrees=DEFAULT_HOME_DEGREES;
    // drivingDegrees will be the tube position for driving. Expect default to changed by config
    private double drivingDegrees=90;


    public TubeSpinnerSystem(HardwareMap hw, String servoName,double homeDegrees) {
        this.homeDegrees=homeDegrees;
        //Rev Robotics Smart Servo
        revSmartServo = new RevSmartServo(hw, servoName);
        // turn to home degrees so that we know where Servo starts
        // revSmartServo.turnToAngle(homeDegrees);
        // moveTubeToHome(); // Need to move servo to known position so that we can track angles
    }

    public TubeSpinnerSystem(HardwareMap hardwareMap,double homeDegree) {
        this(hardwareMap, "TubeSpinnerServo",homeDegree);

    }

    public TubeSpinnerSystem(HardwareMap hardwareMap){
        this(hardwareMap,DEFAULT_HOME_DEGREES);
    }

    public void rotateTube(double degreeToRotate) {
        revSmartServo.rotateByAngle(degreeToRotate, AngleUnit.DEGREES);

    }

    public double getHomeDegrees() {
        return homeDegrees;
    }

    public void setHomeDegrees(double homeDegrees) {
        this.homeDegrees = homeDegrees;
    }

    public void moveTubeToHome(){
        revSmartServo.turnToAngle(homeDegrees);
    }
    public void moveTubeForDriving(){
        revSmartServo.turnToAngle(drivingDegrees);
    }

    public String getTelemetry(){
        return MessageFormat.format(
                "TubeSpinner:(Degrees={0}), (Servo Position={1}), (tubeSpinnerHomeDegrees={2})",
                this.getDegrees(),
                this.getPosition(),
                this.getHomeDegrees()
        );
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

    public void moveToCarry(){
        setDegree(getHomeDegrees() + CARRY_DEGREES);
    }

    public void moveToHome(){
        setDegree(homeDegrees);
    }

}
