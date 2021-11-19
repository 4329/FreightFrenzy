package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

public class MoveTubeSpinnerCommand extends CommandBase {
    private final TubeSpinnerSystem tubeSpinner;
    private double moveToDegree;

    public MoveTubeSpinnerCommand(TubeSpinnerSystem TubeSpinner, double MoveToDegree){
        tubeSpinner = TubeSpinner;
        addRequirements(TubeSpinner);
        moveToDegree=MoveToDegree;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){

    }

    @Override
    public boolean isFinished(){
        return false;
    }

}
