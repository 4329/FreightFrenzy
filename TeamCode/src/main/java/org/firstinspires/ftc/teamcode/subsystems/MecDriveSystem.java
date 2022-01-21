package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@Config
public class MecDriveSystem extends SubsystemBase {
    public static TelemetrySystem.TelemetryLevel telemetryLevel = TelemetrySystem.TelemetryLevel.MATCH;
    public static boolean driveFieldCentric = false;

    private Telemetry telemetry;
    private IMUSystem imuSystem;
    private MecanumDrive mecanumDrive;
    private Motor leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive;
    public double rightFrontEncoderPosition;
    private Motor.Encoder rightFrontEncoder;

    private final int WHEEL_DIAMETER_IN_MMs = 76;
    private final int COUNTS_PER_REVOLUTION = 20 * 28;  // Gear Ration * Counts per revolution at motor
    private final double distanceMMPerRevolution = (WHEEL_DIAMETER_IN_MMs * Math.PI);
    private final double distanceInchPerRevolution= distanceMMPerRevolution/ 25.4;
    public final double distanceInchPerPulse = distanceInchPerRevolution / COUNTS_PER_REVOLUTION;



    // Translation2d frontLeftLocation= new Translation2d(0.381);


    @Override
    public void periodic() {
        switch (telemetryLevel) {
            case DEBUG:
                telemetry.addData(this.getName() + ":distanceInchPerRevolution",distanceInchPerRevolution);
                telemetry.addData(this.getName() + ":distanceInchPerRevolution",distanceInchPerRevolution);
            case DIAGNOSTIC:
                telemetry.addData(this.getName() + ":rightFrontDrive.getCurrentPosition",rightFrontDrive.getCurrentPosition());
            case CONFIG:

            case MATCH:
                telemetry.addData(this.getName() + ":driveFieldCentric=", driveFieldCentric);
                telemetry.addData(this.getName() + ":heading=",
                        Math.round(this.imuSystem.heading() * 1000) / 1000);

        }
    }

    public MecDriveSystem(HardwareMap hardwareMap, IMUSystem imuSystem, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.imuSystem = imuSystem;
        leftFrontDrive = new Motor(hardwareMap, "LeftFrontDrive");
        rightFrontDrive = new Motor(hardwareMap, "RightFrontDrive");
        leftBackDrive = new Motor(hardwareMap, "LeftBackDrive");
        rightBackDrive = new Motor(hardwareMap, "RightBackDrive");
        rightFrontDrive.encoder.setDistancePerPulse(distanceInchPerPulse);
        leftFrontDrive.encoder.setDistancePerPulse(distanceInchPerPulse);
        rightBackDrive.encoder.setDistancePerPulse(distanceInchPerPulse);
        leftBackDrive.encoder.setDistancePerPulse(distanceInchPerPulse);






        leftFrontDrive.motor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.motor.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.motor.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.motor.setDirection(DcMotor.Direction.REVERSE);
        mecanumDrive = new MecanumDrive(leftFrontDrive, rightFrontDrive, leftBackDrive, rightBackDrive);


    }

    public void Drive(double forward, double turn, double strafe) {

        if (driveFieldCentric) {
            // strafe, forward, turn
            mecanumDrive.driveFieldCentric(-strafe, forward, -turn, -imuSystem.heading(), false);
        } else {
            //
            mecanumDrive.driveRobotCentric(-strafe, forward, -turn, false);

            //mecanumDrive.driveRobotCentric(strafe,-turn, forward);
        }
    }

    public void enableFieldCentric(){
        driveFieldCentric=true;
    }

    public void disableFieldCentric(){
        driveFieldCentric=false;
    }


}
