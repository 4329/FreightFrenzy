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
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

@TeleOp(name = "Save Settings",group = "2")
public class SettingsMode extends CommandOpMode {
    private final double TRIGGER_THRESHOLD= 0.1;
    @Override
    public void initialize() {
        GamepadEx operator = new GamepadEx(gamepad2);

        // Subsystems
        TubeSpinnerSystem tubeSpinnerSystem= new TubeSpinnerSystem(hardwareMap);
        ArmSystem armSystem=new ArmSystem(hardwareMap,tubeSpinnerSystem,telemetry);
        TelemetrySystem telemetrySystem=new TelemetrySystem(telemetry);

        // Create Trigger for operator trigger over threshold so that a command can be scheduled
        // This gives manual operator control of Arm to go down - See negative trigger value
        // Turn on addTelemetry by passing true
        Trigger leftOperatorTrigger = new Trigger(
                // When trigger over threshold, then schedule ArmContinuous
                () -> {
                    if (operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > TRIGGER_THRESHOLD){
                        return true;
                    } else {
                        return false;
                    }
                })
                .whenActive(new ArmContinuous(armSystem,telemetry,
                        () -> - operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                        () -> true));
        // Create trigger for operator right trigger to schedule ArmContinous when over threshold
        // This gives manual operator control of Arm to go up
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

        // When Start button is pressed, save settings
        operator.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(new SaveSettings("robot.config",armSystem,telemetry));

        // Register telemetrySystem to that Period will do telemetry update
        // By register system, the periodic will be called first by Command Scheduler
        // so any telemetry adds from preceded looping will be updated
        register(telemetrySystem);

    }
}
