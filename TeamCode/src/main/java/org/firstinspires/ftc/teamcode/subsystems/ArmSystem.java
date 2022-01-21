package org.firstinspires.ftc.teamcode.subsystems;

import android.os.Environment;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PController;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

@Config
public class ArmSystem extends SubsystemBase {
    public static TelemetrySystem.TelemetryLevel TELEMETRY_LEVEL = TelemetrySystem.TelemetryLevel.MATCH;

    public static double PICKUP_DEGREES = 20.0;
    public double MAX_ARM_DEGREES = 55.0;
    public static double LEVEL_1_DEGREES = 10;
    public static double LEVEL_2_DEGREES = 30;
    public static double LEVEL_3_DEGREES = 50;
    public static double MAX_ARM_POWER = 0.75;
    public static double kP = 0.05;
    public static double kI= 0.01;

    private DcMotorEx armMotorSingle = null;
    private AnalogInput armPot = null;
    private BucketSystem bucketSystem;
    // private TubeSpinnerSystem tubeSpinnerSystem;
    private final double voltageAtZeroDegree = 0.27;//voltage when arm is resting on ground
    private final double voltageAt90Degree = 0.957;
    private final double voltsPerDegree = (voltageAt90Degree - voltageAtZeroDegree) / 90;
    private double armHomeDegrees = -95;

    // private double tubeSpinnerHomeDegrees = 0;
    private double lastOutput;

    // File is to save properties of arm and bucket in file so that it is remember if redeploy of app
    private File configFile = new File("armSystem.properties");
    private Exception lastException;
    private Boolean isSettingsLoaded = false;

    private double targetDegrees;
    private PIDController  pController = new PIDController(kP,kI,0);
    private boolean enablePID = false;

    private Telemetry telemetry;


    public ArmSystem(HardwareMap hardwareMap, BucketSystem bucketSystem, Telemetry telemetry) {
        armMotorSingle = hardwareMap.get(DcMotorEx.class, "armMotorSingle");
        armMotorSingle.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotorSingle.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.bucketSystem = bucketSystem;
        this.telemetry = telemetry;
        armPot = hardwareMap.get(AnalogInput.class, "ArmPot");

        loadSettings();

    }

    public double getArmHomeDegrees() {
        return armHomeDegrees;
    }

    public void setArmHomeDegrees(double armHomeDegrees) {
        this.armHomeDegrees = armHomeDegrees;
    }

    public void setPower(double Power) {
        armMotorSingle.setPower(Power);
    }

    public void Stop() {
        armMotorSingle.setPower(0);

    }

    double gravityAdjustPower() {

        double gravityMaxPower = .001;
        double straightUpDegree = 71.755; // measured on Alpha
        return ((straightUpDegree - this.getArmDegrees()) / (straightUpDegree - armHomeDegrees) * gravityMaxPower);

    }

    public Double getArmDegrees() {
        // Formula for linear potentiometer. Covert voltage betweem 0 amd 3.3 to degrees of 0 to 270
        // Subtract from 270 to reverse direction of degrees
        return 270 - (Double.valueOf(Math.round(armPot.getVoltage() * (270.0 / 3.3) * 100)) / 100.0);
    }

    public Double getArmDegreesFromHome() {
        return this.getArmDegrees() - this.getArmHomeDegrees();
    }


    private Boolean loadSettings() {
        File configFile = new File(Environment.getExternalStorageDirectory(), "armSystem.properties");
        // File configFile = new File("/mnt/sdcard/robot/armSystem.txt");
        this.armHomeDegrees = -50.0;
        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            this.armHomeDegrees = Double.valueOf(props.getProperty("armHomeDegrees", "45.0"));
            this.bucketSystem.setHomeEncoderPosition(
                    Double.valueOf(props.getProperty(
                            "bucketHomeEncoderPosition",
                            "0.0")));

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

    public void saveSettings() {
        //setHomeArmDegrees();
        armHomeDegrees = getArmDegrees();
        //tubeSpinnerSystem.setHomeDegrees(tubeSpinnerSystem.getDegrees());

        File configFile = new File(Environment.getExternalStorageDirectory(), "armSystem.properties");
        // File configFile = new File("/sdcard/Robot/armSystem.properties");
        try {
            Properties props = new Properties();
            props.setProperty("armHomeDegrees", Double.toString(this.armHomeDegrees));
            props.setProperty("bucketHomeEncoderPosition",
                    Double.toString(this.bucketSystem.getEncoderPosition()));
            //props.setProperty("tubeSpinnerHomeDegrees", Double.toString(this.tubeSpinnerSystem.getHomeDegrees()));
            // props.setProperty("armHomeDegrees", "90.0");
            FileWriter writer = new FileWriter(configFile);
            props.store(writer, "ArmSystem Settings");
            writer.close();
            // return true;
        } catch (IOException ex) {
            // IOException
            lastException = ex;
            // return false;
        }

    }

    public void moveToPickup() {
        setTargetDegrees(PICKUP_DEGREES);
    }

    public void setTargetDegrees(double targetDegrees) {
        this.targetDegrees = targetDegrees;
        pController.setSetPoint(this.targetDegrees);
        enablePID = true;

    }

    public void goLevel1() {
        setTargetDegrees(LEVEL_1_DEGREES);
    }

    public void goLevel2() {
        setTargetDegrees(LEVEL_2_DEGREES);
    }

    public void goLevel3() {
        setTargetDegrees(LEVEL_3_DEGREES);
    }

    public void setEnablePID(boolean enablePID) {
        this.enablePID = enablePID;

    }

    @Override
    public void periodic() {
        double power;
        // double output = pController.calculate(getArmDegrees());
        double output = pController.calculate(getArmDegreesFromHome());

        lastOutput = output;
//
        if (enablePID) {
            double maxPower = MAX_ARM_POWER;
            power = Math.min(Math.abs(output), maxPower) * Math.signum(output);
            //power = power + gravityAdjustPower();
            telemetry.addData("power", power);
            this.setPower(power);
        } else {
            power = 0;
            if (getArmDegrees() >= MAX_ARM_DEGREES) Stop();
        }
        switch (TELEMETRY_LEVEL) {
            case DEBUG:
                telemetry.addData(this.getName() + ":PIDpower", power);
                telemetry.addData(this.getName() + ":armPotvoltage", armPot.getVoltage());
                telemetry.addData(this.getName() + ":targetDegrees", targetDegrees);
                telemetry.addData(this.getName() + ":output", output);
                telemetry.addData(this.getName() + ":armPower", armMotorSingle.getPower());
                telemetry.addData(this.getName() + ":setPoint", pController.getSetPoint());
            case DIAGNOSTIC:
                telemetry.addData(this.getName() + ":getCurrent", armMotorSingle.getCurrent(CurrentUnit.AMPS));
                telemetry.addData(this.getName() + ":getPower", armMotorSingle.getPower());

            case CONFIG:
                telemetry.addData(this.getName() + ":LEVEL_3_DEGREES:", LEVEL_3_DEGREES);
                telemetry.addData(this.getName() + ":LEVEL_2_DEGREES:", LEVEL_2_DEGREES);
                telemetry.addData(this.getName() + ":LEVEL_1_DEGREES:", LEVEL_1_DEGREES);
                telemetry.addData(this.getName() + ":armHomeDegrees:", armHomeDegrees);
            case MATCH:
                telemetry.addData(this.getName() + ":Armdegrees:",
                        String.format("%.2f", getArmDegrees()));
                telemetry.addData(this.getName() + ":ArmdegreesFromHome:",
                        String.format("%.2f", getArmDegreesFromHome()));

                // telemetry.addData(this.getName()+":Command:",this.getCurrentCommand());
                break;
        }
    }

    public void  zeroArmAndBucket(){
        saveSettings(); // Saves Arm home

    }
}
