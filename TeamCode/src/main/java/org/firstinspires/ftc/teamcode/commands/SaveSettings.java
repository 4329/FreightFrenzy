package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;

import java.text.MessageFormat;
import java.util.Formatter;

public class SaveSettings extends CommandBase {
    ArmSystem armSystem;
    Telemetry telemetry;
    Boolean saveResult=false;
    public SaveSettings(ArmSystem armSystem, Telemetry telemetry) {
        this.armSystem = armSystem;
        this.telemetry = telemetry;
    }

    @Override
    public void initialize() {
        saveResult = armSystem.saveSettings();
        telemetry.addLine(MessageFormat.format("Command {0}, (SaveSettings={1})",
                this.getName(),
                saveResult));
    }

    @Override
    public void execute() {
        telemetry.addLine(MessageFormat.format("Command {0}, (SaveSettings={1})",
                this.getName(),
                saveResult));
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
