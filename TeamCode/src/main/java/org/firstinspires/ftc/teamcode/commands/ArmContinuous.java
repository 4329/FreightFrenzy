package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;

import java.util.function.DoubleSupplier;

public class ArmContinuous extends CommandBase {

    private final ArmSystem armSystem;
    private final DoubleSupplier armPowerSupplier;

    public ArmContinuous(ArmSystem armSystem, DoubleSupplier armPowerSupplier) {
        this.armSystem = armSystem;
        this.armPowerSupplier = armPowerSupplier;
        addRequirements(this.armSystem);
    }

    @Override
    public void execute() {
        armSystem.setPower(armPowerSupplier.getAsDouble());
    }
}
