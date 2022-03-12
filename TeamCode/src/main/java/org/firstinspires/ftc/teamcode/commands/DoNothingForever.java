package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

public class DoNothingForever extends CommandBase {
    @Override
    public boolean isFinished() {
        return false;
    }
}
