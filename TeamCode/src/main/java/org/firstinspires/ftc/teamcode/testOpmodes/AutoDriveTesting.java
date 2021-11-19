package org.firstinspires.ftc.teamcode.testOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;

@Autonomous(name = "AutoDriveTesting",group = "Testing")
public class AutoDriveTesting extends LinearOpMode {
    DriveSystem driveSystem = null;

    @Override
    public void runOpMode(){
        driveSystem = new DriveSystem(hardwareMap);

        waitForStart();

        driveSystem.DriveByDistance(0.5F,24);
        sleep(5000);



    }
}
