package org.firstinspires.ftc.teamcode.subsystems;

import android.util.Log;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.config.variable.BasicVariable;
import com.acmerobotics.dashboard.config.variable.ConfigVariable;
import com.acmerobotics.dashboard.config.variable.CustomVariable;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Config
public class ConfigSystem extends SubsystemBase {
    public static TelemetrySystem.TelemetryLevel telemetryLevel = TelemetrySystem.TelemetryLevel.MATCH;
    public static boolean triggerSave = false;
    private LocalDate lastSave;

    private Telemetry telemetry;
    private CustomVariable customVariable;

    public ConfigSystem(CustomVariable customVariable, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.customVariable = customVariable;
    }

    public void save(){

    }
    public void load() throws ClassNotFoundException {
        CustomVariable rootVariable = this.customVariable;

        for (Map.Entry<String, ConfigVariable> subSystem : this.customVariable.entrySet()){
            String subSystemName=subSystem.getKey();
            ConfigVariable subSystemValue = subSystem.getValue();
            Log.d(this.getName(),subSystemName);
            Map<String, ConfigVariable> subVariables = (Map<String, ConfigVariable>) subSystemValue.getValue();
            for (Map.Entry<String,ConfigVariable> subVariable : subVariables.entrySet()){
                Log.d(this.getName(),subVariable.getKey());
                // Field[] pbFields =Class.forName(subSystemName).getFields();
            }



        }


    }
    @Override
    public void periodic() {
        if(triggerSave){
            triggerSave=false;
        }
        switch (telemetryLevel) {
            case DEBUG:
            case DIAGNOSTIC:
            case CONFIG:
                telemetry.addData(this.getName() + ":lastSave:",lastSave);
            case MATCH:
                // telemetry.addData(this.getName() + ":Command:", this.getCurrentCommand());

        }

    }
}
