package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.subsystems.MecDriveSystem;

public class DriveByPower extends CommandBase {
    // private DriveSystem driveSystem;
    private MecDriveSystem mecDriveSystem;
    private double forwardPower, rotatePower, strafePower;
    private Telemetry telemetry;
    private boolean firstExecute = false;

    @Override
    public void execute() {
        if (!firstExecute){
            // driveSystem.Drive(forwardPower,rotatePower,strafePower);
            firstExecute=true;
        }
        // driveSystem.Drive(forwardPower,rotatePower,strafePower);
        mecDriveSystem.Drive(forwardPower,rotatePower,strafePower);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public DriveByPower(MecDriveSystem driveSystem,
                        double forwardPower, double rotatePower,
                        double strafePower, Telemetry telemetry) {
        // this.driveSystem = driveSystem;
        this.mecDriveSystem = driveSystem;
        this.forwardPower = forwardPower;
        this.rotatePower = rotatePower;
        this.strafePower = strafePower;
        this.telemetry = telemetry;
        addRequirements(mecDriveSystem);
    }

}
