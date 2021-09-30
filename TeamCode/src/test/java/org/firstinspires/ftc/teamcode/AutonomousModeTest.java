package org.firstinspires.ftc.teamcode;

import static org.junit.Assert.*;

import org.junit.Test;

public class AutonomousModeTest {
    private AutonomousMode testObject = new AutonomousMode();
    @Test
    public void turntoangle_smalldistance(){
        boolean keepTurning = testObject.shouldKeepTurning(0, 0.5, RobotDirection.TURN_RIGHT);
        assertFalse(keepTurning);
    }
    @Test
    public void turntoangle_smallnegativedistance(){
        boolean keepTurning = testObject.shouldKeepTurning(0, -0.5, RobotDirection.TURN_LEFT);
        assertFalse(keepTurning);
    }
    @Test
    public void turntoangle_0to90(){
        boolean keepTurning = testObject.shouldKeepTurning(90, 0, RobotDirection.TURN_LEFT);
        assertTrue(keepTurning);
    }
    @Test
    public void turntoangle_90to91(){
        boolean keepTurning = testObject.shouldKeepTurning(90, 91, RobotDirection.TURN_LEFT);
        assertFalse(keepTurning);
    }
    @Test
    public void turntoangle_91to90(){
        boolean keepTurning = testObject.shouldKeepTurning(91, 90, RobotDirection.TURN_LEFT);
        assertTrue(keepTurning);
    }
    @Test
    public void turntoangle_neg90toneg91(){
        boolean keepTurning = testObject.shouldKeepTurning(-90, -91, RobotDirection.TURN_RIGHT);
        assertFalse(keepTurning);
    }
    @Test
    public void turntoangle_neg91toneg90(){
        boolean keepTurning = testObject.shouldKeepTurning(-91, -90, RobotDirection.TURN_RIGHT);
        assertTrue(keepTurning);
    }
    @Test
    public void turntoangle_90toneg5(){
        boolean keepTurning = testObject.shouldKeepTurning(0, 90, RobotDirection.TURN_RIGHT);
        assertTrue(keepTurning);
    }

    @Test
    public void turnToAngle_90To5() {
        boolean keepTurning = testObject.shouldKeepTurning(5, 90, RobotDirection.TURN_RIGHT);
        assertTrue(keepTurning);
    }
}