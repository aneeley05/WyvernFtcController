package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
    DRIVE LAYER

    Contains all logical game operations and utilizes the control layer for robot functions

    NOTE: Driver is controller 1, ring master is controller 2
*/


@TeleOp(name = "Vermithrax_RemoteDrive", group = "OpModes")
public class RemoteDrive extends OpMode {

    public Control control;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        control = new Control();
        control.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        telemetry.addData("Status", "Starting...");
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        try {
            control.updateGamepad(gamepad1, gamepad2);
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

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        control.vermithrax.setDrivePower(0, 0);
        control.vermithrax.setFlywheelPower(0);
    }
}
