package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.hardware.RevSmartServo;

public class CheeseKickerSystem extends SubsystemBase {
    static double HOLD_DEGREES=-90;
    static double KICK_DEGREES =90;
    private RevSmartServo kickerServo;

    public CheeseKickerSystem(HardwareMap hardwareMap,String kickerServoName) {
        kickerServo = new RevSmartServo(hardwareMap,kickerServoName);
    }

    public void setPosition(double position){
        kickerServo.setPosition(position);
    }

    public void setDegree(double degree){
        kickerServo.rotateByAngle(degree, AngleUnit.DEGREES);
    }

    public double getDegree(){
        return  kickerServo.getAngle(AngleUnit.DEGREES);
    }

    public void  holdCheese(){
        // ToDo - Need to figure out hold degrees
        this.setDegree(HOLD_DEGREES);
    }

    public void kickCheese(){
        // ToDo - Need to figure out kick degrees
        this.setDegree(KICK_DEGREES);
    }

}
