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
    private int homeEncoderPosition=0;
    private Telemetry telemetry;
    private double bucketHomeEncoderPosition=0;
    // public static double BUCKET_MOTOR_POWER=0.25;
    private static double BUCKET_UP_POWER=0.5;
    private static int UP_POSITION_CHANGE = 50;

    private DcMotorEx bucketMotor;
    private final int ENCODERPositionsPerMotorRotation=28*2;
    private final int GEARRatio =75;
    private final double POSITIONS_PER_DEGREE = (ENCODERPositionsPerMotorRotation*GEARRatio) /360.0;
    public boolean atPosition =false;



    @Override
    public void periodic() {

        if(bucketMotor.getCurrentPosition() != bucketMotor.getTargetPosition()){
            atPosition =false;
            // If not currently at position, then tell motor PID to run to position
            bucketMotor.setPower(BUCKET_UP_POWER);
            bucketMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else {
            atPosition =true;
        }

        // telemetry

        switch (TELEMETRY_LEVEL) {
            case DEBUG:
            case DIAGNOSTIC:
                telemetry.addData(this.getName()+":getCurrent",bucketMotor.getCurrent(CurrentUnit.AMPS));
                telemetry.addData(this.getName()+":getPower",bucketMotor.getPower());
                telemetry.addData(this.getName()+":getCurrentPosition",bucketMotor.getCurrentPosition());
                telemetry.addData(this.getName()+":getTargetPosition",bucketMotor.getTargetPosition());
                telemetry.addData(this.getName()+":bucketAngle",this.getBucketAngleFromHomePosition());
                telemetry.addData(this.getName()+":atPosition",this.atPosition);
                telemetry.addData(this.getName()+":POSITIONS_PER_DEGREE",this.POSITIONS_PER_DEGREE);
            case CONFIG:
                // telemetry.addData(this.getName()+":BUCKET_MOTOR_POWER:",BUCKET_MOTOR_POWER);
                telemetry.addData(this.getName()+":getHomeEncoderPosition",this.getHomeEncoderPosition());
            case MATCH:
                break;
        }

    }

    public BucketSystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        bucketMotor = hardwareMap.get(DcMotorEx.class, "bucketMotor");
        bucketMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        bucketMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bucketMotor.setTargetPosition(bucketMotor.getCurrentPosition());
        setHomeEncoderPosition(bucketMotor.getCurrentPosition());
    }

    public double getBucketAngleFromHomePosition(){
        int deltaPositionFromHome = bucketMotor.getCurrentPosition()- homeEncoderPosition;
        return deltaPositionFromHome/ POSITIONS_PER_DEGREE;
    }

    public void  setBucketAngleFromHomePosition(double bucketAngleToSet){
        // Assumes zero degrees for home
        int positionForAngle = (int) (bucketAngleToSet*POSITIONS_PER_DEGREE)+ homeEncoderPosition;
        bucketMotor.setTargetPosition(positionForAngle);
    }

    public void setHomeEncoderPosition(int homeEncoderPosition){
        this.homeEncoderPosition = homeEncoderPosition;
    }

    public double getHomeEncoderPosition(){
        return homeEncoderPosition;
    }

    public int getEncoderPosition(){
        return  bucketMotor.getCurrentPosition() ;
    }


    public void up(){

        bucketMotor.setTargetPosition(bucketMotor.getCurrentPosition() + UP_POSITION_CHANGE);
    }

    public void down(){
        bucketMotor.setTargetPosition(bucketMotor.getCurrentPosition() - UP_POSITION_CHANGE);
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
