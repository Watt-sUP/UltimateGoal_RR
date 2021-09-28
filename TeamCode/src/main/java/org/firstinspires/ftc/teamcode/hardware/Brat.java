package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Brat {

    public DcMotor ext, rot;
    public Servo ser;

    public Brat(DcMotor ext, DcMotor rot, Servo ser) {
        this.ext = ext;
        this.rot = rot;
        this.ser = ser;

        this.ext.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.ser.setPosition(0.5);
    }

    public void cutie(double pos) {
        ser.setPosition(pos);
    }

    public void move(double xPow, double yPow) {
        rot.setPower(xPow);
        ext.setPower(yPow);
    }

}
