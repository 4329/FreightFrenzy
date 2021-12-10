package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;

public class StrafeByTime extends CommandBase {
    private DriveSystem driveSystem;
    private double powerToStrafe;
    private double secondsToStrafe;
    private long startTimeInMS=0;
    private Telemetry telemetry;
    private boolean addTelemetry;
    private double elapsedTimeSeconds=-1;


    public StrafeByTime(DriveSystem driveSystem,
                        double powerToStrafe,
                        double secondsToStrafe,
                        Telemetry telemetry,
                        boolean addTelemetry) {
        this.driveSystem = driveSystem;
        this.powerToStrafe = powerToStrafe;
        this.secondsToStrafe = secondsToStrafe;
        this.telemetry = telemetry;
        this.addTelemetry = addTelemetry;
    }

    @Override
    public void initialize() {
        // this.startTimeInMS=System.currentTimeMillis();
    }

    @Override
    public void execute() {
        if(this.startTimeInMS == 0){
            this.startTimeInMS=System.currentTimeMillis();
        }
        driveSystem.Drive(0,0, this.powerToStrafe);
        this.elapsedTimeSeconds = (System.currentTimeMillis() - startTimeInMS)/1000.0;
        if(this.telemetry != null && this.addTelemetry){
            telemetry.addData("Elapsed Time",elapsedTimeSeconds);
        }

    }

    @Override
    public void end(boolean interrupted) {
        driveSystem.Drive(0,0,0);
    }

    @Override
    public boolean isFinished() {
        // When elapsed seconds is bigger than secondsToStrafe
        return this.elapsedTimeSeconds > this.secondsToStrafe;
    }
}
