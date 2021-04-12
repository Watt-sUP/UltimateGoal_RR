package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class WobbleClaw {
    private DcMotor rot;
    private Servo claw;
    private boolean on;
    private static final double MAX_POWER = 0.4;

    private static final double POS_DEFAULT = 0.0;
    private static final double POS_GRAB = 1.0;

    private static final int POS_UP = 0;
    private static final int POS_DOWN = 0;

    public WobbleClaw(DcMotor _motor, Servo _claw) {
        rot = _motor; claw = _claw;

        rot.setDirection(DcMotorSimple.Direction.FORWARD);
        rot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rot.setPower(0);

        claw.setPosition(POS_GRAB);
        on = true;
    }

    public void changeState(boolean change) {
        if(change) {
            on = !on;
            if(on)  claw.setPosition(POS_GRAB);
            else    claw.setPosition(POS_DEFAULT);
        }
    }

    public void setRotatePower(double power) {
        rot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rot.setPower(MAX_POWER * power);
    }

    public void grab() { claw.setPosition(POS_GRAB); on = true; }
    public void release() { claw.setPosition(POS_DEFAULT); on = false; }

    public void up() {
        rot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rot.setTargetPosition(POS_UP);
        rot.setPower(1.0);
    }

    public void down() {
        rot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rot.setTargetPosition(POS_DOWN);
        rot.setPower(1.0);
    }

    public void update() {
        if(rot.getMode() == DcMotor.RunMode.RUN_TO_POSITION && Math.abs( rot.getCurrentPosition() - rot.getTargetPosition() ) < 25) {
            rot.setPower(0.0);
            rot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
