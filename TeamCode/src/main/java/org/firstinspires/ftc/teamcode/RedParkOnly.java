package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.ArmAboveDegree;
import org.firstinspires.ftc.teamcode.commands.ArmToDegree;
import org.firstinspires.ftc.teamcode.commands.ArmToPosition;
import org.firstinspires.ftc.teamcode.commands.DriveDistance;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.subsystems.BucketSystem;
import org.firstinspires.ftc.teamcode.subsystems.CheeseKickerSystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySystem;
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

@Autonomous(name = "Red Park Only", group = "2")
public class RedParkOnly extends CommandOpMode {
    private DriveSystem driveSystem;

    // private ArmSystem armSystem;
    private TelemetrySystem telemetrySystem;
    // private ArmToDegree armToDegree;



    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        this.driveSystem = new DriveSystem(hardwareMap);
        BucketSystem bucketSystem = new BucketSystem(hardwareMap, telemetry);
        ArmSystem armSystem = new ArmSystem(hardwareMap, bucketSystem, telemetry);
        // armSystem = new ArmSystem(hardwareMap,bucketSystem, telemetry);
        this.telemetrySystem = new TelemetrySystem(telemetry);

        // armToDegree = new ArmToDegree(45, armSystem, telemetry);

        DriveDistance driveDistance = new DriveDistance(driveSystem,
                0, 0, -0.5, 24, telemetry);

        ParallelCommandGroup raiseArmAndBucket = new ParallelCommandGroup(
                //  new BucketToAngle(bucketSystem, 10, 5, telemetry),
                new ArmToPosition(armSystem, ArmSystem.ArmPosition.LEVEL2, 5.0,telemetry));

/*        schedule(new SequentialCommandGroup(new InstantCommand(armSystem::moveToPickup),
                new ArmAboveDegree(armSystem, 20.0, telemetry),
                driveDistance ));*/
        schedule(new SequentialCommandGroup(raiseArmAndBucket, driveDistance));
        register(telemetrySystem);

    }
}
