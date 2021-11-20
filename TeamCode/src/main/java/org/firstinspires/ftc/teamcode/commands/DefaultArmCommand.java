package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;

import java.util.function.DoubleSupplier;

public class DefaultArmCommand extends CommandBase {

    private final ArmSystem m_armSystem;
    private final DoubleSupplier m_powerMethod;

    public DefaultArmCommand(ArmSystem armSystem, DoubleSupplier powerMethod) {
        m_armSystem = armSystem;
        m_powerMethod = powerMethod;
        addRequirements(m_armSystem);
    }


    @Override
    public void execute() {

        m_armSystem.setPower(m_powerMethod.getAsDouble());
    }
}
