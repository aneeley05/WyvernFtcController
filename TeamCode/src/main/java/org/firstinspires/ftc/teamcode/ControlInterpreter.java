package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ControlInterpreter {
    public double lPower = 0;
    public double rPower = 0;
    public double flywheelSpeed = 0;
    public double max;
    public boolean flywheelDown10pressed = false;
    public boolean flywheelUp10pressed = false;
    public boolean flywheelUp50pressed = false;
    public boolean flywheelDown50pressed = false;

    Vermithrax vermithrax;

    public void init(HardwareMap hwMap) {
        // Create an instance of, and initialize the drive controller
        vermithrax = new Vermithrax(hwMap);
        vermithrax.init();
    }

    public void update(Gamepad controller) {
        // Controller mapping
        lPower = -controller.left_stick_y;
        rPower = -controller.right_stick_y;

        boolean flywheelUp10 = controller.dpad_up;
        boolean flywheelDown10 = controller.dpad_down;
        boolean flywheelUp50 = controller.dpad_right;
        boolean flywheelDown50 = controller.dpad_left;

        boolean intakeToggle = controller.right_trigger > 0.3;

        // DPAD speed changes

        if (flywheelUp10 && !flywheelUp10pressed) {
            flywheelSpeed += 0.1;
            flywheelUp10pressed = true;
        } if (flywheelDown10 && !flywheelDown10pressed) {
            flywheelSpeed -= 0.1;
            flywheelDown10pressed = true;
        } if (flywheelUp50 && !flywheelUp50pressed) {
            flywheelSpeed += 0.5;
            flywheelUp50pressed = true;
        } if (flywheelDown50 && !flywheelDown50pressed) {
            flywheelSpeed -= 0.5;
            flywheelDown50pressed = true;
        }

        // Reset dpad values
        if (!flywheelUp10) flywheelUp10pressed = false;
        if (!flywheelDown10) flywheelDown10pressed = false;
        if (!flywheelUp50) flywheelUp50pressed = false;
        if (!flywheelDown50) flywheelDown50pressed = false;

        // Clipping max/min flywheel speed values
        if(flywheelSpeed > 1) flywheelSpeed = 1;
        if(flywheelSpeed < 0) flywheelSpeed = 0;

        // Set hardware state
        vermithrax.setFlywheelPower(flywheelSpeed);
        vermithrax.setDrivePower(lPower, rPower);
//        vermithrax.setIntakeState(intakeToggle);
    }

    public String getTelemetryStats() {
        return "FLYWHEEL: " + flywheelSpeed + " lDrive: " + lPower + " rDrive: " + rPower;
    }
}


