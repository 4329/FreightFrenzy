package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

public class ExerciseTubeTurn extends SequentialCommandGroup {

    public ExerciseTubeTurn(TubeSpinnerSystem tubeSpinnerSystem, Telemetry telemetry){
        addCommands(
                new RotateTubeToDegree(tubeSpinnerSystem, telemetry,90.0,true),
                new RotateTubeToDegree(tubeSpinnerSystem,telemetry,135.0,true),
                new RotateTubeToDegree(tubeSpinnerSystem,telemetry,-135.0,true),
                new RotateTubeToDegree(tubeSpinnerSystem,telemetry,0.0,true)
        );
    }
}
