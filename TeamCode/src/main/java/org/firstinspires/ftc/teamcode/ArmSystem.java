package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmSystem extends SubsystemBase {
    private DcMotor ArmMotorleft = null;
    private DcMotor ArmMotorright= null;
    private AnalogInput armPot=null;
    private final double voltageAtZeroDegree=0.27;//voltage when arm is resting on ground
    private final double voltageAt90Degree=0.957;
    private final double voltsPerDegree = (voltageAt90Degree-voltageAtZeroDegree)/90;

    public ArmSystem(HardwareMap hardwareMap){
    ArmMotorleft=hardwareMap.get(DcMotor.class,"arm_left");
    ArmMotorright=hardwareMap.get(DcMotor.class,"arm_right");
    ArmMotorleft.setDirection(DcMotor.Direction.FORWARD);
    ArmMotorright.setDirection(DcMotor.Direction.FORWARD);
    armPot=hardwareMap.get(AnalogInput.class, "arm_pot");

    ArmMotorleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    ArmMotorright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    ArmMotorright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    ArmMotorleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }


    public void GoToAngle(double GoToAngle){
        double upPower=1;
        double downPower=-.1;
        double holdPower=.01;

        while (GetAngle()<GoToAngle){
            ArmMotorright.setPower(upPower);
            ArmMotorleft.setPower(upPower);
        }
        ArmMotorleft.setPower(holdPower);
        ArmMotorright.setPower(holdPower);


    }
    public double GetAngle(){

        return (armPot.getVoltage()-voltageAtZeroDegree)/voltsPerDegree;
    }
    public void Stop(){
        ArmMotorleft.setPower(0);
        ArmMotorright.setPower(0);
    }
}
