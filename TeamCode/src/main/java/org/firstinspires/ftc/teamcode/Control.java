package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
    CONTROL LAYER

    Contains all controlling functions for the robot, just above the hardware layer
*/

public class Control {
    public double lPower = 0;
    public double rPower = 0;
    public double flywheelMultiplier = 1;
    public double driveMultiplier = 1;
    public boolean intakeStatus;
    public boolean loaderStatus;
    public boolean flywheelStatus;
    public boolean flywheelDown10pressed = false;
    public boolean flywheelUp10pressed = false;
    public boolean flywheelUp50pressed = false;
    public boolean flywheelDown50pressed = false;
    public boolean driveUp10pressed = false;
    public boolean driveUp50pressed = false;
    public boolean driveDown50pressed = false;
    public boolean driveDown10pressed = false;
    public boolean aPressed = false;
    public boolean bPressed = false;

    Vermithrax vermithrax;

    public void init(HardwareMap hwMap) {
        // Create an instance of, and initialize the drive controller1
        vermithrax = new Vermithrax(hwMap);
        vermithrax.init();
    }

    public void updateGamepad(Gamepad controller1, Gamepad controller2) throws InterruptedException {
        // Controller mapping
        lPower = -controller1.right_stick_y;
        rPower = -controller1.left_stick_y;

        boolean flywheelUp10 = controller2.dpad_up;
        boolean flywheelDown10 = controller2.dpad_down;
        boolean flywheelUp50 = controller2.dpad_right;
        boolean flywheelDown50 = controller2.dpad_left;
        boolean driveUp10 = controller1.dpad_up;
        boolean driveDown10 = controller1.dpad_down;
        boolean driveUp50 = controller1.dpad_right;
        boolean driveDown50 = controller1.dpad_left;

        intakeStatus = controller2.left_trigger > 0.3;
        loaderStatus = controller2.right_trigger > 0.3;
        flywheelStatus = controller2.left_bumper;
        if(controller2.a && !aPressed) vermithrax.toggleArmLift();
        if(controller2.b && !bPressed) vermithrax.toggleGripState();
        aPressed = controller2.a;
        bPressed = controller2.b;
        boolean emergencyUnload = controller2.x;

        // DPAD speed changes

        if (flywheelUp10 && !flywheelUp10pressed) {
            flywheelMultiplier += 0.1;
            flywheelUp10pressed = true;
        } if (flywheelDown10 && !flywheelDown10pressed) {
            flywheelMultiplier -= 0.1;
            flywheelDown10pressed = true;
        } if (flywheelUp50 && !flywheelUp50pressed) {
            flywheelMultiplier += 0.5;
            flywheelUp50pressed = true;
        } if (flywheelDown50 && !flywheelDown50pressed) {
            flywheelMultiplier -= 0.5;
            flywheelDown50pressed = true;
        }
        if (driveUp10 && !driveUp10pressed) {
            driveMultiplier += 0.1;
            driveUp10pressed = true;
        } if (driveDown10 && !driveDown10pressed) {
            driveMultiplier -= 0.1;
            driveDown10pressed = true;
        } if (driveUp50 && !driveUp50pressed) {
            driveMultiplier += 0.5;
            driveUp50pressed = true;
        } if (driveDown50 && !driveDown50pressed) {
            driveMultiplier -= 0.5;
            driveDown50pressed = true;
        }

        // Reset dpad values
        if (!flywheelUp10) flywheelUp10pressed = false;
        if (!flywheelDown10) flywheelDown10pressed = false;
        if (!flywheelUp50) flywheelUp50pressed = false;
        if (!flywheelDown50) flywheelDown50pressed = false;
        if (!driveUp10) driveUp10pressed = false;
        if (!driveDown10) driveDown10pressed = false;
        if (!driveUp50) driveUp50pressed = false;
        if (!driveDown50) driveDown50pressed = false;

        // Clipping max/min flywheel speed values
        if(flywheelMultiplier > 1) flywheelMultiplier = 1;
        if(flywheelMultiplier < 0) flywheelMultiplier = 0;
        if(driveMultiplier > 1) driveMultiplier = 1;
        if(driveMultiplier < 0) driveMultiplier = 0;

        // Clipping stick values
        if(lPower > 1) lPower = 1;
        if(lPower < -1) lPower = -1;
        if(rPower > 1) rPower = 1;
        if(rPower < -1) rPower = -1;

        // Set hardware state
        vermithrax.setDrivePower(lPower * driveMultiplier, rPower * driveMultiplier);

        if(flywheelStatus) vermithrax.setFlywheelPower(flywheelMultiplier);
        else if(emergencyUnload) vermithrax.setFlywheelPower(-1);
        else vermithrax.setFlywheelPower(0);

        if(intakeStatus) vermithrax.setIntakePower(1);
        else if(emergencyUnload) vermithrax.setIntakePower(-1);
        else vermithrax.setIntakePower(0);

        if(loaderStatus) vermithrax.setLoaderPower(1);
        else if(emergencyUnload) vermithrax.setLoaderPower(-1);
        else vermithrax.setLoaderPower(0);
    }

    public String getTelemetryStats() {
        return "FLYWHEEL MULTIPLIER: " + flywheelMultiplier + " DRIVE MULTIPLIER: " + driveMultiplier;
    }

    public void driveForTime(double lPower, double rPower, long time) throws InterruptedException {
        vermithrax.setDrivePower(lPower, rPower);
        Thread.sleep(time);
        vermithrax.setDrivePower(0, 0);
    }
}
