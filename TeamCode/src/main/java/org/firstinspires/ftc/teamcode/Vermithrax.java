package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/*
    HARDWARE LAYER

    Does not do any logical operations. Base layer for direct communication with hardware.
    Only directly initializes and communicates with sensors and motors.
*/

public class Vermithrax {
    HardwareMap hwMap;

    public BNO055IMU imu;

    public DcMotor motorFL;
    public DcMotor motorBL;
    public DcMotor motorFR;
    public DcMotor motorBR;

    public DcMotor motorFly1;
    public DcMotor motorFly2;

    public DcMotor motorIntake;
    public CRServo servoIntake;

    public DcMotor motorArm;
    public Servo servoArm;

    public boolean armAlreadyUp = true;
    public boolean armClampOpen = true;

    public Vermithrax(HardwareMap hardwareMap) {
        this.hwMap = hardwareMap;
    }

    public void init() {
        // Left motors
        motorFL = hwMap.get(DcMotor.class, "motorFL");
        motorBL = hwMap.get(DcMotor.class, "motorBL");

        // Right Motors
        motorFR = hwMap.get(DcMotor.class, "motorFR");
        motorBR = hwMap.get(DcMotor.class, "motorBR");

        // Flywheel motors
        motorFly1 = hwMap.get(DcMotor.class, "motorFly1");
        motorFly2 = hwMap.get(DcMotor.class, "motorFly2");

        // Intake motors
        motorIntake = hwMap.get(DcMotor.class, "motorIntake");
        servoIntake = hwMap.get(CRServo.class, "servoIntake");

        // Arm motors
        motorArm = hwMap.get(DcMotor.class, "motorArm");
        servoArm = hwMap.get(Servo.class, "servoArm");

        //  left motors (confirmed)
        motorFL.setDirection(DcMotorSimple.Direction.REVERSE); // beep
        motorBL.setDirection(DcMotorSimple.Direction.REVERSE);

        //  right motors (confirmed)
        motorFR.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBR.setDirection(DcMotorSimple.Direction.FORWARD);

        // flywheel motors (confirmed)
        motorFly1.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFly2.setDirection(DcMotorSimple.Direction.FORWARD);

        // arm motors (unconfirmed)
        motorArm.setDirection(DcMotorSimple.Direction.FORWARD);

        // Intake motors (uncomfirmed)
        motorIntake.setDirection(DcMotorSimple.Direction.REVERSE);
        servoIntake.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set all power to 0 by default
        motorFL.setPower(0);
        motorBL.setPower(0);
        motorFR.setPower(0);
        motorBR.setPower(0);
        motorFly1.setPower(0);
        motorFly2.setPower(0);
        motorIntake.setPower(0);
        servoIntake.setPower(0);
        motorArm.setPower(0);

        // Set encoder mode
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFly1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFly2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hwMap.get(BNO055IMU.class,"imu");
        imu.initialize(parameters);

    }

    // A simple drive function to set the power of both motor arrays at the same time
    // TODO: WARNING MAKE SURE THAT MOTOR PAIRS 1+2 and 3+4 ARE SUPPOSED TO TURN IN THE SAME DIRECTION BEFORE TESTING
    public void setDrivePower(double leftpower, double rightpower) {
        motorFL.setPower(leftpower);
        motorFR.setPower(rightpower);
        motorBL.setPower(leftpower);
        motorBR.setPower(rightpower);
    }

    // a simple flywheel function to set the power to the flywheel
    public void setFlywheelPower(double flypower){
        motorFly1.setPower(flypower);
        motorFly2.setPower(flypower);
    }

    public void setIntakePower(double speed) {
        motorIntake.setPower(speed);
    }

    public void setLoaderPower(double speed) {
        servoIntake.setPower(speed);
    }

    public void toggleGripState() {
        if(!armClampOpen) {
            servoArm.setPosition(0);
            armClampOpen = true;
        }
        else {
            servoArm.setPosition(0.55);
            armClampOpen = false;
        }
    }

    public void toggleArmLift() throws InterruptedException {
        if(!armAlreadyUp) {
            motorArm.setPower(-1);
            Thread.sleep(1000);
            motorArm.setPower(0);
            armAlreadyUp = true;
        } else {
            motorArm.setPower(1);
            Thread.sleep(1000);
            motorArm.setPower(0);
            armAlreadyUp = false;
        }
    }
}


