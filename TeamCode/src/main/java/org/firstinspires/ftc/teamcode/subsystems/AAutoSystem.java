package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class AAutoSystem extends SubsystemBase {
    public static boolean redAlliance = false;
    public static boolean blueAlliance = false;
    public static TelemetrySystem.TelemetryLevel telemetryLevel = TelemetrySystem.TelemetryLevel.MATCH;

    private Telemetry telemetry;
    private DigitalChannel redToggle;

    public enum ALLIANCE {
        RED_ALLIANCE,
        BLUE_ALLIANCE,
        UNKNOWN
    }

    public ALLIANCE alliance=ALLIANCE.UNKNOWN;

    public AAutoSystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        redToggle = hardwareMap.get(DigitalChannel.class, "redtoggle");
        redToggle.setMode(DigitalChannel.Mode.INPUT);
        readSwitches();
    }

    @Override
    public void periodic() {
        switch (telemetryLevel) {
            case DEBUG:
            case DIAGNOSTIC:
                telemetry.addData(this.getName() + ":redToggle", redToggle.getState());
                telemetry.addData(this.getName() + ":redAlliance", redAlliance);
            case CONFIG:
            case MATCH:
                telemetry.addData(this.getName()+"ALLIANCE",alliance);
        }
    }

    public void readSwitches(){
        redAlliance = redToggle.getState();

        if(redAlliance) alliance = ALLIANCE.RED_ALLIANCE;
        else alliance = ALLIANCE.UNKNOWN;


    }

}
