package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.TelemetrySystem;

public class UpdateTelemetry extends CommandBase {
    TelemetrySystem telemetrySystem;
    public UpdateTelemetry(TelemetrySystem telemetrySystem) {
        this.telemetrySystem = telemetrySystem;
        addRequirements(telemetrySystem);
    }

    @Override
    public void initialize() {
        // telemetrySystem.update();
    }

    @Override
    public void execute() {
        telemetrySystem.update();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
