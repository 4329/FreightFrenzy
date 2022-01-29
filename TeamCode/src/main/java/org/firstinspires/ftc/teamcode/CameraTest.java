package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.CameraSystem;
import org.firstinspires.ftc.teamcode.subsystems.TelemetrySystem;

@TeleOp(name = "Camera test",group = "2")
public class CameraTest extends CommandOpMode {
    @Override
    public void initialize() {
        Telemetry multipleTelemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        TelemetrySystem telemetrySystem = new TelemetrySystem(multipleTelemetry);
        CameraSystem cameraSystem = new CameraSystem(hardwareMap,"frontcam",multipleTelemetry);
        cameraSystem.enableObjectDetection();
        register(telemetrySystem, cameraSystem);
    }
}
