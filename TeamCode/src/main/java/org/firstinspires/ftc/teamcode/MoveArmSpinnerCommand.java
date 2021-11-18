package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ArmSpinnerSubsystem;

public class MoveArmSpinnerCommand extends CommandBase {
    private final ArmSpinnerSubsystem armSpinner;

    public MoveArmSpinnerCommand(ArmSpinnerSubsystem ArmSpinner){
        armSpinner = ArmSpinner;
        addRequirements(ArmSpinner);
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
