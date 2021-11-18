package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ArmSpinnerSubsystem;

public class ArmSpinnerReportPositionCommand extends CommandBase {
    private final ArmSpinnerSubsystem armSpinner;

    public ArmSpinnerReportPositionCommand(ArmSpinnerSubsystem ArmSpinner, String TelemetryString){
        armSpinner = ArmSpinner;
        addRequirements(armSpinner);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        armSpinner.getposition();
    }
    @Override
    public boolean isFinished(){
        return  false;
    }

}
