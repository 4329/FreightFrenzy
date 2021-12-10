package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PController;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.ArmSystem;

public class ArmToDegree extends CommandBase {
    private PController pController = new PController(.8);
    private double degreeTarget;
    private ArmSystem armSystem;
    private Telemetry telemetry;

    public ArmToDegree(double degreeTarget, ArmSystem armSystem, Telemetry telemetry) {
        this.degreeTarget = degreeTarget;
        this.armSystem = armSystem;
        this.telemetry = telemetry;
        addRequirements(armSystem);
    }
    double gravityAdjustPower(){
        return ((90-armSystem.getArmHomeDegrees())/90 * .01);

    }
    @Override
    public void initialize() {
        pController.setSetPoint(degreeTarget);
    }

    @Override
    public void execute() {
        double output= pController.calculate(armSystem.getArmDegrees());
        double motorPower = output+ gravityAdjustPower();
        telemetry.addData("output",output);
        telemetry.addData("armDegrees",armSystem.getArmDegrees());
        telemetry.addData("motorPower",motorPower);
        armSystem.setPower(motorPower);
    }

    @Override
    public boolean isFinished() {
        telemetry.addData("At Setpoint",pController.atSetPoint());
        // return pController.atSetPoint();
        return false;
    }
}
