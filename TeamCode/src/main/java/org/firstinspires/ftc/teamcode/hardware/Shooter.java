package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Rotation;

public class Shooter {
    public DcMotorEx motor;
    public Servo lift, push, angleChanger;

    private double endTime;
    private boolean on, pushing, tower;
    private static final double MAX_POWER = 1.0;
    private static final double MAX_TIME = 600;

    private static final double POS_LIFT_DOWN = 0.8;
    private static final double POS_LIFT_UP = 0.25;

    private static final double POS_ANGLE_TOWER = 0.34;
    private static final double POS_ANGLE_POWERSHOT = 0.44;

    private static final double POS_PUSH_RING = 0.5;
    private static final double POS_PUSH_RESET = 0.22;

    public ElapsedTime runtime;

    public Shooter(DcMotorEx _left, Servo _push, Servo _lift, Servo _angleChanger) {
        motor = _left;

        MotorConfigurationType motor_cfg = motor.getMotorType().clone();
        motor_cfg.setMaxRPM(6600);
        motor_cfg.setTicksPerRev(28);
        motor_cfg.setGearing(1);
        motor_cfg.setOrientation(Rotation.CCW);
        motor_cfg.setAchieveableMaxRPMFraction(1.0);
        motor.setMotorType(motor_cfg);

        push = _push;
        lift = _lift;
        angleChanger = _angleChanger;

        on = false;
        pushing = false;

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setDirection(DcMotorSimple.Direction.FORWARD);

        lift.setPosition(POS_LIFT_DOWN);

        push.setPosition(POS_PUSH_RESET);

        tower = true;
        angleChanger.setPosition(POS_ANGLE_TOWER);
    }

    public void changeState(boolean change) {
        if(change) {
            on = !on;
            if(on)
                motor.setPower(MAX_POWER);
            else
                motor.setPower(0.0);
        }
    }

    public void setState(double power) {
        motor.setPower(MAX_POWER * power);
        if(power < 0.001)   on = false;
        else                on = true;
    }

    public void pushRing(boolean change) {
        if( Math.abs(lift.getPosition() - POS_LIFT_DOWN) < 0.001 ) return;
        if(!pushing && change) {
            push.setPosition(POS_PUSH_RING);
            pushing = true;
            endTime = runtime.milliseconds() + MAX_TIME;
        }

        if(runtime.milliseconds() > endTime) {
            pushing = false;
            push.setPosition(POS_PUSH_RESET);
        }
    }

    public void pushRingSync() {
        if( Math.abs(lift.getPosition() - POS_LIFT_DOWN) < 0.001 ) return;

        double endTime = runtime.milliseconds() + 700;
        push.setPosition(POS_PUSH_RING);
        while (runtime.milliseconds() < endTime) { ; }
        push.setPosition(POS_PUSH_RESET);
        while (runtime.milliseconds() < endTime + 700) { ; }
    }

    public void Up (boolean change){
        if(change)  lift.setPosition(POS_LIFT_UP);
    }

    public void Down (boolean change){
        if(change)  lift.setPosition(POS_LIFT_DOWN);
    }
    
    public void anglePowershot() {
        angleChanger.setPosition(POS_ANGLE_POWERSHOT);
    }

    public void angleTower() {
        angleChanger.setPosition(POS_ANGLE_TOWER);
    }

    public void setAngle(double ang) { angleChanger.setPosition(ang); }

    public void incAngle(double ang) { angleChanger.setPosition(ang + angleChanger.getPosition() ); }

    public void changeAngleState(boolean change) {
        if(change) {
            tower = !tower;
            if(tower)   angleChanger.setPosition(POS_ANGLE_TOWER);
            else        angleChanger.setPosition(POS_ANGLE_POWERSHOT);
        }
    }
}
