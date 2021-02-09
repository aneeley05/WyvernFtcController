package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
    DRIVE LAYER

 Contains all logical game operations and utilizes the control layer for robot functions
*/

@Autonomous(name="Vermithrax_AutoDrive", group="Autonomous")
public class AutoDrive extends LinearOpMode {

    public Control control;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize Control Layer
        control = new Control();
        control.init(hardwareMap);
        // Await start event
        control.vermithrax.toggleGripState();
        waitForStart();
        // Main game logic
        try {
            control.driveForTime(0.4, 3000);
        } catch (Exception e) {
            control.vermithrax.setFlywheelPower(0);
            control.vermithrax.setDrivePower(0, 0);
            control.vermithrax.setIntakePower(0);
            control.vermithrax.setLoaderPower(0);
            telemetry.addData("Status", "FATAL RUNTIME ERROR");
            telemetry.addData("ERROR", e.getStackTrace());
        }
    }

//    @Override
//    public void init() {
//        control = new Control();
//        control.init(hardwareMap);
//        telemetry.addData("Status", "Initialized");
//    }
//
//    @Override
//    public void loop() {
//        try {
//            control.driveForTime(0.5,0.5, 1000);
//            requestOpModeStop();
//            telemetry.addData("Status", "RUNNING");
//            telemetry.addData("ControllerData", control.getTelemetryStats());
//        } catch (Exception e) {
//            control.vermithrax.setFlywheelPower(0);
//            control.vermithrax.setDrivePower(0, 0);
//            control.vermithrax.setIntakeState(false);
//            telemetry.addData("ControllerData", "FATAL RUNTIME ERROR");
//            telemetry.addData("ERROR", e.getStackTrace());
//        }
//    }
}
