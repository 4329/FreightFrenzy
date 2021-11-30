package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

import java.text.MessageFormat;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;


public class RotateTubeContinuous extends CommandBase {
    private final TubeSpinnerSystem tubeSpinnerSystem;
    private final DoubleSupplier rotateDegreesSupplier;
    private Telemetry telemetry;
    private final BooleanSupplier addTelemetrySupplier;


    public RotateTubeContinuous(TubeSpinnerSystem tubeSpinnerSystem,
                                Telemetry telemetry,
                                DoubleSupplier rotateDegreesSupplier,
                                BooleanSupplier addTelemetrySupplier) {
        this.tubeSpinnerSystem = tubeSpinnerSystem;
        this.telemetry = telemetry;
        this.rotateDegreesSupplier = rotateDegreesSupplier;
        this.addTelemetrySupplier = addTelemetrySupplier;
        addRequirements(tubeSpinnerSystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (this.addTelemetrySupplier.getAsBoolean() && (this.telemetry != null)) {
            telemetry.addLine(
                    MessageFormat.format("---{0}(Degrees {1})",
                            this.getName(),
                            this.rotateDegreesSupplier.getAsDouble()));
        }
        tubeSpinnerSystem.rotateTube(this.rotateDegreesSupplier.getAsDouble());
        if (this.addTelemetrySupplier.getAsBoolean() && (this.telemetry != null)) {
            telemetry.addLine(
                    MessageFormat.format("---{0} -> getDegrees={1}",
                            this.getName(),
                            this.tubeSpinnerSystem.getDegrees()));
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
