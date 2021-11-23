package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;

import java.util.function.DoubleSupplier;

public class DriveContinuous extends CommandBase {
    private final DriveSystem driveSystem;
    private final DoubleSupplier forwardDrive;
    private final DoubleSupplier rotateDrive;
    private final DoubleSupplier strafeDrive;

    public DriveContinuous(DriveSystem driveSystem, DoubleSupplier forwardDrive, DoubleSupplier rotateDrive, DoubleSupplier strafeDrive) {
        this.driveSystem = driveSystem;
        this.forwardDrive = forwardDrive;
        this.rotateDrive = rotateDrive;
        this.strafeDrive = strafeDrive;
        addRequirements(driveSystem);
    }

    @Override
    public void execute() {
        driveSystem.Drive(forwardDrive.getAsDouble(),
                rotateDrive.getAsDouble(),
                strafeDrive.getAsDouble());
    }
}
