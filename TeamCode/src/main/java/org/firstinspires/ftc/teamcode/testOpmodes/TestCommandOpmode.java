package org.firstinspires.ftc.teamcode.testOpmodes;



import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.MoveArmByPower;
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;

@TeleOp(name = "TestSomething",group="Test")
public class TestCommandOpmode extends CommandOpMode{

    private GamepadEx m_Operator;
    private TubeSpinnerSystem tubeSpinner;
    private ArmSystem armSystem;
    private GamepadButton bButton;

    @Override
    public void initialize() {
        m_Operator = new GamepadEx(gamepad2);
        // tubeSpinner = new TubeSpinnerSystem(hardwareMap);
        armSystem = new ArmSystem(hardwareMap);
        bButton = new GamepadButton(m_Operator,GamepadKeys.Button.B);


    }
}
