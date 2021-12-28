package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BucketSystem extends SubsystemBase {
    private Telemetry telemetry;

    private DcMotorEx bucketMotor;

    public BucketSystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        bucketMotor = hardwareMap.get(DcMotorEx.class, "bucketMotor");
        bucketMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        bucketMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void up(){bucketMotor.setPower(0.1);}

    public void down(){bucketMotor.setPower(-0.1);}

    public void level(){}

    public void stop(){bucketMotor.setPower(0);}

}
