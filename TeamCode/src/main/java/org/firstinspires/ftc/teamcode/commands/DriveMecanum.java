package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.subsystems.IMUSystem;
import org.firstinspires.ftc.teamcode.subsystems.MecDriveSystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DriveMecanum extends CommandBase {
    private MecDriveSystem mecDriveSystem;
    private DoubleSupplier forwardDrive;
    private DoubleSupplier rotateDrive;
    private DoubleSupplier strafeDrive;
    private BooleanSupplier slowMotion;

    public DriveMecanum(MecDriveSystem mecDriveSystem,
                        DoubleSupplier forwardDrive,
                        DoubleSupplier rotateDrive,
                        DoubleSupplier strafeDrive,
                        BooleanSupplier slowMotion) {
        this.mecDriveSystem = mecDriveSystem;
        this.forwardDrive = forwardDrive;
        this.rotateDrive = rotateDrive;
        this.strafeDrive = strafeDrive;
        this.slowMotion = slowMotion;
        addRequirements(mecDriveSystem);
    }

    @Override
    public void execute() {
        if (!slowMotion.getAsBoolean()) {
            mecDriveSystem.Drive(
                    forwardDrive.getAsDouble(),
                    rotateDrive.getAsDouble(),
                    strafeDrive.getAsDouble());
        }else {
            mecDriveSystem.Drive(
                    forwardDrive.getAsDouble()/3,
                    rotateDrive.getAsDouble()/3,
                    strafeDrive.getAsDouble()/3);
        }
    }
}
