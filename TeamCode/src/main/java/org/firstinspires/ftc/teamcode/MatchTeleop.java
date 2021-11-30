package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.teamcode.commands.ArmContinuous;
import org.firstinspires.ftc.teamcode.commands.CaroContinuous;
import org.firstinspires.ftc.teamcode.commands.DriveContinuous;
import org.firstinspires.ftc.teamcode.commands.RotateTubeContinuous;
import org.firstinspires.ftc.teamcode.commands.UpdateTelemetry;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.subsystems.CarouselTurnerSystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySystem;
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

import java.util.function.BooleanSupplier;

@TeleOp(name = "Match Teleop", group = "1")
public class MatchTeleop extends CommandOpMode {
    static Double ROTATE_DEGREES = 0.1;

    GamepadEx driver, operator;
    TriggerReader leftOperatorTriggerReader,rightOperatorTriggerReader;
    DriveSystem driveSystem;
    TubeSpinnerSystem tubeSpinnerSystem;
    TelemetrySystem telemetrySystem;
    ArmSystem armSystem;
    CarouselTurnerSystem carouselTurnerSystem;

    DriveContinuous driveContinuous;

    RotateTubeContinuous rotateTubeContinuous;
    ArmContinuous armContinuous;
    UpdateTelemetry updateTelemetry;
    CaroContinuous caroContinuous;

    @Override
    public void initialize() {
        // gamepads
        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);

        // Subsystems
        driveSystem = new DriveSystem(hardwareMap);
        tubeSpinnerSystem = new TubeSpinnerSystem(hardwareMap);
        armSystem = new ArmSystem(hardwareMap);
        telemetrySystem = new TelemetrySystem(telemetry);
        carouselTurnerSystem = new CarouselTurnerSystem(hardwareMap);

        // Bind RotateTube commands
        // Right Bumper Rotates Positive
        // Left Bumper Rotates Negative
        // No Bumper Stops Rotate
        operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new RotateTubeContinuous(tubeSpinnerSystem,telemetry,
                        () -> ROTATE_DEGREES,
                        () -> operator.getButton(GamepadKeys.Button.BACK)))
                .whenReleased(new RotateTubeContinuous(tubeSpinnerSystem,telemetry,
                        () -> 0,
                        () -> operator.getButton(GamepadKeys.Button.BACK)));
        // Left Bumper register negative degrees command continous
        operator.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new RotateTubeContinuous(tubeSpinnerSystem,telemetry,
                        () -> - ROTATE_DEGREES,
                        () -> operator.getButton(GamepadKeys.Button.BACK)))
                .whenReleased(new RotateTubeContinuous(tubeSpinnerSystem,telemetry,
                        () -> 0,
                        () -> operator.getButton(GamepadKeys.Button.BACK)));
        // Left and Right Bumper together resets to home
        operator.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .and(operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER))
                .whenActive(new RotateTubeContinuous(tubeSpinnerSystem,telemetry,
                        () -> 0,
                        () -> operator.getButton(GamepadKeys.Button.BACK)));
        // Continuous Commands
        driveContinuous = new DriveContinuous(driveSystem,
                () -> -driver.getLeftY(),
                () -> -driver.getRightX(),
                () -> -driver.getLeftX()
        );



        rotateTubeContinuous = new RotateTubeContinuous(tubeSpinnerSystem, telemetry,
                () -> {
                    if (operator.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
                        return ROTATE_DEGREES;
                    } else if (operator.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
                        return -ROTATE_DEGREES;
                    } else {
                        return 0;
                    }
                },
                () -> operator.getButton((GamepadKeys.Button.BACK))
        );

        // Carousel Command - Continuous
        caroContinuous = new CaroContinuous(carouselTurnerSystem, telemetry,
                () -> {
                    if (operator.getButton(GamepadKeys.Button.DPAD_RIGHT)) return 1.0;
                    else if (operator.getButton(GamepadKeys.Button.DPAD_LEFT)) return -1.0;
                    else return 0.0;
                },
                () -> operator.getButton(GamepadKeys.Button.BACK)
        );

        // Arm commands
        // - Right Trigger - Go up continuous
        // - Left Trigger - Go down continuous
        // - X Button - Go to home
        // - A Button - Level 1
        // - B Button - Level 2
        // - Y Button - Level 3
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
                        () -> operator.getButton(GamepadKeys.Button.BACK)));
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
                        () -> operator.getButton(GamepadKeys.Button.BACK)));


        armContinuous = new ArmContinuous(armSystem, telemetry,
                () -> operator.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) -
                        operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                () -> operator.getButton(GamepadKeys.Button.BACK));

        // Register default command to update telemetry
        telemetrySystem.setDefaultCommand(new UpdateTelemetry(telemetrySystem));

        schedule(driveContinuous, caroContinuous);
        register(telemetrySystem);
    }
}
