package org.firstinspires.ftc.teamcode.commands;

import static java.lang.Double.min;
import static java.lang.Math.signum;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PController;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

public class RotateTubeToDegree extends CommandBase {
    private final TubeSpinnerSystem tubeSpinnerSystem;
    private final Double degreeToRotate;
    private Telemetry telemetry;
    private int loopCount=0;
    PController pController=new PController(1.0);
    double outputPID;
    static double MAX_ROTATE_DEGREES=0.1;
    double rotateDegrees;

    public RotateTubeToDegree(TubeSpinnerSystem tubeSpinnerSystem, Double degreeToRotate, Telemetry telemetry) {
        this(tubeSpinnerSystem,degreeToRotate);
        this.telemetry = telemetry;
    }

    public RotateTubeToDegree(TubeSpinnerSystem tubeSpinnerSystem, Double degreeToRotate) {
        this.tubeSpinnerSystem = tubeSpinnerSystem;
        this.degreeToRotate=degreeToRotate;
        addRequirements(tubeSpinnerSystem);
    }


    @Override
    public void initialize() {
        pController.setSetPoint(degreeToRotate);
        if(telemetry != null){
            telemetry.addLine("RotateTubeToDegree Initialize");
            telemetry.addData("degreeToRotate", this.degreeToRotate);
            telemetry.addLine("   Init done");
        }
    }

    @Override
    public void execute() {
        outputPID=pController.calculate(tubeSpinnerSystem.getDegrees());
        // Pull of sign of output with abs to check for max rotate,
        // and then put sign back in with signum
        rotateDegrees=min(Math.abs(outputPID),MAX_ROTATE_DEGREES) * Math.signum(outputPID);
        tubeSpinnerSystem.rotateTube(rotateDegrees);
        if (telemetry != null) {
            telemetry.addLine("Execute");
            telemetry.addData("loopCount",loopCount++);
            telemetry.addData("rotateDegrees",rotateDegrees);
            telemetry.addData("getDegrees",tubeSpinnerSystem.getDegrees());
            telemetry.addData("outputPID",outputPID);
            telemetry.addData("targetDegrees",pController.getSetPoint());
        }
    }

    @Override
    public void end(boolean interrupted) {
        if (telemetry != null)
        {
            telemetry.addLine("End");
            telemetry.addData("interrupted",interrupted);
        }

    }

    @Override
    public boolean isFinished() {
        return pController.atSetPoint();
    }
}
