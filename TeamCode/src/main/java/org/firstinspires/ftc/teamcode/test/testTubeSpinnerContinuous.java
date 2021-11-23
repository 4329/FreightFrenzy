package org.firstinspires.ftc.teamcode.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultArmCommand;
import org.firstinspires.ftc.teamcode.commands.RotateTubeContinuous;
import org.firstinspires.ftc.teamcode.subsystems.TubeSpinnerSystem;

@TeleOp(name = "Test TubeSpinner Commands",group = "3")
public class testTubeSpinnerContinuous extends CommandOpMode {
    private TubeSpinnerSystem tubeSpinnerSystem;
    private GamepadEx operator;
    private RotateTubeContinuous rotateTube;
    private final double rotateDegree = .5;


    @Override
    public void initialize() {
        tubeSpinnerSystem = new TubeSpinnerSystem(hardwareMap, "TubeSpinner",0);
        operator = new GamepadEx(gamepad2);

        // This lamba technique wil run the lamba each pass to get a new value to pass to the command
        rotateTube = new RotateTubeContinuous(tubeSpinnerSystem,
                () -> {
                    double degree =0;
                    if (operator.getButton(GamepadKeys.Button.RIGHT_BUMPER))
                    {
                        telemetry.addLine("Right Bumper");
                        degree =this.rotateDegree;
                    } else if (gamepad2.left_bumper){
                        telemetry.addLine("Left Bumper");
                        degree =-this.rotateDegree;
                    }
                    else{
                        telemetry.addLine("No Bumper");
                        degree =0.0;
                    }
                    return degree;
                },
                telemetry);


        schedule(rotateTube);
        register(tubeSpinnerSystem);

    }


}
