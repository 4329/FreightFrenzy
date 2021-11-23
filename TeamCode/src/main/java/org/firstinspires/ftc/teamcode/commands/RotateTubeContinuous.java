package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

import java.util.function.DoubleSupplier;


public class RotateTubeContinuous extends CommandBase {
    private final TubeSpinnerSystem tubeSpinnerSystem;
    private DoubleSupplier rotateDegreesSupplier;
    private Telemetry telemetry;


    public RotateTubeContinuous(TubeSpinnerSystem tubeSpinnerSystem,
                                DoubleSupplier rotateDegreesSupplier,
                                Telemetry telemetry){
        this(tubeSpinnerSystem,rotateDegreesSupplier);
        this.telemetry = telemetry;
        addRequirements(tubeSpinnerSystem);
    }

    public RotateTubeContinuous(TubeSpinnerSystem tubeSpinnerSystem,
                                DoubleSupplier rotateDegreesSupplier) {
        this.tubeSpinnerSystem = tubeSpinnerSystem;
        this.rotateDegreesSupplier = rotateDegreesSupplier;
        addRequirements(tubeSpinnerSystem);
    }

    @Override
    public void initialize() {
        if(telemetry != null){
            telemetry.addLine("RotateTube Command Init");
        }
     }

    @Override
    public void execute() {
        if(telemetry != null){
            telemetry.addLine("RotateTube Command Execute");
            telemetry.addData("rotateDegrees",this.rotateDegreesSupplier.getAsDouble());
        }
        tubeSpinnerSystem.rotateTube(this.rotateDegreesSupplier.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
