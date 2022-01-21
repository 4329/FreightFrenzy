package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.variable.CustomVariable;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.teamcode.commands.ArmContinuous;
import org.firstinspires.ftc.teamcode.commands.CaroContinuous;
import org.firstinspires.ftc.teamcode.commands.DriveByPower;
import org.firstinspires.ftc.teamcode.commands.DriveContinuous;
import org.firstinspires.ftc.teamcode.commands.DriveMecanum;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.subsystems.BucketSystem;
import org.firstinspires.ftc.teamcode.subsystems.CameraSystem;
import org.firstinspires.ftc.teamcode.subsystems.CarouselTurnerSystem;
import org.firstinspires.ftc.teamcode.subsystems.ConfigSystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.subsystems.IMUSystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSystem;
import org.firstinspires.ftc.teamcode.subsystems.MecDriveSystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySystem;

@TeleOp(name = "Match Teleop", group = "1")
public class MatchTeleop extends CommandOpMode {
    // FtcDashboard dashboard = FtcDashboard.getInstance();

    GamepadEx driver, operator;
    TriggerReader leftOperatorTriggerReader, rightOperatorTriggerReader;
    DriveSystem driveSystem;

    TelemetrySystem telemetrySystem;
    ArmSystem armSystem;
    CarouselTurnerSystem carouselTurnerSystem;


    DriveContinuous driveContinuous;

    CaroContinuous caroContinuous;

    @Override
    public void initialize() {
        CustomVariable customVariable = FtcDashboard.getInstance().getConfigRoot();

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        IMUSystem imuSystem = new IMUSystem(hardwareMap, telemetry);
        MecDriveSystem mecDriveSystem = new MecDriveSystem(hardwareMap, imuSystem, telemetry);
        // gamepads
        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);

        // Subsystems
        BucketSystem bucketSystem = new BucketSystem(hardwareMap, telemetry);
        // driveSystem = new DriveSystem(hardwareMap);
        armSystem = new ArmSystem(hardwareMap, bucketSystem, telemetry);
        telemetrySystem = new TelemetrySystem(telemetry);
        carouselTurnerSystem = new CarouselTurnerSystem(hardwareMap);
        IntakeSystem intakeSystem = new IntakeSystem(hardwareMap, telemetry);
        // CameraSystem cameraSystem = new CameraSystem(hardwareMap,"frontcam", telemetry);
        ConfigSystem configSystem = new ConfigSystem(FtcDashboard.getInstance().getConfigRoot(),
                telemetry);
        try {
            configSystem.load();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        operator.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld(new InstantCommand(bucketSystem::up,bucketSystem))
                .whenReleased(new InstantCommand(bucketSystem::stop, bucketSystem));
        // Left Bumper register negative degrees command continuous
        operator.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whileHeld(new InstantCommand(bucketSystem::down, bucketSystem))
                .whenReleased(new InstantCommand(bucketSystem::stop, bucketSystem));
        //Dpad up expel "Intake system"
        operator.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new InstantCommand(intakeSystem::expel, intakeSystem))
                .whenReleased(new InstantCommand(intakeSystem::stop, intakeSystem));
        //Dpad down takeIn "Intake system"
        operator.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new InstantCommand(intakeSystem::takeIn, intakeSystem))
                .whenReleased(new InstantCommand(intakeSystem::stop, intakeSystem));



        DriveMecanum driveMecanumCommand = new DriveMecanum(mecDriveSystem,
                () -> -driver.getLeftY(),
                () -> driver.getRightX(),
                () -> driver.getLeftX(),
                () -> driver.getButton(GamepadKeys.Button.LEFT_BUMPER));
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
        // - Right Bumper - Go up continuous
        // - Left Bumper - Go down continuous
        // - X Button - Arm to pickup
        // - A Button - Level 1
        // - B Button - Level 2
        // - Y Button - Level 3
        Trigger leftOperatorTrigger = new Trigger(
                () -> {
                    if (operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .whenActive(new ArmContinuous(armSystem, telemetry,
                        () -> -operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                        () -> operator.getButton(GamepadKeys.Button.BACK)));
        Trigger rightOperatorTrigger = new Trigger(
                () -> {
                    // if greater than 0.1 then it trigger command; command is active (ArmCone
                    if (operator.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1) {
                        return true;
                    } else {
                        return false;
                    }
                })
                .whenActive(new ArmContinuous(armSystem, telemetry,
                        () -> operator.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER),
                        () -> operator.getButton(GamepadKeys.Button.BACK)));


        // When Start button is pressed, save settings
        operator.getGamepadButton(GamepadKeys.Button.START)
                .and(operator.getGamepadButton(GamepadKeys.Button.BACK))
                .whenActive(new ParallelCommandGroup(new InstantCommand(armSystem::saveSettings)));

        // Move Tube to Carry Position
        operator.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(new ParallelCommandGroup(
                        new InstantCommand(armSystem::goLevel2, armSystem),
                        new InstantCommand(bucketSystem::level, bucketSystem)));
        operator.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(new ParallelCommandGroup(
                        new InstantCommand(armSystem::goLevel1, armSystem),
                        new InstantCommand(bucketSystem::level, bucketSystem)));
        operator.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(new ParallelCommandGroup(
                        new InstantCommand(armSystem::goLevel3, armSystem),
                        new InstantCommand(bucketSystem::level, bucketSystem)));

        // Register default command to update telemetry at top of next
        // telemetrySystem.setDefaultCommand(new UpdateTelemetry(telemetrySystem));

        // DriveByPower driveStrafeLeft = new DriveByPower(driveSystem,
        //        0.0,0.0,1.0,
        //        telemetry);
        // D=Pad Left - Strafe Left
        //Slooooooooooooooooow
        driver.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .and(driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER))
                .whenActive(new DriveByPower(mecDriveSystem,
                        0.0, 0.0, -0.2,
                        telemetry))
                .whenInactive(driveMecanumCommand);

        //Normal (;
        driver.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .and(driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).negate())
                .whenActive(new DriveByPower(mecDriveSystem,
                        0.0, 0.0, -0.8,
                        telemetry))
                .whenInactive(driveMecanumCommand);

        // D-Pan Right - Strafe Right
        driver.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .and(driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).negate())
                .whenActive(new DriveByPower(mecDriveSystem,
                        0.0, 0.0, 0.8,
                        telemetry))
                //Sloooooooooooooooooooow
                .whenInactive(driveMecanumCommand);
        driver.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .and(driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER))
                .whenActive(new DriveByPower(mecDriveSystem,
                        0.0, 0.0, 0.2,
                        telemetry))
                .whenInactive(driveMecanumCommand);
        // D-Pad Up - Forward
        driver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .and(driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).negate())
                .whenActive(new DriveByPower(mecDriveSystem,
                        -0.75, 0.0, 0.0,
                        telemetry))
                .whenInactive(driveMecanumCommand);
        //Sloooooooooooooooow
        driver.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .and(driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER))
                .whenActive(new DriveByPower(mecDriveSystem,
                        -0.25, 0.0, 0.0,
                        telemetry))
                .whenInactive(driveMecanumCommand);
        // D-Pad Down - Reverse
        driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .and(driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).negate())
                .whenActive(new DriveByPower(mecDriveSystem,
                        0.75, 0.0, 0.0,
                        telemetry))
                .whenInactive(driveMecanumCommand);
        //Sloooooooooooooow
        driver.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .and(driver.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER))
                .whenActive(new DriveByPower(mecDriveSystem,
                        0.25, 0.0, 0.0,
                        telemetry))
                .whenInactive(driveMecanumCommand);

        driver.getGamepadButton(GamepadKeys.Button.B)
                .toggleWhenPressed(new InstantCommand(mecDriveSystem::enableFieldCentric),
                        new InstantCommand(mecDriveSystem::disableFieldCentric),true);

        driver.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(new InstantCommand(imuSystem::zeroHeading));
        // schedule(driveContinuous, caroContinuous);
        schedule(driveMecanumCommand, caroContinuous);
        armSystem.setEnablePID(false);
        // Registered subsystems will have their Periodic method called
        register(telemetrySystem, armSystem,
                bucketSystem, imuSystem,
                intakeSystem, mecDriveSystem);
    }
}
