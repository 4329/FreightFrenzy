package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.commands.ArmToPosition;
import org.firstinspires.ftc.teamcode.commands.BucketToAngle;
import org.firstinspires.ftc.teamcode.commands.DoNothingForTime;
import org.firstinspires.ftc.teamcode.commands.DriveDistance;
import org.firstinspires.ftc.teamcode.commands.EjectCube;
import org.firstinspires.ftc.teamcode.commands.ScanForCube;
import org.firstinspires.ftc.teamcode.subsystems.AAutoSystem;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.subsystems.BucketSystem;
import org.firstinspires.ftc.teamcode.subsystems.CameraSystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySystem;

@Autonomous(name = "Red Storage Hub Auto", group = "1")
public class RedStorageHubAuto extends CommandOpMode {

    private DriveSystem driveSystem;

    private TelemetrySystem telemetrySystem;

    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        CameraSystem cameraSystem = new CameraSystem(hardwareMap, "frontcam", telemetry);
        BucketSystem bucketSystem = new BucketSystem(hardwareMap, telemetry);
        ArmSystem armSystem = new ArmSystem(hardwareMap, bucketSystem, telemetry);
        IntakeSystem intakeSystem = new IntakeSystem(hardwareMap, telemetry);
        this.driveSystem = new DriveSystem(hardwareMap);
        // armSystem = new ArmSystem(hardwareMap, bucketSystem, telemetry);
        this.telemetrySystem = new TelemetrySystem(telemetry);


        SequentialCommandGroup moveArmToLevel = new SequentialCommandGroup(
                new ConditionalCommand(
                        new ArmToPosition(armSystem, ArmSystem.ArmPosition.LEVEL1, 30, telemetry).withTimeout(2000),
                        new DoNothingForTime(0, telemetry),
                        () -> {
                            return cameraSystem.lastObjectDirection == CameraSystem.ObjectDirection.UNKNOWN;
                        }
                ),
                new ConditionalCommand(
                        new ArmToPosition(armSystem, ArmSystem.ArmPosition.LEVEL2, 30, telemetry).withTimeout(2000),
                        new DoNothingForTime(0, telemetry),
                        () -> {
                            return cameraSystem.lastObjectDirection == CameraSystem.ObjectDirection.LEFT;
                        }
                ),
                new ConditionalCommand(
                        new ArmToPosition(armSystem, ArmSystem.ArmPosition.LEVEL3, 30, telemetry).withTimeout(2000),
                        new DoNothingForTime(0, telemetry),
                        () -> {
                            return cameraSystem.lastObjectDirection == CameraSystem.ObjectDirection.RIGHT;
                        }
                )
        );
        // Object left  - Level 1
        // Object Right - Level 2
        // Object Unknown - Level 3
        SequentialCommandGroup moveBucketForLevel = new SequentialCommandGroup(
                new ConditionalCommand(
                        new BucketToAngle(bucketSystem, 35, telemetry),
                        new DoNothingForTime(0, telemetry),
                        () -> {
                            return cameraSystem.lastObjectDirection == CameraSystem.ObjectDirection.UNKNOWN;
                        }
                ),
                new ConditionalCommand(

                        new BucketToAngle(bucketSystem, 35, telemetry),
                        new DoNothingForTime(0, telemetry),
                        () -> {
                            return cameraSystem.lastObjectDirection == CameraSystem.ObjectDirection.LEFT;
                        }
                ),
                new ConditionalCommand(
                        new BucketToAngle(bucketSystem, 25, telemetry),
                        new DoNothingForTime(0, telemetry),
                        () -> {
                            return cameraSystem.lastObjectDirection == CameraSystem.ObjectDirection.RIGHT;
                        }
                )
        );

/*        ParallelCommandGroup moveArmAndBucketForLevel = new ParallelCommandGroup(
                moveArmToLevel.withTimeout(3000),
                moveBucketForLevel
        );*/

        // Positive strafe is left, negative is right
        DriveDistance strafeToHub = new DriveDistance(driveSystem, 0, 0, .3, 28, telemetry);


        SequentialCommandGroup forwardToHub = new SequentialCommandGroup(
                new ConditionalCommand(
                        // Object left  - Level 1
                        new DriveDistance(driveSystem, -.3, 0, .0, 21, telemetry),
                        new DoNothingForTime(0, telemetry),
                        () -> {
                            return cameraSystem.lastObjectDirection == CameraSystem.ObjectDirection.UNKNOWN;
                        }
                ),
                new ConditionalCommand(
                        // Object Right - Level 2
                        new DriveDistance(driveSystem, -.3, 0, 0, 23.5, telemetry),
                        new DoNothingForTime(0, telemetry),
                        () -> {
                            return cameraSystem.lastObjectDirection == CameraSystem.ObjectDirection.LEFT;
                        }
                ),
                new ConditionalCommand(
                        // Object Unknown - Level 3
                        new DriveDistance(driveSystem, -.3, 0, 0, 25.5, telemetry),
                        new DoNothingForTime(0, telemetry),
                        () -> {
                            return cameraSystem.lastObjectDirection == CameraSystem.ObjectDirection.RIGHT;
                        }
                )
        );
        SequentialCommandGroup reverseToWall = new SequentialCommandGroup(
                new ConditionalCommand(
                        // Object left  - Level 1
                        new DriveDistance(driveSystem, .3, 0, 0, 23, telemetry),
                        new DoNothingForTime(0, telemetry),
                        () -> {
                            return cameraSystem.lastObjectDirection == CameraSystem.ObjectDirection.UNKNOWN;
                        }
                ),
                new ConditionalCommand(
                        // Object Right - Level 2
                        new DriveDistance(driveSystem, .3, 0, 0, 24, telemetry),
                        new DoNothingForTime(0, telemetry),
                        () -> {
                            return cameraSystem.lastObjectDirection == CameraSystem.ObjectDirection.RIGHT;
                        }
                ),
                new ConditionalCommand(
                        // Object Unknown - Level 3
                        new DriveDistance(driveSystem, .3, 0, 0, 25.5, telemetry),
                        new DoNothingForTime(0, telemetry),
                        () -> {
                            return cameraSystem.lastObjectDirection == CameraSystem.ObjectDirection.LEFT;
                        }
                )
        );

        // Strafe right to storage
        DriveDistance strafeToStorage = new DriveDistance(driveSystem, 0, 0, -.3, 53, telemetry);

        DriveDistance forwardFromWall = new DriveDistance(driveSystem, -.3, 0, 0, .25, telemetry);

        schedule(new SequentialCommandGroup(
                        new ScanForCube(cameraSystem, telemetry, true).withTimeout(3000),
                        new DoNothingForTime(AAutoSystem.autoDelayMilliseconds,telemetry),
                        moveBucketForLevel,
                        forwardFromWall,
                        strafeToHub,
                        moveArmToLevel,
                        forwardToHub.withTimeout(2000),
                        new EjectCube(intakeSystem).withTimeout(1000),
                        new DriveDistance(driveSystem, .2, 0, 0, 2, telemetry),
                        new InstantCommand(armSystem::goLevel2, armSystem),
                        reverseToWall,
                        forwardFromWall,
                        strafeToStorage.withTimeout(6000),
                        new DoNothingForTime(0, telemetry)
                )
        );
    }
}
