package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.BasicOpMode_Iterative;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;

@TeleOp(name = "Test ArmSystem",group = "Test")
public class testArmSystem extends OpMode {
    private ArmSystem armSystem;
    @Override
    public void init() {
        armSystem = new ArmSystem(hardwareMap,"arm_left",
                "arm_right","arm_pot");

    }

    @Override
    public void loop() {
        armSystem.setPower(gamepad2.right_trigger);
        armSystem.setPower(-gamepad2.left_trigger);
    }
}
