package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class IntakeSystem extends SubsystemBase {
    public static TelemetrySystem.TelemetryLevel telemetryLevel= TelemetrySystem.TelemetryLevel.MATCH;
    public static double expelPower=.5;
    public static double intakePower=-1.0;

    private Telemetry telemetry;
    private DcMotorEx intakeMotor;

    public void takeIn(){intakeMotor.setPower(intakePower);}

    public void stop(){intakeMotor.setPower(0);}

    public void expel(){intakeMotor.setPower(expelPower);}

    public IntakeSystem(HardwareMap hardwareMap, Telemetry telemetry) {

        this.telemetry=telemetry;
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void periodic() {

    }
}
