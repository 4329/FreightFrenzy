package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.ArmAboveDegree;
import org.firstinspires.ftc.teamcode.commands.ArmToDegree;
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
    private TubeSpinnerSystem tubeSpinnerSystem;
    private BucketSystem bucketSystem;
    private ArmSystem armSystem;
    private TelemetrySystem telemetrySystem;
    private ArmToDegree armToDegree;
    private CheeseKickerSystem cheeseKickerSystem;


    @Override
    public void initialize() {
        this.driveSystem = new DriveSystem(hardwareMap);
        tubeSpinnerSystem = new TubeSpinnerSystem(hardwareMap);
        armSystem = new ArmSystem(hardwareMap,bucketSystem, telemetry);
        cheeseKickerSystem = new CheeseKickerSystem(hardwareMap);
        this.telemetrySystem = new TelemetrySystem(telemetry);

        armToDegree = new ArmToDegree(45, armSystem, telemetry);

        DriveDistance driveDistance = new DriveDistance(driveSystem,
                0, 0, -0.5, 24, telemetry);

        schedule(new SequentialCommandGroup(new InstantCommand(armSystem::moveToPickup),
                new ArmAboveDegree(armSystem, 20.0, telemetry),
                driveDistance
        ));
        register(telemetrySystem);

    }
}
