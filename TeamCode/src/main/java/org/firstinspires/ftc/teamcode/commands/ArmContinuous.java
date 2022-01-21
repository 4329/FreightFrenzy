package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class ArmContinuous extends CommandBase {

    private final ArmSystem armSystem;
    private final DoubleSupplier armPowerSupplier;
    private final BooleanSupplier addTelemetrySupplier;
    private Telemetry telemetry;


    public ArmContinuous(ArmSystem armSystem,
                         Telemetry telemetry,
                         DoubleSupplier armPowerSupplier,
                         BooleanSupplier addTelemetrySupplier) {
        this.armSystem = armSystem;
        this.telemetry = telemetry;
        this.armPowerSupplier = armPowerSupplier;
        this.addTelemetrySupplier = addTelemetrySupplier;
        addRequirements(this.armSystem);
    }

    @Override
    public void execute() {
        if(addTelemetrySupplier.getAsBoolean() && (this.telemetry != null))
        {
            telemetry.addData("--- Command", this.getName());
            telemetry.addData("Arm Power",armPowerSupplier.getAsDouble());
            // telemetry.addData("ArmSystem",armSystem.getTelemetry());
        }
        armSystem.setEnablePID(false);
        armSystem.setPower(armPowerSupplier.getAsDouble());


    }
}
