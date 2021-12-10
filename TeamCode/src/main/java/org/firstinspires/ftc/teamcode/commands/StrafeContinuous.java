package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;
import com.arcrobotics.ftclib.util.Timing.Timer;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;

import java.util.concurrent.TimeUnit;

public class StrafeContinuous extends CommandBase {
    private DriveSystem driveSystem;
    private double powerToStrafe;
    private long startTimeInMS;
    private Telemetry telemetry;
    private boolean addTelemetry;
    private double elapsedTimeSeconds;
    private Timer timer;

    public StrafeContinuous(DriveSystem driveSystem,
                            double powerToStrafe,
                            Telemetry telemetry,
                            boolean addTelemetry) {
        this.driveSystem = driveSystem;
        this.powerToStrafe = powerToStrafe;
        this.telemetry = telemetry;
        this.addTelemetry = addTelemetry;
    }

    @Override
    public void initialize() {
        this.startTimeInMS = System.currentTimeMillis();
        this.timer = new Timer(30,TimeUnit.SECONDS);
        // timer.start();
        // driveSystem.Drive(0, 0, this.powerToStrafe);
    }

    @Override
    public void execute() {
        telemetry.addLine("Top of Execute");
        if (! timer.isTimerOn()) {
            timer.start();
        }
        driveSystem.Drive(0,0, this.powerToStrafe);
        this.elapsedTimeSeconds = (System.currentTimeMillis() - startTimeInMS) / 1000;
        // telemetry.addData("Elapsed Time", this.elapsedTimeSeconds);
        if ((this.telemetry != null) && this.addTelemetry) {
            this.telemetry.addData("Elapsed Time", elapsedTimeSeconds);
            this.telemetry.addData("Timer", timer.elapsedTime());
            // telemetry.addData("Timer",timer.elapsedTime());
        }

    }

    @Override
    public void end(boolean interrupted) {
        // timer.done();

        driveSystem.Drive(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        // When elapsed seconds is bigger than secondsToStrafe
        return false;
    }
}
