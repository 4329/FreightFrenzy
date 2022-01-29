package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;

public class ArmToPosition extends CommandBase {
    private ArmSystem armSystem;
    private ArmSystem.ArmPosition armPosition;
    private double maxSeconds=0;
    private Telemetry telemetry;
    private ElapsedTime elapsedTime;

    @Override
    public void initialize() {
        armSystem.goToPosition(armPosition);
        elapsedTime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
    }

    @Override
    public boolean isFinished() {
        telemetry.addLine(this.getName()+":running");
        telemetry.addData(this.getName()+":",armSystem.currentArmPosition());
        return (armSystem.currentArmPosition() == armPosition)
                || (elapsedTime.seconds()>maxSeconds);
    }

    public ArmToPosition(ArmSystem armSystem,
                         ArmSystem.ArmPosition armPosition,
                         double maxSeconds,
                         Telemetry telemetry) {
        this.armSystem = armSystem;
        this.armPosition = armPosition;
        this.maxSeconds = maxSeconds;
        this.telemetry = telemetry;
        addRequirements(armSystem);
    }
}
