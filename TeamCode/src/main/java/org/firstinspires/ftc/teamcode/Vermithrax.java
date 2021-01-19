package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Vermithrax {
    HardwareMap hwMap;
    public DcMotor motorFL;
    public DcMotor motorBL;
    public DcMotor motorFR;
    public DcMotor motorBR;

    public DcMotor motorFly1;
    public DcMotor motorFly2;

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

        // Forward left motors (unconfirmed)
        motorFL.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBL.setDirection(DcMotorSimple.Direction.FORWARD);

        // Reverse right motors (unconfirmed)
        motorFR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        //reverse flywheel motors (confirmed)
        motorFly1.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFly2.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set all power to 0 by default
        motorFL.setPower(0);
        motorBL.setPower(0);
        motorFR.setPower(0);
        motorBR.setPower(0);
        motorFly1.setPower(0);
        motorFly2.setPower(0);

        // Set encoder mode
        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFly1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFly2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // A simple drive function to set the power of both motor arrays at the same time
    // TODO: WARNING MAKE SURE THAT MOTOR PAIRS 1+2 and 3+4 ARE SUPPOSED TO TURN IN THE SAME DIRECTION BEFORE TESTING
    public void setDrivePower(double leftpower, double rightpower) {
        motorFL.setPower(leftpower);
        motorBL.setPower(leftpower);

        motorFR.setPower(rightpower);
        motorBR.setPower(rightpower);
    }

    // a simple flywheel function to set the power to the flywheel
    public void setFlywheelPower(double flypower){
        motorFly1.setPower(flypower);
        motorFly2.setPower(flypower);
    }

    public void setIntakeState(boolean state) {

    }

    public void driveForDistance(double speed, double leftdistance, double rightdistance) {

    }
}


