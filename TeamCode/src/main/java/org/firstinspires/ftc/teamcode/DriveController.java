package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
 *
 * This code is written assuming the following motor configuration (by index):
 *
 *     FRONT
 *     0---2
 *     1---3
 *     ---
 *     BACK
 *
 *
 * flywheel: 4,5
 *
 *
 * */


public class DriveController {
    HardwareMap hwMap;
    public DcMotor motorTL;
    public DcMotor motorBL;
    public DcMotor motorTR;
    public DcMotor motorBR;

    public DcMotor motorFly1;
    public DcMotor motorFly2;



    public DriveController(HardwareMap hardwareMap) {
        hwMap = hardwareMap;
    }

    public void init() {
        // Left motors
        motorTL = hwMap.get(DcMotor.class, "motor0");
        motorBL = hwMap.get(DcMotor.class, "motor1");

        // Right Motors
        motorTR = hwMap.get(DcMotor.class, "motor2");
        motorBR = hwMap.get(DcMotor.class, "motor3");

        // Flywheel motors
        motorFly1 = hwMap.get(DcMotor.class, "motor4");
        motorFly2 = hwMap.get(DcMotor.class, "motor5");

        // Forward left motors (unconfirmed)
        motorTL.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBL.setDirection(DcMotorSimple.Direction.FORWARD);

        // Reverse right motors (unconfirmed)
        motorTR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        //forward flywheel motors (unconfirmed)
        motorFly1.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFly2.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set all power to 0 by default
        motorTL.setPower(0);
        motorBL.setPower(0);
        motorTR.setPower(0);
        motorBR.setPower(0);
        motorFly1.setPower(0);
        motorFly2.setPower(0);


        // Set encoder mode
        motorTL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorTR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFly1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFly2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }

    // A simple drive function to set the power of both motor arrays at the same time
    // TODO: WARNING MAKE SURE THAT MOTOR PAIRS 1+2 and 3+4 ARE SUPPOSED TO TURN IN THE SAME DIRECTION BEFORE TESTING
    public void setDrivePower(double leftpower, double rightpower) {
        motorTL.setPower(leftpower);
        motorBL.setPower(leftpower);

        motorTR.setPower(rightpower);
        motorBR.setPower(rightpower);
    }

    // a simple flywheel function to set the power to the flywheel
    public void setFlywheelPower(double flypower){
        motorFly1.setPower(flypower);
        motorFly2.setPower(flypower);
    }
}
