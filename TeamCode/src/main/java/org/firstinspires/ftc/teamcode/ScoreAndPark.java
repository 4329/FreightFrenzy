package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.DriveDistance;
import org.firstinspires.ftc.teamcode.subsystems.AAutoSystem;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;
import org.firstinspires.ftc.teamcode.subsystems.BucketSystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySystem;

@Disabled
@Autonomous(name = "Score and Park",group = "3")
public class ScoreAndPark extends CommandOpMode {
    @Override
    public void initialize() {
        MultipleTelemetry multipleTelemetry = new MultipleTelemetry(telemetry,
                FtcDashboard.getInstance().getTelemetry());
        TelemetrySystem telemetrySystem = new TelemetrySystem(multipleTelemetry);
        BucketSystem bucketSystem = new BucketSystem(hardwareMap,multipleTelemetry);
        ArmSystem armSystem = new ArmSystem(hardwareMap,bucketSystem,multipleTelemetry);
        DriveSystem driveSystem = new DriveSystem(hardwareMap);
        AAutoSystem aAutoSystem = new AAutoSystem(hardwareMap,multipleTelemetry);

        DriveDistance driveDistance;
        switch (aAutoSystem.alliance){
            case RED_ALLIANCE:
             //   driveDistance = new DriveDistance(0,.5,)
                break;
            case BLUE_ALLIANCE:
            case UNKNOWN:
        }
    }
}
