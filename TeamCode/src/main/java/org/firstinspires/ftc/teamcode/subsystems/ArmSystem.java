package org.firstinspires.ftc.teamcode.subsystems;

import android.os.Environment;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

public class ArmSystem extends SubsystemBase {
    private DcMotor armMotorLeft = null;
    private DcMotor armMotorRight = null;
    private AnalogInput armPot = null;
    private final double voltageAtZeroDegree = 0.27;//voltage when arm is resting on ground
    private final double voltageAt90Degree = 0.957;
    private final double voltsPerDegree = (voltageAt90Degree - voltageAtZeroDegree) / 90;
    private double armHomeDegrees=-99;
    private File configFile = new File("armSystem.properties");
    private Exception lastException;
    private Boolean isSettingsLoaded=false;

    public ArmSystem(HardwareMap hardwareMap,
                     String LeftMotorName,
                     String RightMotorName,
                     String PotName) {
        armMotorLeft = hardwareMap.get(DcMotor.class, LeftMotorName);
        armMotorRight = hardwareMap.get(DcMotor.class, RightMotorName);
        armMotorLeft.setDirection(DcMotor.Direction.REVERSE);
        armMotorRight.setDirection(DcMotor.Direction.REVERSE);
        armPot = hardwareMap.get(AnalogInput.class, PotName);

        armMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.isSettingsLoaded= loadSettings();
    }

    public ArmSystem(HardwareMap hardwareMap) {
        this(hardwareMap, "ArmLeftMotor",
                "ArmRightMotor",
                "ArmPot");
    }

    public void setPower(double Power) {
        armMotorLeft.setPower(Power);
        armMotorRight.setPower(Power);
    }


    public void Stop() {
        armMotorLeft.setPower(0);
        armMotorRight.setPower(0);

    }

    public String getTelemetry() {
        String exMessage;
        if (lastException != null) {
            exMessage = lastException.getMessage();
        } else {
            exMessage = "";
        }
        return MessageFormat.format(
                "ArmSystem:(ArmPot={0}), (PotVolts={1}), (armHomeDegrees={2}),(ex={3}),(settings={4})",
                this.getArmDegrees(),
                armPot.getVoltage(),
                this.armHomeDegrees,
                exMessage,
                this.isSettingsLoaded);
    }

    ;

    public Double getArmDegrees() {
        // Formula for linear potentiometer
        return armPot.getVoltage() * (270 / 3.3);
    }

    public void setHomeArmDegrees() {
        // When called, set the armHomeDegrees and update settings
        this.armHomeDegrees = this.getArmDegrees();
    }

    private Boolean loadSettings() {
        File configFile = new File(Environment.getExternalStorageDirectory(), "armSystem.properties");
        // File configFile = new File("/mnt/sdcard/robot/armSystem.txt");
        this.armHomeDegrees=-50.0;
        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            this.armHomeDegrees = Double.valueOf(props.getProperty("armHomeDegrees"));
            // this.armHomeDegrees=Double.valueOf("46.00");
            reader.close();
            return true;
        } catch (FileNotFoundException ex) {
            // File does not exist -
            this.armHomeDegrees = Double.NaN;
            lastException = ex;
            return false;
        } catch (IOException ex) {
            this.armHomeDegrees = Double.NaN;
            lastException = ex;
            return false;
        }

    }

    public Boolean saveSettings() {
        setHomeArmDegrees();
        File configFile = new File(Environment.getExternalStorageDirectory(), "armSystem.properties");
        // File configFile = new File("/sdcard/Robot/armSystem.properties");
        try {
            Properties props = new Properties();
            props.setProperty("armHomeDegrees", Double.toString(this.armHomeDegrees));
            // props.setProperty("armHomeDegrees", "90.0");
            FileWriter writer = new FileWriter(configFile);
            props.store(writer, "ArmSystem Settings");
            writer.close();
            return true;
        } catch (IOException ex) {
            // IOException
            lastException = ex;
            return false;
        }

    }


}
