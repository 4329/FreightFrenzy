package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.commands.ArmToDegree;
import org.firstinspires.ftc.teamcode.commands.StrafeByTime;
import org.firstinspires.ftc.teamcode.commands.StrafeContinuous;
import org.firstinspires.ftc.teamcode.commands.UpdateTelemetry;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySystem;
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

@Autonomous(name = "BlueAutoCrawl", group = "1")
public class BlueAutoCrawl extends CommandOpMode {

    private StrafeByTime strafeByTime;
    private DriveSystem driveSystem;
    private TubeSpinnerSystem tubeSpinnerSystem;
    private ArmSystem armSystem;
    private TelemetrySystem telemetrySystem;
    private StrafeContinuous strafeContinuous;
    private ArmToDegree armToDegree;


    @Override
    public void initialize() {
        this.driveSystem = new DriveSystem(hardwareMap);
        tubeSpinnerSystem = new TubeSpinnerSystem(hardwareMap);
        armSystem = new ArmSystem(hardwareMap,tubeSpinnerSystem,telemetry);
        this.telemetrySystem = new TelemetrySystem(telemetry);

        armToDegree = new ArmToDegree(10, armSystem,telemetry);

        this.strafeByTime = new StrafeByTime(driveSystem,
                1,
                1,
                 telemetry,true);
        this.strafeContinuous = new StrafeContinuous(driveSystem,1,telemetry,true);

        // strafeContinuous.withTimeout(1000);


        // telemetrySystem.setDefaultCommand(new UpdateTelemetry(telemetrySystem));


        // schedule(new SequentialCommandGroup(strafeContinuous));
        // schedule(new SequentialCommandGroup(strafeByTime));
        schedule(new SequentialCommandGroup(armToDegree));
        register(telemetrySystem);

    }
}
