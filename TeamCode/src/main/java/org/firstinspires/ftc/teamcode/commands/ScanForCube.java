package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.CameraSystem;

public class ScanForCube extends CommandBase {
    private CameraSystem cameraSystem;
    private Telemetry telemetry;
    private boolean isBlueside;

    @Override
    public boolean isFinished() {
        telemetry.addData(this.getName()+":objectdirection", cameraSystem.getObjectDirection());
        telemetry.addData(this.getName()+":lastObjectDirection", cameraSystem.lastObjectDirection);
        return cameraSystem.getObjectDirection()!= CameraSystem.ObjectDirection.UNKNOWN;

    }

    @Override
    public void initialize() {
        cameraSystem.enableObjectDetection();

    }

    @Override
    public void end(boolean interrupted) {
        cameraSystem.disableObjectDetection();
    }

    public ScanForCube(CameraSystem cameraSystem, Telemetry telemetry, boolean isBlueside) {
        this.cameraSystem = cameraSystem;
        this.telemetry = telemetry;
        this.isBlueside = isBlueside;
    }
}
