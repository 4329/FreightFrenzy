package org.firstinspires.ftc.teamcode;

import android.util.SizeF;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.commands.ArmAboveDegree;
import org.firstinspires.ftc.teamcode.commands.ArmToDegree;
import org.firstinspires.ftc.teamcode.commands.DriveDistance;
import org.firstinspires.ftc.teamcode.commands.StrafeByTime;
import org.firstinspires.ftc.teamcode.commands.StrafeContinuous;
import org.firstinspires.ftc.teamcode.commands.UpdateTelemetry;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.subsystems.BucketSystem;
import org.firstinspires.ftc.teamcode.subsystems.CheeseKickerSystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySystem;
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

@Autonomous(name = "Blue Park Only", group = "1")
public class BlueParkOnly extends CommandOpMode {


    private DriveSystem driveSystem;
    private TubeSpinnerSystem tubeSpinnerSystem;
    private BucketSystem bucketSystem;
    private ArmSystem armSystem;
    private TelemetrySystem telemetrySystem;
    private ArmToDegree armToDegree;
    private CheeseKickerSystem cheeseKickerSystem;


    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        this.driveSystem = new DriveSystem(hardwareMap);
        armSystem = new ArmSystem(hardwareMap, bucketSystem, telemetry);
        this.telemetrySystem = new TelemetrySystem(telemetry);

        armToDegree = new ArmToDegree(45, armSystem,telemetry);

        DriveDistance driveDistance = new DriveDistance(driveSystem,
                0,0,.5,24,telemetry);

        /*schedule(new SequentialCommandGroup(new InstantCommand(armSystem::moveToPickup),
                new ArmAboveDegree(armSystem,20.0,telemetry),
                driveDistance
                ));*/
        schedule(new SequentialCommandGroup(driveDistance));

        register(telemetrySystem);

    }
}
