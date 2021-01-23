package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/*
    DRIVE LAYER

    Contains all logical game operations and utilizes the control layer for robot functions
*/

@Autonomous(name="Vermithrax_AutoDrive", group="Autonomous")
public class AutoDrive extends OpMode {

    public Control control;

    @Override
    public void init() {
        control = new Control();
        control.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void loop() {
        try {
            control.driveForTime(0.5,0.5, 1000);
            requestOpModeStop();
            telemetry.addData("Status", "RUNNING");
            telemetry.addData("ControllerData", control.getTelemetryStats());
        } catch (Exception e) {
            control.vermithrax.setFlywheelPower(0);
            control.vermithrax.setDrivePower(0, 0);
            control.vermithrax.setIntakeState(false);
            telemetry.addData("ControllerData", "FATAL RUNTIME ERROR");
            telemetry.addData("ERROR", e.getStackTrace());
        }
    }
}
