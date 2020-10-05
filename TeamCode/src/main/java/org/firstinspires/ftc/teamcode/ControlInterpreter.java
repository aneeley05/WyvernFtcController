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
        return "Drive: " + drive + " Turn: " + turn + " Flywheel: " + flywheelSpeed;
    }
}
