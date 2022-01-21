package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.subsystems.MecDriveSystem;

public class DriveDistance  extends CommandBase {
    private DriveSystem driveSystem;
    // private MecDriveSystem mecDriveSystem;
    private double forwardPower;
    private double rotatePower;
    private double strafePower;
    private double distanceInches;
    private Telemetry telemetry;
    private double startPosition;
    static double countsPerRevolution = 26;
    static double gearRatio=20;
    static double positionsPerRevolution = countsPerRevolution * gearRatio;
    static double wheelDiameterInches = 75.0 / 26.54;
    static double wheelCircInches = wheelDiameterInches * Math.PI;

    @Override
    public void initialize() {
        startPosition = driveSystem.getRightFrontEncoder();
    }

    private double distanceTraveled(){
        return Math.abs((driveSystem.getRightFrontEncoder() - startPosition)/ positionsPerRevolution * wheelCircInches);
    }

    @Override
    public void execute() {
        driveSystem.Drive(this.forwardPower,this.rotatePower, this.strafePower);
        telemetry.addData("rightFrontEncoder",driveSystem.getRightFrontEncoder());
        telemetry.addData("distanceTraveled",distanceTraveled());
    }

    @Override
    public void end(boolean interrupted) {
        driveSystem.Drive(0,0,0);
    }

    @Override
    public boolean isFinished() {
        return distanceTraveled() > distanceInches;
    }

    public DriveDistance(DriveSystem driveSystem, double forwardPower, double rotatePower,
                         double strafePower, double distanceInches, Telemetry telemetry) {
        this.driveSystem = driveSystem;
        this.forwardPower = forwardPower;
        this.rotatePower = rotatePower;
        this.strafePower = strafePower;
        this.distanceInches = distanceInches;
        this.telemetry = telemetry;
    }
}
