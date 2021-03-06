package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

/*
    DRIVE LAYER

 Contains all logical game operations and utilizes the control layer for robot functions
*/

@Autonomous(name="Vermithrax_AutoDriveObjectDetect", group="Autonomous")//
public class AutoDriveAdvancedObjectDetection extends LinearOpMode {

    public Control control;
    private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Quad";
    private static final String LABEL_SECOND_ELEMENT = "Single";

    private static final String VUFORIA_KEY =
            "AbEIUTT/////AAABmefEObgf5kOCsDDbz8T4tPt5cfmVWne/Dio9Xj9dBeog2fWfBTI/Th3bfB00aCoT7gd6YcnsRVSyqrDjQ4LUDc1PTo6UIqOY8dcG1LYIwPJ2pnar+ZedvtemhwCfONb3zocwMgry/jVFuR5CRP/BIjQQ9d68R5ecpci9Cyg0TZaIyl0jFmli+lmilwrsezCZKqRGHtJBxyTPnxr6fj0qV7Amcdbp4SRfsbG/D6UXoQOd7sKz+7iAAmPLnheCqRREbBFgTrK91b3z+8CPhW68rjdm3JTg9pmtXjGMD8rDM253betMLFaMk4OSeXOqtgQw5bJUCNORl1vw9ni+VJDGaybUf3v/cPb24eR0G6D1ikHQ";

    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData(">", "DO NOT START YET");
        telemetry.update();
        // Initialize Control Layer
        control = new Control();
        control.init(hardwareMap);
        initVuforia();
        initTfod();

        if (tfod != null) {
            tfod.activate();
            // Uncomment the following line if you want to adjust the magnification and/or the aspect ratio of the input images.
            //tfod.setZoom(2.5, 1.78);
        }

        // Await start event
        control.vermithrax.toggleGripState();
        control.vermithrax.setArmPosition(0);
        if(tfod != null) {
            tfod.activate();
        }
        telemetry.addData(">", "Press Play to start mode");
        telemetry.update();
        waitForStart();


        int loopIter = 0;
        int quadsFound = 0;
        int singlesFound = 0;
        int noneFound = 0;
        telemetry.addData(">", "Starting Recognition Loop");
        while(opModeIsActive() && loopIter < 23) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                Thread.sleep(50);
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        if(recognition.getLabel().toUpperCase() == "QUAD") quadsFound += 1;
                        else if(recognition.getLabel().toUpperCase() == "SINGLE") singlesFound += 1;
                        else noneFound += 1;
                    }
                    telemetry.update();
                }
            }
            loopIter += 1;
        }

        int dropoffSquare = 2;

        if(quadsFound > singlesFound && quadsFound > noneFound) {
            dropoffSquare = 3;
            telemetry.addData(">", "Found a likely result of QUAD over 21 iterations");
        } else if(singlesFound > quadsFound && singlesFound > noneFound) {
            dropoffSquare = 2;
            telemetry.addData(">", "Found a likely result of SINGLE over 21 iterations");
        } else {
            dropoffSquare = 1;
            telemetry.addData(">", "Found a likely result of NO RINGS over 21 iterations");
        }

        // Main game logic
        //control.vermithrax.initArm();
        try {
            // Drive forward to prep shot
            control.driveForTime(0.75, 0.75, 900);
            Thread.sleep(1000);
            // Spin up flywheel and then loader
            control.vermithrax.setFlywheelPower(1);
            Thread.sleep(3000);
            control.vermithrax.setLoaderPower(1);
            Thread.sleep(3000);
            // Turn off loader and turn on intake for second ring
            control.vermithrax.setLoaderPower(0);
            control.vermithrax.setIntakePower(1);
            Thread.sleep(2000);
            // Turn off intake and turn on loader to fire second ring
            control.vermithrax.setIntakePower(0);
            control.vermithrax.setLoaderPower(1);
            Thread.sleep(3000);
            // Turn everything off
            control.vermithrax.setIntakePower(0);
            control.vermithrax.setLoaderPower(0);
            control.vermithrax.setFlywheelPower(0);
            // Drive to the appropriate drop zone
            if(dropoffSquare == 1) {
                control.driveForTime(0.75, 0.8, 750);
                control.vermithrax.initArm();
                Thread.sleep(1000);
                control.driveForTime(-0.2, 0.4, 500);
                // Rotate to face drop zone// MAKE THIS
            } else if(dropoffSquare == 2) {
                control.driveForTime(0.75, 0.8, 750);
                control.vermithrax.initArm();
                Thread.sleep(1000);
                control.driveForTime(-0.2, 0.4, 500);
                // Rotate to face drop zone
            } else if(dropoffSquare == 3) {
                control.driveForTime(0.75, 0.8, 750);
                control.vermithrax.initArm();
                Thread.sleep(1000);
                control.driveForTime(-0.2, 0.4, 500);
                control.driveForTime(0.6, 0.8, 1200);
                control.vermithrax.initArm();
                Thread.sleep(1000);
                control.driveForTime(-0.2, 0.4, 500); // Rotate to face drop zone
                control.driveForTime(-0.6, -0.6, 900); // Back up to line
                Thread.sleep(1000);
            }
            Thread.sleep(1000);
            control.vermithrax.toggleArmLift(); // Drop arm
            Thread.sleep(1000);
            control.vermithrax.toggleGripState(); // Let go of wobble
            Thread.sleep(1000);
            control.vermithrax.toggleArmLift(); // Move arm back up
            control.driveForTime(0.2, -0.4, 500); // Rotate back to line
            Thread.sleep(1000);
            control.vermithrax.setArmPosition(0); // De-init arm
            Thread.sleep(1000);
            if(dropoffSquare == 3) {
                control.driveForTime(-0.6, -0.6, 800);
                Thread.sleep(1000);
            }
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
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.6f;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
