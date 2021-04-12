package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Collector {
    private DcMotor motor;
    private boolean on, reverse;
    private static final double MAX_POWER = 1.0;

    public Collector(DcMotor _motor) {
        on = false;
        reverse = false;
        motor = _motor;
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void changeState(boolean change) {
        if(change) {
            on = !on;
            if(on)  reverse = false;
            if(on)  motor.setPower(MAX_POWER);
            else    motor.setPower(0.0);
        }
    }

    public void reverseCollector(boolean change){
        if(change){
          reverse = !reverse;
          if(reverse) on = false;
          if(reverse) motor.setPower(-MAX_POWER);
          else        motor.setPower(0.0);
        }
    }

    public void setState(double power) {
        if(power < -0.001)  reverse = true;
        else if(power > 0.001)  on = true;
        else    reverse = on = false;
        motor.setPower(MAX_POWER * power);
    }
}
