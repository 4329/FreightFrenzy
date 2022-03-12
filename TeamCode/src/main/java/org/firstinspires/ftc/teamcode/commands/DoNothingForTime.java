package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DoNothingForTime extends CommandBase {
    private int timeToTakeInMilliseconds=0;
    private Telemetry telemetry;
    private ElapsedTime elapsedTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS) ;
    private boolean runOnce=false;

    @Override
    public void execute() {
        if (!runOnce){
            elapsedTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
            elapsedTime.startTime();
            runOnce=true;
        }
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        telemetry.addLine(this.getName()+":"+elapsedTime.toString());
        return timeToTakeInMilliseconds<=elapsedTime.milliseconds();
    }

    @Override
    public void initialize() {
        
    }

    public DoNothingForTime(int timeToTakeInMilliseconds, Telemetry telemetry) {
        this.timeToTakeInMilliseconds = timeToTakeInMilliseconds;
        this.telemetry =telemetry;
    }
}
