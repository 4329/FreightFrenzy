package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.TelemetrySystem;

public class DefaultTelemetry extends CommandBase {
    TelemetrySystem telemetrySystem;
    public DefaultTelemetry(TelemetrySystem telemetrySystem) {
        this.telemetrySystem = telemetrySystem;
        addRequirements(telemetrySystem);
    }

    @Override
    public void initialize() {
        telemetrySystem.update();
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
