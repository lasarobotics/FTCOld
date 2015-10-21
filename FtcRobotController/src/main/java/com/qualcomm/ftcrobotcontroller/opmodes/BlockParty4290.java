package com.qualcomm.ftcrobotcontroller.opmodes;

import com.lasarobotics.library.drive.Tank;
import com.lasarobotics.library.util.MathUtil;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * OPMode to control FTC4290 robot from Block Party using legcay module
 */
public class BlockParty4290 extends OpMode {
    DcMotor mLeft;
    DcMotor mRight;
    DcMotor arma;
    DcMotor armb;
    DcMotor armc;
    Servo sLeft;
    Servo sRight;
    TouchSensor limit;

    DcMotorController.DeviceMode devMode;
    DcMotorController m1;
    DcMotorController m2;
    DcMotorController m3;

    double servopos = .5;
    @Override
    public void init() {
        mLeft = hardwareMap.dcMotor.get("mleft");
        mLeft.setDirection(DcMotor.Direction.REVERSE);
        mRight = hardwareMap.dcMotor.get("mright");
        arma = hardwareMap.dcMotor.get("arma");
        armb = hardwareMap.dcMotor.get("armb");
        armc = hardwareMap.dcMotor.get("armc");
        sLeft = hardwareMap.servo.get("sleft");
        sRight = hardwareMap.servo.get("sright");
        limit = hardwareMap.touchSensor.get("touch");

        m1 = hardwareMap.dcMotorController.get("m1");
        m2 = hardwareMap.dcMotorController.get("m2");
        m3 = hardwareMap.dcMotorController.get("m3");

        m1.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        m2.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        m3.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);

    }

    @Override
    public void loop() {
        Tank.motor2(mLeft, mRight, gamepad1.left_stick_y, gamepad1.right_stick_y);
        if(!limit.isPressed() || (gamepad2.left_stick_y > 0)){
            arma.setPower(gamepad2.left_stick_y);
            armb.setPower(gamepad2.left_stick_y);
            armc.setPower(gamepad2.left_stick_y);
        }
        if (gamepad2.right_stick_y > 0){
            servopos += .05;
        }
        else if (gamepad2.right_stick_y < 0){
            servopos -=.05;
        }
        MathUtil.coerce(0,1,servopos);
        sLeft.setPosition(servopos);
        sRight.setPosition(servopos);
    }
}
