package org.firstinspires.ftc.teamcode.test;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

@TeleOp(name = "Test TubeSpinnerSystem",group = "3")
@Disabled
public class TestTubeSpinnerSystem extends OpMode {
    private TubeSpinnerSystem spinTube;

    @Override
    public void init() {
        spinTube =new  TubeSpinnerSystem(hardwareMap);
    }


    @Override
    public void loop() {
        double degreesToRotate = .5;
        if (gamepad2.left_bumper){
           spinTube.rotateTube(-degreesToRotate);
        }

        if (gamepad2.right_bumper) {
            // spinTube.rotateTube(degreesToRotate);
            spinTube.rotateTube(degreesToRotate);
        }

        if(gamepad2.y){
            spinTube.setDegree(0);
        }
        if(gamepad2.b){
            spinTube.setDegree(135);
        }
        if(gamepad2.x){
            spinTube.setDegree(-135);
        }
        telemetry.addData("TubeSpinner Position", spinTube.getPosition());
        telemetry.addData("TubeSpinner Angle",spinTube.getDegrees());
        telemetry.addData("TubeSpinner Range",spinTube.getDegreeRange());

        telemetry.update();
    }

}
