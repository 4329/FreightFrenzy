package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TelemetrySystem extends SubsystemBase {
    private Telemetry telemetry;
    public boolean enableTelemetry=false;
    private int periodicCount=0;

    public TelemetrySystem(Telemetry telemetry) {
        this.telemetry=telemetry;
        // Make Telemetry update faster than default 250ms. May need to change this back if things go too slow
        telemetry.setMsTransmissionInterval(100);
    }

    @Override
    public void periodic() {
        periodicCount++;
        // Give a count just to show telemetry running
        telemetry.addData("Telemetry Periodic Count",periodicCount);
        // telemetry.addLine("End of telemetry");
        this.update();
    }

    public void update(){
        telemetry.update();
    }

}
