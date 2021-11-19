package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Carousel turner spins a wheel to turn the carousel
 */
public class CarouselTurnerSystem extends SubsystemBase{

    private final DcMotor carouselMotor;


    public CarouselTurnerSystem(final HardwareMap hMap, final String name){
        carouselMotor = hMap.get(DcMotor.class, name);
    }

    /**
     * Spin Wheel Clockwise
     */
    public void spinClockwise() {
        carouselMotor.setPower(1);
    }

    /**
     * Spin Wheel CounterClockwise
     */

    public void spinCounterClockwise(){
        carouselMotor.setPower(-1);
    }

    public void spinStop(){
        carouselMotor.setPower(0);
    }
    @Override
    public void periodic(){
        // This method will be called once per scheduler run.
    }

}
