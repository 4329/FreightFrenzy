package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.ArmSpinnerSubsystem;

@TeleOp(name = "TestArmSpinner",group="Test")
public class TestCommandOpmode extends CommandOpMode{

    private GamepadEx m_Operator;
    private ArmSpinnerSubsystem armSpinner;

    @Override
    public void initialize() {
        m_Operator = new GamepadEx(gamepad2);
        armSpinner = new ArmSpinnerSubsystem(hardwareMap);

        m_Operator.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
            .whenPressed(new MoveArmSpinnerCommand(armSpinner));



    }
}
