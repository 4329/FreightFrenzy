package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="ArmTest", group="Test")

public class TestArm extends OpMode{
    private ArmSystem armSystem=null;
    @Override
    public void init() {
        armSystem=new ArmSystem(hardwareMap);

    }

    @Override
    public void loop() {

        if(gamepad2.b){
            armSystem.GoToAngle(30);
        }
        else{
            // armSystem.Stop();
        }

        telemetry.addData("GetAngle", armSystem.GetAngle());

        telemetry.update();

    }

}
