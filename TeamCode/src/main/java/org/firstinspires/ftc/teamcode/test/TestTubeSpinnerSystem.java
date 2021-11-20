package org.firstinspires.ftc.teamcode.test;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

@TeleOp(name = "TestTubeSpinnerSystem",group = "Test")
public class TestTubeSpinnerSystem extends OpMode {
    private TubeSpinnerSystem SpinTube;
    private Servo servoTest = null;

    @Override
    public void init() {
        SpinTube=new  TubeSpinnerSystem(hardwareMap,"TubeSpinner");
        servoTest =hardwareMap.get(Servo.class,"TubeSpinner");

        SpinTube.movePosition(0);
    }


    @Override
    public void loop() {
        if (gamepad2.y == true) {
            SpinTube.turnTubeToAngle(0.0);
        }
        else{
            SpinTube.turnTubeToAngle(90);
        }
        telemetry.addData("TubeSpinner Position",SpinTube.getPosition());
        telemetry.addData("TubeSpinner Angle",servoTest.getPosition());
    }

}
