package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;

public class DriveByPower extends CommandBase {
    private DriveSystem driveSystem;
    private double forwardPower, rotatePower, strafePower;
    private Telemetry telemetry;
    private boolean firstExecute = false;

    @Override
    public void execute() {
        if (!firstExecute){
            // driveSystem.Drive(forwardPower,rotatePower,strafePower);
            firstExecute=true;
        }
        driveSystem.Drive(forwardPower,rotatePower,strafePower);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public DriveByPower(DriveSystem driveSystem, double forwardPower, double rotatePower, double strafePower, Telemetry telemetry) {
        this.driveSystem = driveSystem;
        this.forwardPower = forwardPower;
        this.rotatePower = rotatePower;
        this.strafePower = strafePower;
        this.telemetry = telemetry;
        addRequirements(driveSystem);
    }

}
