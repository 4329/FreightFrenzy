package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.util.Locale;

@Config
public class IMUSystem extends SubsystemBase {
    public static TelemetrySystem.TelemetryLevel telemetryLevel = TelemetrySystem.TelemetryLevel.MATCH;
    public static boolean resetIMU = false;
    public static double zeroHeadingOffset= 0.0;
    private static String CALIBRATION_FILE = "BNO055IMUCalibration.json";

    private Telemetry telemetry;
    BNO055IMU imu;
    // State used for updating telemetry
    Orientation angles;
    Acceleration gravity;

    public IMUSystem(HardwareMap hardwareMap, Telemetry telemetry) {
        imu = hardwareMap.get(BNO055IMU.class, "imu2");
        this.telemetry = telemetry;
        initializeIMU();
        startIMU();
        //enableCalibration();
    }

    private void startIMU() {
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
    }

    private void stopIMU(){
        imu.stopAccelerationIntegration();
    }
    public void resetIMU() {
        initializeIMU();
        resetIMU = false;
    }

    public boolean isCalibrated(){
        return  imu.isGyroCalibrated();
    }
    public void enableCalibration() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        // imu.close();
        imu.initialize(parameters);
    }

    public void saveCalibration() {
        BNO055IMU.CalibrationData calibrationData = imu.readCalibrationData();
        File file = AppUtil.getInstance().getSettingsFile(CALIBRATION_FILE);
        ReadWriteFile.writeFile(file, calibrationData.serialize());
        telemetry.log().add("Calibration saved to '%s'", CALIBRATION_FILE);

    }

    public void initializeIMU() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = CALIBRATION_FILE;
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);
    }

    public Orientation getAngles() {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
    }

    public double heading() {
        float headingAngle=angles.firstAngle;
        if (headingAngle<=0)
            return (Math.abs(headingAngle)+zeroHeadingOffset)%360.0 ;
        else
            return ((360-headingAngle)+zeroHeadingOffset)%360.0;
    }

    @Override
    public void periodic() {
        if (resetIMU) resetIMU();
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        gravity =imu.getGravity();
        switch (telemetryLevel) {
            case DEBUG:
            case DIAGNOSTIC:
                telemetry.addData(this.getName() + ":Z", formatAngle(angles.angleUnit, angles.firstAngle));
                telemetry.addData(this.getName() + ":X", formatAngle(angles.angleUnit, angles.secondAngle));
                telemetry.addData(this.getName() + ":Y", formatAngle(angles.angleUnit, angles.thirdAngle));
                telemetry.addData(this.getName() + ":gravity", gravity.toString());
                telemetry.addData(this.getName() + ":isAccelerometerCalibrated", imu.isAccelerometerCalibrated());
                telemetry.addData(this.getName() + ":isGyroCalibrated", imu.isGyroCalibrated());
                telemetry.addData(this.getName() + ":isSystemCalibrated", imu.isSystemCalibrated());
                telemetry.addData(this.getName() + ":isMagnetometerCalibrated", imu.isMagnetometerCalibrated());

                telemetry.addData(this.getName() + ":imuCalibrationStatus", imu.getCalibrationStatus());
                telemetry.addData(this.getName() + ":imuSystemStatus", imu.getSystemStatus());
            case CONFIG:
            case MATCH:
                telemetry.addData(this.getName() + ":Heading",
                        String.format("%.2f",heading()));
                telemetry.addData(this.getName() + ":gyro",
                        String.format("%.2f",angles.firstAngle));

                telemetry.addData(this.getName() + ":zeroHeadingOffset",
                        String.format("%.2f",zeroHeadingOffset));
                telemetry.addData(this.getName() + ":isCalibrated",imu.isGyroCalibrated());

                // telemetry.addData(this.getName() + ":CurrentCommand", this.getCurrentCommand());
                break;
        }
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

    public void zeroHeading(){
        zeroHeadingOffset=0;
        zeroHeadingOffset = 360-heading();
    }
}
