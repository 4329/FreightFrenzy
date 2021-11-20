package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class TubeSpinnerSystem extends SubsystemBase {
    private ServoEx servo;

    public TubeSpinnerSystem(HardwareMap hw, String servoName){
            //Rev Robotics Smart Servo

            servo=new SimpleServo(hw, servoName,
                    -135,135, AngleUnit.DEGREES);

    }
    public double getPosition(){
        return servo.getPosition();
    }

    public void movePosition(double positionToMove){
        servo.setPosition(servo.getPosition() + positionToMove);
    }

    public void turnTubeToAngle(double angle){
        servo.turnToAngle(angle,AngleUnit.DEGREES);
    }

    public void rotate(double position){
        servo.rotateBy(position);
    }

}
