package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.hardware.RevSmartServo;

public class CheeseKickerSystem extends SubsystemBase {
    static double HOLD_DEGREES=-90;
    static double KICK_DEGREES =90;
    static double HOLD_POSITION = 0.73;
    static double KICK_POSITION = 0.0;
    static double HOME_DEGREES = 0;
    private RevSmartServo kickerServo;

    public CheeseKickerSystem(HardwareMap hardwareMap) {
        kickerServo = new RevSmartServo(hardwareMap,"CheeseKickerServo");
        holdCheese();
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
        this.setPosition(HOLD_POSITION);
    }

    public void kickCheese(){
        // ToDo - Need to figure out kick degrees
        this.setPosition(KICK_POSITION);
    }

}
