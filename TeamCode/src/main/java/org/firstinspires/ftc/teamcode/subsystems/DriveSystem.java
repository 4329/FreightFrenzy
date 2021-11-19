package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class DriveSystem extends SubsystemBase {
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private final double wheelDiamenterInches = 3.0;

    public  DriveSystem(HardwareMap hardwareMap){
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void Drive(float drive, float turn,float strafe){
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

    public void DriveByDistance(float drive,float distanceInches){

        double wheelRotations=  distanceInches / (wheelDiamenterInches * Math.PI);
        int startingEncoderPosition =  leftBackDrive.getCurrentPosition();

        while (leftBackDrive.getCurrentPosition()< startingEncoderPosition+500){
            Drive(drive,0,0);
        }
        Drive(0,0,0);
    }

    @Override
    public void periodic(){

    }
}
