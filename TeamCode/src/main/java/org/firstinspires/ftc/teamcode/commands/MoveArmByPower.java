package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;

public class MoveArmByPower extends CommandBase {
    private ArmSystem armSystem;
    private double armPower;
    private Telemetry telemetry;

    public MoveArmByPower(Telemetry Telemetry, ArmSystem ArmSystem, double ArmPower) {
        telemetry = Telemetry;
        armSystem = ArmSystem;
        armPower = ArmPower;

    }

    @Override
    public void initialize() {
        armSystem.setPower(armPower);
        telemetry.addData("MoveByArmPower","initialize");
    }

    @Override
    public void execute() {
        telemetry.addData("MoveByArmPower","execute");
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public void addTelemetry() {

    }
}
