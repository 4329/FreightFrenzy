package org.firstinspires.ftc.teamcode.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultArmCommand;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;

@TeleOp(name = "TestArmCommands",group = "Test")
public class testArmCommands extends CommandOpMode {
    private ArmSystem m_armSystem;
    private GamepadEx m_operatorController;
    private DefaultArmCommand m_armUpCommand;
    private DefaultArmCommand m_armDownCommand;

    @Override
    public void initialize() {
        m_armSystem = new ArmSystem(hardwareMap,"arm_left",
                "arm_right","arm_pot");
        m_operatorController = new GamepadEx(gamepad2);

        m_armUpCommand = new DefaultArmCommand(m_armSystem,
                () -> m_operatorController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
        m_armDownCommand = new DefaultArmCommand(m_armSystem,
                () -> m_operatorController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)
                        - m_operatorController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));

        schedule(m_armDownCommand);
    }
}
