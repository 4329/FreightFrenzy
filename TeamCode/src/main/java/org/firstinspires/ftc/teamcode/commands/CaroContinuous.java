package org.firstinspires.ftc.teamcode.commands;

import android.os.Message;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.CarouselTurnerSystem;

import java.text.MessageFormat;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class CaroContinuous extends CommandBase {
    private final CarouselTurnerSystem carouselTurnerSystem;
    private final Telemetry telemetry;
    private final DoubleSupplier powerSupplier;
    private final BooleanSupplier addTelemetrySupplier;


    public CaroContinuous(CarouselTurnerSystem carouselTurnerSystem,
                          Telemetry telemetry,
                          DoubleSupplier powerSupplier,
                          BooleanSupplier addTelemetrySupplier) {
        this.carouselTurnerSystem = carouselTurnerSystem;
        this.telemetry = telemetry;
        this.powerSupplier = powerSupplier;
        this.addTelemetrySupplier = addTelemetrySupplier;
        addRequirements(carouselTurnerSystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (this.addTelemetrySupplier.getAsBoolean() && (this.telemetry != null)) {
            telemetry.addLine(
                    MessageFormat.format("---{0}(power {1})",
                            this.getName(),
                            powerSupplier.getAsDouble()));

        }
        carouselTurnerSystem.setPower(powerSupplier.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
