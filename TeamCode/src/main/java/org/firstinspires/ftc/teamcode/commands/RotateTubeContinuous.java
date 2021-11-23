package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

import java.util.function.DoubleSupplier;


public class RotateTubeContinuous extends CommandBase {
    private final TubeSpinnerSystem tubeSpinnerSystem;
    private DoubleSupplier rotateDegreesSupplier;
    private final Telemetry telemetry;
    private Boolean isFinished = false;


    public RotateTubeContinuous(TubeSpinnerSystem tubeSpinnerSystem, DoubleSupplier rotateDegreesSupplier, Telemetry telemetry){
        this.tubeSpinnerSystem=tubeSpinnerSystem;
        this.rotateDegreesSupplier = rotateDegreesSupplier;
        this.telemetry = telemetry;
        addRequirements(tubeSpinnerSystem);
    }
    @Override
    public void initialize() {
        telemetry.addLine("RotateTube Command Init");
        tubeSpinnerSystem.rotateTube(0);
     }

    @Override
    public void execute() {
        telemetry.addLine("RotateTube Command Execute");
        telemetry.addData("rotateDegrees",this.rotateDegreesSupplier.getAsDouble());
        tubeSpinnerSystem.rotateTube(this.rotateDegreesSupplier.getAsDouble());
        telemetry.update();
        this.isFinished=false;

    }


    @Override
    public boolean isFinished() {
        return this.isFinished;
    }
}
