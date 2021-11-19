package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

public class TubeSpinnerReportPositionCommand extends CommandBase {
    private final TubeSpinnerSystem armSpinner;

    public TubeSpinnerReportPositionCommand(TubeSpinnerSystem ArmSpinner, String TelemetryString){
        armSpinner = ArmSpinner;
        addRequirements(armSpinner);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        telemetry.addData("TubeSpinnerPosition",armSpinner.getPosition());
    }
    @Override
    public boolean isFinished(){
        return  false;
    }

}
