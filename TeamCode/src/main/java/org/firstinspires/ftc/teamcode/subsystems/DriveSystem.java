package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveSystem extends SubsystemBase {
    private DcMotorEx leftFrontDrive = null;
    private DcMotorEx rightFrontDrive = null;
    private DcMotorEx leftBackDrive = null;
    private DcMotorEx rightBackDrive = null;
    private final double wheelDiameterInches = 75.0/26.5;
    private final double encoderCountsPerRevolution = 28;
    private final double encoderPositionsPerRevolution = encoderCountsPerRevolution * 4;
    private Telemetry telemetry;

    public  DriveSystem(HardwareMap hardwareMap){
        leftFrontDrive = hardwareMap.get(DcMotorEx.class, "LeftFrontDrive");
        rightFrontDrive = hardwareMap.get(DcMotorEx.class, "RightFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotorEx.class, "LeftBackDrive");
        rightBackDrive = hardwareMap.get(DcMotorEx.class, "RightBackDrive");
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);


        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void Drive(double drive, double turn,double strafe){
        double leftFrontPower = Range.clip(strafe+drive+turn,-1,+1);
        double leftBackPower = Range.clip(-strafe+drive+turn,-1,+1);
        double rightFrontPower = Range.clip(-strafe+drive-turn,-1,+1);
        double rightBackPower = Range.clip(strafe+drive-turn,-1,+1);
        // Send calculated power to wheels
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);
    }

    public double getRightFrontEncoder(){
        return Math.abs(rightFrontDrive.getCurrentPosition());
    }
    @Override
    public void periodic(){

    }

    public void resetEncoders(){
    }
}
