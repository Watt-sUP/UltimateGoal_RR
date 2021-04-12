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

    public WobbleClaw(DcMotor _motor, Servo _claw) {
        rot = _motor; claw = _claw;

        rot.setDirection(DcMotorSimple.Direction.FORWARD);
        rot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rot.setPower(0);

        claw.setPosition(POS_DEFAULT);
        on = false;
    }

    public void changeState(boolean change) {
        if(change) {
            on = !on;
            if(on)  claw.setPosition(POS_GRAB);
            else    claw.setPosition(POS_DEFAULT);
        }
    }

    public void setRotatePower(double power) {
        rot.setPower(MAX_POWER * power);
    }
}
