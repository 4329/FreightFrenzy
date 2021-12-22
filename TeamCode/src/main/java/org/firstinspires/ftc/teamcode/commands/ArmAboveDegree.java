package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;

public class ArmAboveDegree extends CommandBase {
    private ArmSystem armSystem;
    private double degreeTarget;
    Telemetry telemetry;


    public ArmAboveDegree(ArmSystem armSystem, double degreeTarget, Telemetry telemetry) {
        this.armSystem = armSystem;
        this.degreeTarget = degreeTarget;
        this.telemetry = telemetry;
    }

    @Override
    public boolean isFinished() {
        return armSystem.getArmDegrees() > degreeTarget;
    }
}
