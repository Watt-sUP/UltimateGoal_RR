package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class MugurelRR {

    public SampleMecanumDrive drive;
    public Collector collector;
    public Shooter shooter;
    public WobbleClaw claw;

    public HardwareMap hardwareMap;
    public Telemetry telemetry;
    public LinearOpMode opMode;
    public ElapsedTime runtime;

     public void init() {
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        collector = new Collector(hardwareMap.get(DcMotor.class, Config.collector));

        shooter = new Shooter(
                hardwareMap.get(DcMotor.class, Config.left_shoot),
                hardwareMap.get(Servo.class, Config.push),
                hardwareMap.get(Servo.class, Config.lift),
                hardwareMap.get(Servo.class, Config.angleChanger)
        );

        claw = new WobbleClaw(
               hardwareMap.get(DcMotor.class, Config.rotBrat),
               hardwareMap.get(Servo.class, Config.stransBrat)
        );
    }

    public MugurelRR() {}

    public MugurelRR(HardwareMap hm){
        hardwareMap = hm;
        init();
    }

    public MugurelRR(HardwareMap hm, Telemetry t, LinearOpMode opmode, ElapsedTime _runtime) {
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
