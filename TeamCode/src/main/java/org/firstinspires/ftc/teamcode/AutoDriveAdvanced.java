package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
    DRIVE LAYER

 Contains all logical game operations and utilizes the control layer for robot functions
*/

@Autonomous(name="Vermithrax_AutoDriveAdvanced", group="Autonomous")
public class AutoDriveAdvanced extends LinearOpMode {

    public Control control;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize Control Layer
        control = new Control();
        control.init(hardwareMap);
        // Await start event
        control.vermithrax.toggleGripState();
        control.vermithrax.setArmPosition(0);
        waitForStart();
        // Main game logic
        //control.vermithrax.initArm();
        try {
            control.driveForTime(0.75, 0.75, 900);
            Thread.sleep(1000);
            control.vermithrax.setFlywheelPower(1);
            Thread.sleep(3000);
            control.vermithrax.setLoaderPower(1);
            Thread.sleep(3000);
            control.vermithrax.setLoaderPower(0);
            control.vermithrax.setIntakePower(1);
            Thread.sleep(2000);
            control.vermithrax.setIntakePower(0);
            control.vermithrax.setLoaderPower(1);
            Thread.sleep(3000);
            control.vermithrax.setIntakePower(0);
            control.vermithrax.setLoaderPower(0);
            control.vermithrax.setFlywheelPower(0);
            control.driveForTime(0.75, 0.8, 750);
            control.vermithrax.initArm();
            Thread.sleep(1000);
            control.driveForTime(-0.2, 0.4, 500);
            Thread.sleep(1000);
            control.vermithrax.toggleArmLift();
            Thread.sleep(1000);
            control.vermithrax.toggleGripState();
            Thread.sleep(1000);
            control.vermithrax.toggleArmLift();
            control.driveForTime(0.2, -0.4, 500);
            Thread.sleep(1000);
            control.vermithrax.setArmPosition(0);
            Thread.sleep(1000);
        } catch (Exception e) {
            control.vermithrax.setFlywheelPower(0);
            control.vermithrax.setDrivePower(0, 0);
            control.vermithrax.setIntakePower(0);
            control.vermithrax.setLoaderPower(0);
            telemetry.addData("Status", "FATAL RUNTIME ERROR");
            telemetry.addData("ERROR", e.getStackTrace());
        }
        while(opModeIsActive()) {
            telemetry.addData("position", control.vermithrax.motorArm.getCurrentPosition());
            telemetry.update();
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
