package org.firstinspires.ftc.teamcode.commands;

import static java.lang.Thread.sleep;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class NothingCommand extends CommandBase {

    private Telemetry telemetry;
    private int execLoop = 0;
    private String cmdName;
    private Boolean finished = false;
    int maxLoops=10000;

    public NothingCommand(Telemetry telemetry, String cmdName) {
        this.telemetry = telemetry;
        this.cmdName = cmdName;
    }

    public NothingCommand(Telemetry telemetry,String cmdName,int maxLoops){
        this(telemetry,cmdName);
        this.maxLoops=maxLoops;
    }
    @Override
    public void initialize() {
        telemetry.addLine("Init");
        telemetry.addData("cmdName", cmdName);
    }

    @Override
    public void execute() {
        this.execLoop+=1;
        telemetry.addLine("exec");
        telemetry.addData("cmdName", cmdName);
        telemetry.addData("execLoop", execLoop);
    }

    @Override
    public boolean isFinished() {
        if(execLoop == maxLoops) {
            telemetry.addLine("Finished");
            return true;
        }
        else
        {
            telemetry.addData("Not Finished", execLoop);
            return false;
        }
    }
}
