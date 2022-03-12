package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSystem;

public class EjectCube extends CommandBase {
    private IntakeSystem intakeSystem;

    @Override
    public void end(boolean interrupted) {
        intakeSystem.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void initialize() {
        intakeSystem.expel();
    }

    public EjectCube(IntakeSystem intakeSystem) {
        this.intakeSystem = intakeSystem;
    }
}
