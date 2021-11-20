package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;

public class ReportTelemetry  extends CommandBase {
    private Telemetry telemetry;
    private ArmSystem armSystem;

    public ReportTelemetry(Telemetry Telemetry,ArmSystem ArmSystem){
        telemetry = Telemetry;
        armSystem =ArmSystem;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void execute() {
        // armSystem.ReportTelemetry(telemetry);
        telemetry.update();

    }

    @Override
    public void initialize() {

    }
}

