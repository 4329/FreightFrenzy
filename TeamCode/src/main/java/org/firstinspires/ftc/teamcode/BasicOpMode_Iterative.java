/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Basic: Iterative OpMode", group="Iterative Opmode")

public class BasicOpMode_Iterative extends OpMode
{
    // Declare OpMode members.
    RobotHardware robot   = new RobotHardware();   //declaration
    RobotController robotController = new RobotController(robot);

    private ElapsedTime runtime = new ElapsedTime();
    // private DcMotor leftFrontDrive = null;
    // private DcMotor leftBackDrive = null;
    // private DcMotor rightFrontDrive = null;
    // private DcMotor rightBackDrive = null;
    //private DcMotor carouselMotor =null;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initializing");
        robot.init(hardwareMap);
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).

        // Control Hub Port 3
        //leftFrontDrive  = hardwareMap.get(DcMotor.class, "left_front_drive");

        // Control Hub Port 1
        //rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");

        //  Control Hub Port 0
        //leftBackDrive  = hardwareMap.get(DcMotor.class, "left_back_drive");

        // Control Hub Port 2
        //rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive"); //
        // Expansion hub port 0
        //carouselMotor = hardwareMap.get(DcMotor.class,"carousel_motor");
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        //leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        //rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        //leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        //rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);

        // Set up for using encoders for speed control
        //leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double leftPower;
        double rightPower;

        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        // Positive value of drive goes forward
        double drive = -gamepad1.left_stick_y;   //Flip sign so that push up causes positive power for forward
        // Positive value of turn goes right
        double turn  =  gamepad1.right_stick_x;
        // Positive Left stick X strafes right
        double strafe = gamepad1.left_stick_x;

        telemetry.addData("Left Stick Y",gamepad1.left_stick_y);
        telemetry.addData("Left Stick X",gamepad1.left_stick_x);
        telemetry.addData("Right Stick Y",gamepad1.right_stick_y);
        telemetry.addData("Right Stick X",gamepad1.right_stick_x);


        telemetry.addData("dpad_left",gamepad2.dpad_left);
        telemetry.addData("dpad_right",gamepad2.dpad_right);



        leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
        rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

        double leftFrontPower = Range.clip(strafe+drive+turn,-1,+1);
        double leftBackPower = Range.clip(-strafe+drive+turn,-1,+1);
        double rightFrontPower = Range.clip(-strafe+drive-turn,-1,+1);
        double rightBackPower = Range.clip(strafe+drive-turn,-1,+1);



        // Send calculated power to wheels
        robot.leftFrontDrive.setPower(leftFrontPower);
        robot.rightFrontDrive.setPower(rightFrontPower);
        robot.leftBackDrive.setPower(leftBackPower);
        robot.rightBackDrive.setPower(rightBackPower);

        // Use Dbap on Operator to enable carousel spin directions
        if(gamepad2.dpad_left)
        {
           robot.carouselMotor.setPower(1);
        }
        if(gamepad2.dpad_right)
        {
            robot.carouselMotor.setPower(-1);
        }



        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
