package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class WobbleClaw {
    public DcMotor rot;
    private Servo claw;
    private boolean on;
    private static final double MAX_POWER = 0.7;

    private static final double POS_DEFAULT = 1.0;
    private static final double POS_GRAB = 0.0;

    private static final int POS_UP = 0;
    private static final int POS_DOWN = -4191;
    private static final int POS_MID = -1607;

    public WobbleClaw(DcMotor _motor, Servo _claw) {
        rot = _motor; claw = _claw;

        rot.setDirection(DcMotorSimple.Direction.FORWARD);
        rot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
        if(DcMotor.RunMode.RUN_TO_POSITION == rot.getMode() && Math.abs(power) < 0.1)   {
            return;
        }
        rot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rot.setPower(MAX_POWER * power);
    }

    public void grab() { claw.setPosition(POS_GRAB); on = true; }
    public void release() { claw.setPosition(POS_DEFAULT); on = false; }

    public void up() {
        rot.setTargetPosition(POS_UP);
        rot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rot.setPower(1.0);
    }

    public void down() {
        rot.setTargetPosition(POS_DOWN);
        rot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rot.setPower(1.0);
    }

    public void mid() {
        rot.setTargetPosition(POS_MID);
        rot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rot.setPower(1.0);
    }

    public void update() {
        if(rot.getMode() == DcMotor.RunMode.RUN_TO_POSITION && Math.abs( rot.getCurrentPosition() - rot.getTargetPosition() ) < 25) {
            rot.setPower(0.0);
            rot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}
