package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ArmContinuous;
import org.firstinspires.ftc.teamcode.commands.SaveSettings;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySystem;

@TeleOp(name = "Save Settings",group = "2")
public class SettingsMode extends CommandOpMode {

    @Override
    public void initialize() {
        GamepadEx operator = new GamepadEx(gamepad2);

        ArmSystem armSystem;
        TelemetrySystem telemetrySystem;

        armSystem = new ArmSystem(hardwareMap);
        telemetrySystem = new TelemetrySystem(telemetry);

        Trigger leftOperatorTrigger = new Trigger(
                () -> {
                    if (operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1){
                        return true;
                    } else {
                        return false;
                    }
                })
                .whenActive(new ArmContinuous(armSystem,telemetry,
                        () -> - operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                        () -> true));
        Trigger rightOperatorTrigger = new Trigger(
                () -> {
                    if (operator.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1){
                        return true;
                    } else {
                        return false;
                    }
                })
                .whenActive(new ArmContinuous(armSystem,telemetry,
                        () -> operator.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER),
                        () -> true));


        operator.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(new SaveSettings(armSystem,telemetry));
        register(telemetrySystem);

    }
}
