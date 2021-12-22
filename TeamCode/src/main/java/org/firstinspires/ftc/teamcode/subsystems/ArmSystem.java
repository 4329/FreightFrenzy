package org.firstinspires.ftc.teamcode.subsystems;

import android.os.Environment;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PController;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

public class ArmSystem extends SubsystemBase {
    static double PICKUP_DEGREES=20.0;
   // private DcMotorEx armMotorLeft = null;
   // private DcMotorEx armMotorRight = null;
    private DcMotorEx armMotorSingle = null;
    private AnalogInput armPot = null;
    private TubeSpinnerSystem tubeSpinnerSystem;
    private final double voltageAtZeroDegree = 0.27;//voltage when arm is resting on ground
    private final double voltageAt90Degree = 0.957;
    private final double voltsPerDegree = (voltageAt90Degree - voltageAtZeroDegree) / 90;
    private double armHomeDegrees = -95;
    private double tubeSpinnerHomeDegrees=0;
    private double lastOutput;

    private File configFile = new File("armSystem.properties");
    private Exception lastException;
    private Boolean isSettingsLoaded = false;

    private double targetDegrees;
    private PController pController = new PController(.05);
    private boolean enablePID=false;

    private Telemetry telemetry;


   /* public ArmSystem(HardwareMap hardwareMap,
                     String LeftMotorName,
                     String RightMotorName,
                     String PotName,
                     TubeSpinnerSystem tubeSpinnerSystem,
                     Telemetry telemetry) {
        armMotorLeft = hardwareMap.get(DcMotorEx.class, LeftMotorName);
        armMotorRight = hardwareMap.get(DcMotorEx.class, RightMotorName);
        armMotorLeft.setDirection(DcMotor.Direction.REVERSE);
        armMotorRight.setDirection(DcMotor.Direction.REVERSE);
        armPot = hardwareMap.get(AnalogInput.class, PotName);
        this.tubeSpinnerSystem = tubeSpinnerSystem;
        this.telemetry = telemetry;

        armMotorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //ToDo  - Need to tweak i coeff to help get arm moving quicker.
        armMotorLeft.setVelocityPIDFCoefficients(10,12,0,0);
        armMotorRight.setVelocityPIDFCoefficients(10,12,0,0);
        armMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        loadSettings();
        // setTargetDegrees(armHomeDegrees);
        pController.reset();
        tubeSpinnerSystem.setDegree(tubeSpinnerSystem.getHomeDegrees());
        // this.isSettingsLoaded = loadSettings();
    }
*/
    public ArmSystem(HardwareMap hardwareMap, TubeSpinnerSystem tubeSpinnerSystem, Telemetry telemetry) {
        armMotorSingle = hardwareMap.get(DcMotorEx.class, "armMotorSingle");
        armMotorSingle.setDirection(DcMotorSimple.Direction.FORWARD);
        armMotorSingle.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.tubeSpinnerSystem = tubeSpinnerSystem;
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

// Hi Mr. Sheck. DJ was coding here.. This comment was made on 12/21/21
    public void Stop() {
       armMotorSingle.setPower(0);

    }

    public String getTelemetry() {
        String exMessage;
        if (lastException != null) {
            exMessage = lastException.getMessage();
        } else {
            exMessage = "";
        }
        return MessageFormat.format(
                "ArmSystem:(ArmDegrees={0}), (PotVolts={1}), (armHomeDegrees={2}),(tubeSpinnerHomeDegrees={3}, (output={4})",
                this.getArmDegrees(),
                armPot.getVoltage(),
                this.armHomeDegrees,
                tubeSpinnerSystem.getHomeDegrees(),
                lastOutput
        );
    }

    double gravityAdjustPower(){

        double gravityMaxPower = .001;
        double straightUpDegree = 71.755; // measured on Alpha
        return ((straightUpDegree-this.getArmDegrees())/(straightUpDegree-armHomeDegrees) * gravityMaxPower);

    }

    public Double getArmDegrees() {
        // Formula for linear potentiometer
        return armPot.getVoltage() * (270 / 3.3);
    }

    public void setHomeArmDegrees() {
        // When called, set the armHomeDegrees and update settings
        this.armHomeDegrees = this.getArmDegrees();
    }

    public void moveToLevel1(){

    }
    private Boolean loadSettings() {
        File configFile = new File(Environment.getExternalStorageDirectory(), "armSystem.properties");
        // File configFile = new File("/mnt/sdcard/robot/armSystem.txt");
        this.armHomeDegrees = -50.0;
        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            this.armHomeDegrees = Double.valueOf(props.getProperty("armHomeDegrees"));
            this.tubeSpinnerSystem.setHomeDegrees(Double.valueOf(props.getProperty("tubeSpinnerHomeDegrees")));
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

    public void saveSettings() {
        setHomeArmDegrees();
        armHomeDegrees=getArmDegrees();
        tubeSpinnerSystem.setHomeDegrees(tubeSpinnerSystem.getDegrees());

        File configFile = new File(Environment.getExternalStorageDirectory(), "armSystem.properties");
        // File configFile = new File("/sdcard/Robot/armSystem.properties");
        try {
            Properties props = new Properties();
            props.setProperty("armHomeDegrees", Double.toString(this.armHomeDegrees));
            props.setProperty("tubeSpinnerHomeDegrees", Double.toString(this.tubeSpinnerSystem.getHomeDegrees()));
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

    public void moveToPickup(){
        setTargetDegrees(PICKUP_DEGREES);
    }

    public void setTargetDegrees(double targetDegrees){
        this.targetDegrees=targetDegrees;
        pController.setSetPoint(this.targetDegrees);
        enablePID=true;

    }
    public void setEnablePID(boolean enablePID){
        this.enablePID=enablePID;

    }

    @Override
    public void periodic() {
        double output= pController.calculate(getArmDegrees());
        lastOutput=output;
//        telemetry.addLine(this.getTelemetry());
//        telemetry.addData("targetDegrees",targetDegrees);
//        telemetry.addData("output",output);
//        telemetry.addData("getArmDegrees",getArmDegrees());
//        telemetry.addData("setPoint",pController.getSetPoint());
//        telemetry.addData("enablePID",enablePID);
//        telemetry.addData("gravityPower",this.gravityAdjustPower());
//        telemetry.addData("leftMotorPosition",armMotorLeft.getCurrentPosition());
//        telemetry.addData("leftMotorVelocity",armMotorLeft.getVelocity(AngleUnit.DEGREES));
//        telemetry.addData("leftMotorPID",armMotorLeft.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER));

        if (enablePID){
            double maxPower=.125;
            double power = Math.min(Math.abs(output),maxPower) * Math.signum(output);
            power = power+gravityAdjustPower();
            telemetry.addData("power",power);
          //this.setPower(power);
        }

    }
}
