package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.CarouselTurnerSystem;

import java.util.function.DoubleSupplier;

public class CaroContinuous extends CommandBase {
    private CarouselTurnerSystem carouselTurnerSystem;
    private DoubleSupplier powerSupplier;


    public CaroContinuous(CarouselTurnerSystem carouselTurnerSystem, DoubleSupplier powerSupplier) {
        this.carouselTurnerSystem = carouselTurnerSystem;
        this.powerSupplier=powerSupplier;
        addRequirements(carouselTurnerSystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        carouselTurnerSystem.setPower(powerSupplier.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
