package org.firstinspires.ftc.teamcode.opmode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.gamepad.Axis;
import org.firstinspires.ftc.teamcode.gamepad.Button;
import org.firstinspires.ftc.teamcode.hardware.Config;
import org.firstinspires.ftc.teamcode.hardware.Mugurel;

@Autonomous(name="AutoField" , group="Linear Opmode")
//@Disabled
public class AutoField extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();
    public DcMotor rotBrat;
    public Servo stransBrat;
    private org.firstinspires.ftc.teamcode.hardware.Mugurel robot;

    @Override
    public void runOpMode()  {

        robot = new org.firstinspires.ftc.teamcode.hardware.Mugurel(hardwareMap, telemetry, this, runtime);

        rotBrat = hardwareMap.get(DcMotor.class, org.firstinspires.ftc.teamcode.hardware.Config.rotBrat);
        stransBrat = hardwareMap.get(Servo.class, org.firstinspires.ftc.teamcode.hardware.Config.stransBrat);

        stransBrat.setPosition(1.0);

       // robot.setOpmode(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        runtime.reset();

        robot.runner.setFace(0);
        robot.runner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        // run until the end of the match (driver presses STOP)

        while (opModeIsActive()){

            telemetry.addData("Status", "Run Time: " + runtime.toString());

//            rotBrat.setPower(-0.8);
//            sleep(1400);
//            rotBrat.setPower(0);
//
//            stransBrat.setPosition(0.0);
//            sleep(1000);
//
//            rotBrat.setPower(0.8);
//            sleep(1000);
//            rotBrat.setPower(0);
//
//            sleep(1000);



            robot.runner.moveWithAngle(0, -1, 0, 1);
            sleep(1600);
            robot.runner.moveWithAngle(0, 0, 0, 1);
           /* sleep(1000);
            robot.runner.moveWithAngle(0, 1, 0, 1);
            sleep(400);
            robot.runner.moveWithAngle(0, 0,0, 1);

//            robot.shooter.changeState(true);
            robot.shooter.startShoot(1);
            for(int i = 0; i < 3; i++) {
                sleep(500);
                robot.shooter.pushRing2();
                sleep(1000);
                robot.shooter.pushRing2();
            }


            robot.shooter.startShoot(0);

            robot.runner.moveWithAngle(0, -1, 0, 0.5);
            sleep(400);
            robot.runner.moveWithAngle(0, 0, 0, 1);

            rotBrat.setPower(-0.8);
            sleep(600);
            rotBrat.setPower(0);
*/
            break;
        }

    }

    private void setFace(Button front, Button back, Button left, Button right) {
        if (front != null && front.pressed()) robot.runner.setFace(0);
        else if (back != null && back.pressed()) robot.runner.setFace(Math.PI);
        else if (left != null && left.pressed()) robot.runner.setFace(Math.PI / 2.0);
        else if (right != null && right.pressed()) robot.runner.setFace(-Math.PI / 2.0);
    }

    private void move(Axis lx, Axis ly, Axis rx, Button smallPower, Button mediumPower, Button dl, Button dr) {
        double modifier = 1.0;
        if (smallPower != null && smallPower.raw) modifier = 0.23;
        if (mediumPower != null && mediumPower.raw)  modifier = 0.5;

        final double drive_y = -robot.runner.scalePower(ly.raw);
        final double drive_x = -robot.runner.scalePower(lx.raw);
        final double turn = robot.runner.scalePower(rx.raw);

        if (dr != null && dr.raw) robot.runner.moveWithAngle(-1,0,0, modifier);
        else if (dl != null && dl.raw) robot.runner.moveWithAngle(1,0,0, modifier);
        else robot.runner.moveWithAngle(drive_x, drive_y, turn, modifier);
    }

    private void collect(Button startCollector, Button reverseCollector) {
        robot.collector.changeState(startCollector.pressed());
        robot.collector.reverseCollector(reverseCollector.pressed());
    }

    private void shoot(Button startShooter, Button push) {
        robot.shooter.changeState(startShooter.pressed());
        robot.shooter.pushRing(push.pressed());
    }
}
