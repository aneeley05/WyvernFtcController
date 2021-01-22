package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
    DRIVE LAYER

    Contains all logical game operations and utilizes the control layer for robot functions
*/


@TeleOp(name = "Vermithrax_RemoteDrive", group = "OpModes")
public class RemoteDrive extends OpMode {

    public ElapsedTime runtime;
    public Control control;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        runtime = new ElapsedTime();
        control = new Control();
        control.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
        telemetry.addData("Status", "Starting...");
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        try {
            control.updateGamepad(gamepad1);
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("ControllerData", control.getTelemetryStats());
        } catch (Exception e) {
            control.vermithrax.setFlywheelPower(0);
            control.vermithrax.setDrivePower(0, 0);
            telemetry.addData("ControllerData", "FATAL RUNTIME ERROR");
            telemetry.addData("ERROR", e.getStackTrace());
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        control.vermithrax.setDrivePower(0, 0);
        control.vermithrax.setFlywheelPower(0);
    }
}