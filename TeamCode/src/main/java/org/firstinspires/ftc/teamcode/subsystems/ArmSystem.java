package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmSystem extends SubsystemBase {
    private DcMotor armMotorLeft = null;
    private DcMotor armMotorRight = null;
    private AnalogInput armPot=null;
    private final double voltageAtZeroDegree=0.27;//voltage when arm is resting on ground
    private final double voltageAt90Degree=0.957;
    private final double voltsPerDegree = (voltageAt90Degree-voltageAtZeroDegree)/90;

    public ArmSystem(HardwareMap hardwareMap,String LeftMotorName, String RightMotorName, String PotName){
    armMotorLeft =hardwareMap.get(DcMotor.class,LeftMotorName);
    armMotorRight =hardwareMap.get(DcMotor.class,RightMotorName);
    armMotorLeft.setDirection(DcMotor.Direction.FORWARD);
    armMotorRight.setDirection(DcMotor.Direction.FORWARD);
    armPot=hardwareMap.get(AnalogInput.class,PotName);

    armMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    armMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    armMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    armMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double Power){
        armMotorLeft.setPower(Power);
        armMotorRight.setPower(Power);
    }


    public void Stop(){
        armMotorLeft.setPower(0);
        armMotorRight.setPower(0);
    }
}
