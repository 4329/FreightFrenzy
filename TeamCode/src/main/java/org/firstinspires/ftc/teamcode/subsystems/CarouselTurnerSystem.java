package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Carousel turner spins a wheel to turn the carousel
 */
public class CarouselTurnerSystem extends SubsystemBase{

    private final DcMotor carouselMotor;

    public CarouselTurnerSystem(HardwareMap hardwareMap, final String name){
        carouselMotor = hardwareMap.get(DcMotor.class, name);
        carouselMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public CarouselTurnerSystem(HardwareMap hardwareMap){
        this(hardwareMap,"CaroMotor");
    }

    public void setPower(Double power){
        carouselMotor.setPower(power);
    }


}
