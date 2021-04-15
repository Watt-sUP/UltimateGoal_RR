package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Mugurel {

    public Runner runner;
    public Collector collector;
    public Shooter shooter;
    public WobbleClaw claw;

    public HardwareMap hardwareMap;
    public Telemetry telemetry;
    public LinearOpMode opMode;
    public ElapsedTime runtime;

     public void init() {
        runner = new Runner(
                hardwareMap.get(DcMotorEx.class, Config.left_back),
                hardwareMap.get(DcMotorEx.class, Config.left_front),
                hardwareMap.get(DcMotorEx.class, Config.right_back),
                hardwareMap.get(DcMotorEx.class, Config.right_front)
        );

        collector = new Collector(hardwareMap.get(DcMotorEx.class, Config.collector));

        shooter = new Shooter(
                hardwareMap.get(DcMotorEx.class, Config.left_shoot),
                hardwareMap.get(Servo.class, Config.push),
                hardwareMap.get(Servo.class, Config.lift),
                hardwareMap.get(Servo.class, Config.angleChanger)
        );

        claw = new WobbleClaw(
               hardwareMap.get(DcMotorEx.class, Config.rotBrat),
               hardwareMap.get(Servo.class, Config.stransBrat)
        );
    }

    public Mugurel () {}

    public Mugurel (HardwareMap hm){
        hardwareMap = hm;
        init();
    }

    public Mugurel(HardwareMap hm, Telemetry t, LinearOpMode opmode, ElapsedTime _runtime) {
        hardwareMap = hm;
        telemetry = t;
        opMode = opmode;
        runtime = _runtime;
        init();
        shooter.runtime = runtime;
    }

    public void setTelemetry (Telemetry _t) { telemetry = _t; }
    public void setOpmode(LinearOpMode _o) { opMode = _o; }
}
