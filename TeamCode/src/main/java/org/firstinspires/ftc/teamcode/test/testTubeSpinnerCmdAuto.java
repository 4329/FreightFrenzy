package org.firstinspires.ftc.teamcode.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.UpdateTelemetry;
import org.firstinspires.ftc.teamcode.commands.ExerciseTubeTurn;
import org.firstinspires.ftc.teamcode.commands.NothingCommand;
import org.firstinspires.ftc.teamcode.commands.RotateTubeToDegree;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySystem;
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

@Autonomous(name = "Test TubeSpinnerCmd Auto", group = "3")
public class testTubeSpinnerCmdAuto extends CommandOpMode {
    private TubeSpinnerSystem tubeSpinnerSystem;
    private RotateTubeToDegree rotateTubeToDegree;
    private SequentialCommandGroup autoCommands, otherauto, nothingauto;
    private TelemetrySystem telemetrySystem;

    @Override
    public void initialize() {
        telemetrySystem  = new TelemetrySystem(telemetry);
        telemetrySystem.setDefaultCommand(new UpdateTelemetry(telemetrySystem));

        tubeSpinnerSystem = new TubeSpinnerSystem(hardwareMap);
        autoCommands = new SequentialCommandGroup(
                new RotateTubeToDegree(tubeSpinnerSystem,telemetry, 0.0, true),
                new RotateTubeToDegree(tubeSpinnerSystem,telemetry, 135.0, true),
                new RotateTubeToDegree(tubeSpinnerSystem,telemetry, -135.0, true),
                new RotateTubeToDegree(tubeSpinnerSystem,telemetry, 0.0, true));

        nothingauto = new SequentialCommandGroup(
                new NothingCommand(telemetry, "Nothing 1",50000),
                new RotateTubeToDegree(tubeSpinnerSystem,telemetry, -135.0, true),
                new NothingCommand(telemetry, "Nothing 2",50000),
                new RotateTubeToDegree(tubeSpinnerSystem,telemetry, 90.0, true),
                new NothingCommand(telemetry, "Nothing 2.1",50000),
                new RotateTubeToDegree(tubeSpinnerSystem,telemetry, 45.0, true),
                new NothingCommand(telemetry, "Nothing 2.2",50000),
                new RotateTubeToDegree(tubeSpinnerSystem,telemetry, 0.0, true),
                new NothingCommand(telemetry, "Nothing 2.5",50000),
                new RotateTubeToDegree(tubeSpinnerSystem,telemetry, -135.0, true),
                new NothingCommand(telemetry, "Nothing 3",5000),
                new RotateTubeToDegree(tubeSpinnerSystem,telemetry, 0.0, true),
                new NothingCommand(telemetry, "Nothing 4",5000));



        // autoCommands.schedule();
        // otherauto = new ExerciseTubeTurn(tubeSpinnerSystem, telemetry);
        // schedule(otherauto);
        //schedule(nothingauto);
        // schedule(new NothingCommand(telemetry,"OneOff"));
        schedule(nothingauto);
        // schedule(autoCommands);
        register(telemetrySystem);
        tubeSpinnerSystem.setPosition(.5);
    }

}


