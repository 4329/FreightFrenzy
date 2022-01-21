package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
public class BucketSystem extends SubsystemBase {
    public static TelemetrySystem.TelemetryLevel TELEMETRY_LEVEL= TelemetrySystem.TelemetryLevel.MATCH;
    public static double homeEncoderPosition;
    private Telemetry telemetry;
    private double bucketHomeEncoderPosition=0;
    // public static double BUCKET_MOTOR_POWER=0.25;
    public static double BUCKET_UP_POWER=0.5;
    public static double BUCKET_DOWN_POWER = -0.5;
    public static int UP_POSITION_CHANGE = 50;

    private DcMotorEx bucketMotor;

    @Override
    public void periodic() {

        if(bucketMotor.getCurrentPosition() != bucketMotor.getTargetPosition()){
            bucketMotor.setPower(BUCKET_UP_POWER);
            bucketMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        // telemetry

        switch (TELEMETRY_LEVEL) {
            case DEBUG:
            case DIAGNOSTIC:
                telemetry.addData(this.getName()+":getCurrent",bucketMotor.getCurrent(CurrentUnit.AMPS));
                telemetry.addData(this.getName()+":getPower",bucketMotor.getPower());

            case CONFIG:
                // telemetry.addData(this.getName()+":BUCKET_MOTOR_POWER:",BUCKET_MOTOR_POWER);
                telemetry.addData(this.getName()+":getHomeEncoderPosition",this.getHomeEncoderPosition());
            case MATCH:
                // telemetry.addData(this.getName()+":Command:",this.getCurrentCommand());
                telemetry.addData(this.getName()+":getCurrentPosition",bucketMotor.getCurrentPosition());
                telemetry.addData(this.getName()+":getTargetPosition",bucketMotor.getTargetPosition());


                break;
        }

    }

    public BucketSystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        bucketMotor = hardwareMap.get(DcMotorEx.class, "bucketMotor");
        bucketMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        bucketMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bucketMotor.setTargetPosition(bucketMotor.getCurrentPosition());

        // resetMotor();
        // setHomeEncoderPosition();
    }

    public void setHomeEncoderPosition(double newHomeEncoderPosition){
        homeEncoderPosition = newHomeEncoderPosition;
    }

    public double getHomeEncoderPosition(){
        return homeEncoderPosition;
    }

    public double getEncoderPosition(){
        return  bucketMotor.getCurrentPosition() ;
    }


    public void up(){

        bucketMotor.setTargetPosition(bucketMotor.getCurrentPosition() + UP_POSITION_CHANGE);
        // bucketMotor.setPower(BUCKET_UP_POWER);
        // bucketMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // while (bucketMotor.isBusy()){}
    }

    public void down(){
        bucketMotor.setTargetPosition(bucketMotor.getCurrentPosition() - UP_POSITION_CHANGE);

        // bucketMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // bucketMotor.setPower(BUCKET_DOWN_POWER);
        // bucketMotor.setTargetPosition(bucketMotor.getCurrentPosition() +\- UP_POSITION_CHANGE);
        // bucketMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void level(){}

    public void stop(){
        bucketMotor.setTargetPosition(bucketMotor.getCurrentPosition());
        // bucketMotor.setPower(0);
        }

    public void resetMotor(){
        // Core Hex has 288 per rotation
        bucketMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bucketMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        bucketMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}
