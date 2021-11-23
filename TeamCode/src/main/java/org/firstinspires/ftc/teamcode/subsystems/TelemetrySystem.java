package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TelemetrySystem extends SubsystemBase {
    private Telemetry telemetry;
    public boolean enableTelemetry=false;

    public TelemetrySystem(Telemetry telemetry) {
        this.telemetry=telemetry;
    }

    @Override
    public void periodic() {
        telemetry.addLine("End of telemetry");
        this.update();
    }

    public void update(){
        telemetry.update();
    }

}
