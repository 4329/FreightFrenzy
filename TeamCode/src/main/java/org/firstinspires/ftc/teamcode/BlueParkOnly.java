package org.firstinspires.ftc.teamcode;

import android.util.SizeF;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.commands.ArmAboveDegree;
import org.firstinspires.ftc.teamcode.commands.ArmToDegree;
import org.firstinspires.ftc.teamcode.commands.ArmToPosition;
import org.firstinspires.ftc.teamcode.commands.BucketToAngle;
import org.firstinspires.ftc.teamcode.commands.DoNothingForTime;
import org.firstinspires.ftc.teamcode.commands.DriveDistance;
import org.firstinspires.ftc.teamcode.commands.StrafeByTime;
import org.firstinspires.ftc.teamcode.commands.StrafeContinuous;
import org.firstinspires.ftc.teamcode.commands.UpdateTelemetry;
import org.firstinspires.ftc.teamcode.subsystems.AAutoSystem;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.subsystems.BucketSystem;
import org.firstinspires.ftc.teamcode.subsystems.CheeseKickerSystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySystem;
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

@Autonomous(name = "Blue Park Only", group = "1")
public class BlueParkOnly extends CommandOpMode {


    private DriveSystem driveSystem;

    private TelemetrySystem telemetrySystem;



    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        BucketSystem bucketSystem = new BucketSystem(hardwareMap, telemetry);
        ArmSystem armSystem = new ArmSystem(hardwareMap, bucketSystem, telemetry);
        this.driveSystem = new DriveSystem(hardwareMap);
        // armSystem = new ArmSystem(hardwareMap, bucketSystem, telemetry);
        this.telemetrySystem = new TelemetrySystem(telemetry);
       // AAutoSystem aAutoSystem = new AAutoSystem(hardwareMap, telemetry);


        // armToDegree = new ArmToDegree(45, armSystem,telemetry);

        //strafe left for 24in.
        DriveDistance driveDistance = new DriveDistance(driveSystem,
                0, 0, 0.5, 24, telemetry);

        /*schedule(new SequentialCommandGroup(new InstantCommand(armSystem::moveToPickup),
                new ArmAboveDegree(armSystem,20.0,telemetry),
                driveDistance
                ));*/
        ParallelCommandGroup raiseArmAndBucket = new ParallelCommandGroup(
              //  new BucketToAngle(bucketSystem, 10, 5, telemetry),
                new ArmToPosition(armSystem, ArmSystem.ArmPosition.LEVEL2, 5.0,telemetry));
    /*    ParallelRaceGroup raiseWithTimer = new ParallelRaceGroup(raiseArmAndBucket,
                new DoNothingForTime(5000,telemetry));*/
        schedule(new SequentialCommandGroup(
                raiseArmAndBucket,
                driveDistance));

        register(telemetrySystem);

    }
}
