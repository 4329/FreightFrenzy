package org.firstinspires.ftc.teamcode.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ArmContinuous;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;

@TeleOp(name = "TestArmCommands",group = "Test")
public class testArmCommands extends CommandOpMode {
    private ArmSystem armSystem;
    private GamepadEx m_operatorController;
    private ArmContinuous armContinuous;

    @Override
    public void initialize() {
        armSystem = new ArmSystem(hardwareMap,"arm_left",
                "arm_right","arm_pot");
        m_operatorController = new GamepadEx(gamepad2);

        armContinuous = new ArmContinuous(armSystem,
                () -> m_operatorController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)
                        - m_operatorController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));

        schedule(armContinuous);
    }
}
