package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.BucketSystem;

public class BucketToAngle extends CommandBase {
    private BucketSystem bucketSystem;
    private double newAngleForBucket;
    private Telemetry telemetry;

    public BucketToAngle(BucketSystem bucketSystem,
                         double newAngleForBucket,
                         Telemetry telemetry) {
        this.bucketSystem = bucketSystem;
        this.newAngleForBucket = newAngleForBucket;
        this.telemetry = telemetry;
        addRequirements(bucketSystem);
    }

    @Override
    public void initialize() {
        this.bucketSystem.setBucketAngleFromHomePosition(newAngleForBucket);
    }

    @Override
    public boolean isFinished() {
        return bucketSystem.atPosition;
    }
}
