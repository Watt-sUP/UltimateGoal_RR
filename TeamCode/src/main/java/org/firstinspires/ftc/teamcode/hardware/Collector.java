package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Collector {
    private DcMotor motor;
    private boolean on, reverse;
    private static final double MAX_POWER = 1.0;
    private double endTime = 0.0;
    private boolean ended = true;
    private ElapsedTime runtime;

    public Collector(DcMotor _motor) {
        on = false;
        reverse = false;
        motor = _motor;
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        ended = true;
    }

    public void setRuntime(ElapsedTime _runtime) {runtime = _runtime;}

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

    public void setStateEnd0(double _endTime) {
        endTime = _endTime;
        ended = false;
    }

    public void update() {
        if(!ended && runtime.milliseconds() > endTime) {
            setState(0);
            ended = true;
        }
    }
}
