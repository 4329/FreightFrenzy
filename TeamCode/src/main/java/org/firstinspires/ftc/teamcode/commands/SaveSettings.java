package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.subsystems.PropertySystem;

import java.text.MessageFormat;
import java.util.Formatter;

public class SaveSettings extends CommandBase {
    ArmSystem armSystem;
    PropertySystem propertySystem;
    Telemetry telemetry;
    Boolean saveResult=false;
    String configFilename;

    public SaveSettings(String configFilename,ArmSystem armSystem, Telemetry telemetry) {
        this.configFilename =configFilename;
        this.armSystem = armSystem;
        this.telemetry = telemetry;
        propertySystem = new PropertySystem(configFilename);
    }

    @Override
    public void initialize() {
        saveResult = propertySystem.setProperty("",Double.toString(armSystem.getArmHomeDegrees()));
        // ToDo - Ask tubeSpinner to save settings
        telemetry.addLine(MessageFormat.format("Command {0}, (SaveSettings={1})",
                this.getName(),
                saveResult));
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
