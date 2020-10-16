package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ControlInterpreter {
    public double drive;
    public double turn;
    public double lPower = 0;
    public double rPower = 0;
    public double flywheelSpeed = 0;
    public double max;
    public boolean flywheelUp10pressed = false;
    public boolean flywheelDown10pressed = false;
    public boolean flywheelUp50pressed = false;
    public boolean flywheelDown50pressed = false;


    DriveController dControl;

    public void init(HardwareMap hwMap) {
        // Create an instance of, and initialize the drive controller
        dControl = new DriveController(hwMap);
        dControl.init();
    }

    public void update(Gamepad controller) {
        // Controller mapping
        drive = -controller.left_stick_y;
        turn = controller.left_stick_x;
        boolean flywheelUp10 = controller.dpad_up;
        boolean flywheelDown10 = controller.dpad_down;
        boolean flywheelUp50 = controller.dpad_right;
        boolean flywheelDown50 = controller.dpad_left;

        // Drive interpretation
        lPower = drive + turn;
        rPower = drive - turn;
        max = Math.max(Math.abs(lPower), Math.abs(rPower));
        if (max > 1.0)
        {
            lPower /= max;
            rPower /= max;
        }
        dControl.setDrivePower(lPower,rPower);

        // DPAD speed changes
        if (flywheelUp10) {
            flywheelSpeed=+10;
        } else if (flywheelDown10) {
            flywheelSpeed-=10;
        } else if (flywheelUp50) {
            flywheelSpeed+=50;
        } else if (flywheelDown50) {
            flywheelSpeed+=50;
        }

        // Clipping max/min flywheel speed values
        if(flywheelSpeed > 100) {
            flywheelSpeed = 100;
        }
        if(flywheelSpeed < 0) {
            flywheelSpeed = 0;
        }

        dControl.setFlywheelPower(flywheelSpeed);
    }

    public String controllerData(Gamepad controller) {
        // Controller mapping
        drive = -controller.left_stick_y;
        turn = controller.left_stick_x;

        boolean flywheelUp10 = controller.dpad_up;
        boolean flywheelDown10 = controller.dpad_down;
        boolean flywheelUp50 = controller.dpad_right;
        boolean flywheelDown50 = controller.dpad_left;

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

        if (!flywheelUp10) {
            flywheelUp10pressed = false;
        } if (!flywheelDown10) {
            flywheelDown10pressed = false;
        } if (!flywheelUp50) {
            flywheelUp50pressed = false;
        } if (!flywheelDown50) {
            flywheelDown50pressed = false;
        }

        // Clipping max/min flywheel speed values
        if(flywheelSpeed > 1) {
            flywheelSpeed = 1;
        }
        if(flywheelSpeed < 0) {
            flywheelSpeed = 0;
        }

        return "Drive: " + drive + " Turn: " + turn + " Flywheel: " + flywheelSpeed;
    }
}
