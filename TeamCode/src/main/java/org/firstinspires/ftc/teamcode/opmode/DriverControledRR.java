package org.firstinspires.ftc.teamcode.opmode;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.gamepad.Axis;
import org.firstinspires.ftc.teamcode.gamepad.Button;
import org.firstinspires.ftc.teamcode.gamepad.GamepadEx;
import org.firstinspires.ftc.teamcode.hardware.Mugurel;
import org.firstinspires.ftc.teamcode.hardware.MugurelRR;

@TeleOp(name="Driver Controled RR" , group="Linear Opmode")
//@Disabled
public class DriverControledRR extends LinearOpMode {

    public ElapsedTime runtime = new ElapsedTime();
    private MugurelRR robot;

    private GamepadEx gaju, andrei;

    @Override
    public void runOpMode()  {

        robot = new MugurelRR(hardwareMap, telemetry, this, runtime);

        gaju = new GamepadEx(gamepad1);
        andrei = new GamepadEx(gamepad2);

       // robot.setOpmode(this);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        runtime.reset();

        waitForStart();
        // run until the end of the match (driver presses STOP)

        while (opModeIsActive()){
            gaju.update();
            andrei.update();

            telemetry.addData("Status", "Run Time: " + runtime.toString());

//            setFace(gaju.y, gaju.a, gaju.x, gaju.b);
            move(gaju.left_x, gaju.left_y, gaju.right_x, gaju.left_trigger.toButton(0.3), gaju.right_trigger.toButton(0.3));
            collect(andrei.b, andrei.a);
            shoot(andrei.y, andrei.x, andrei.dpad_up, andrei.dpad_down);
            angleChange(andrei.right_bumper);
            woobleClaw(andrei.left_y, andrei.left_bumper);

            telemetry.update();
        }

    }

    private void setFace(Button front, Button back, Button left, Button right) {
        // Not available with roadrunner
    }

    private void move(Axis lx, Axis ly, Axis rx, Button smallPower, Button mediumPower) {
        double modifier = 1.0;
        if (smallPower != null && smallPower.raw) modifier = 0.23;
        if (mediumPower != null && mediumPower.raw)  modifier = 0.5;

        final double drive_y = ly.raw * modifier;
        final double drive_x = lx.raw * modifier;
        final double turn = rx.raw * modifier;

        robot.drive.setWeightedDrivePower(
                new Pose2d( -drive_y, -drive_x, -turn ) );

        robot.drive.update();

//        Pose2d poseEstimate = robot.drive.getPoseEstimate();
//        telemetry.addData("x", poseEstimate.getX());
//        telemetry.addData("y", poseEstimate.getY());
//        telemetry.addData("heading", poseEstimate.getHeading());
    }

    private void collect(Button startCollector, Button reverseCollector) {
        robot.collector.changeState(startCollector.pressed());
        robot.collector.reverseCollector(reverseCollector.pressed());
    }

    private void shoot(Button startShooter, Button push, Button up, Button down) {
        robot.shooter.changeState(startShooter.pressed());
        robot.shooter.pushRing(push.pressed());
//        robot.shooter.Up(up.pressed());
//        robot.shooter.Down(down.pressed());

        if(up.pressed()) {
            robot.shooter.Up(true);
            robot.collector.setState(0.0);
            robot.shooter.setState(1.0);
        }

        if(down.pressed()) {
            robot.shooter.Down(true);
            robot.shooter.setState(0.0);
            robot.collector.setState(1.0);
        }
    }

    private void angleChange(Button angleChange) {
        robot.shooter.changeAngleState(angleChange.pressed());
    }

    private void woobleClaw(Axis rotate, Button grab) {
        robot.claw.changeState(grab.pressed());
        robot.claw.setRotatePower(-rotate.raw);
    }
}
