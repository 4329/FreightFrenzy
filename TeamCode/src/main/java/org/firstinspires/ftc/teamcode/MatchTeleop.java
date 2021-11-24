package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
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

@TeleOp(name = "Match Teleop", group = "1")
public class MatchTeleop extends CommandOpMode {
    static Double ROTATE_DEGREES = 0.1;

    DriveSystem driveSystem;
    TubeSpinnerSystem tubeSpinnerSystem;
    TelemetrySystem telemetrySystem;
    ArmSystem armSystem;
    CarouselTurnerSystem carouselTurnerSystem;

    DriveContinuous driveContinuous;
    GamepadEx driver, operator;
    RotateTubeContinuous rotateTubeContinuous;
    ArmContinuous armContinuous;
    UpdateTelemetry updateTelemetry;
    CaroContinuous caroContinuous;

    @Override
    public void initialize() {
        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);

        driveSystem = new DriveSystem(hardwareMap);
        tubeSpinnerSystem = new TubeSpinnerSystem(hardwareMap);
        armSystem = new ArmSystem(hardwareMap);
        telemetrySystem = new TelemetrySystem(telemetry);
        carouselTurnerSystem = new CarouselTurnerSystem(hardwareMap);

        driveContinuous = new DriveContinuous(driveSystem,
                () -> -driver.getLeftY(),
                () -> -driver.getRightX(),
                () -> -driver.getLeftX()
        );

        rotateTubeContinuous = new RotateTubeContinuous(tubeSpinnerSystem,
                () -> {
                    if (operator.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
                        return ROTATE_DEGREES;
                    } else if (operator.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
                        return -ROTATE_DEGREES;
                    } else {
                        return 0;
                    }
                });

        caroContinuous = new CaroContinuous(carouselTurnerSystem,
                () -> 0.0);


        armContinuous = new ArmContinuous(armSystem,
                () -> operator.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) -
                        operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));

        // Register default command to update telemetry
        telemetrySystem.setDefaultCommand( new UpdateTelemetry(telemetrySystem));

        schedule(driveContinuous, rotateTubeContinuous,armContinuous);
        register(telemetrySystem);
    }
}
