package org.firstinspires.ftc.teamcode.opmode;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
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
    private int mode = 0; // 0 - normal; 1 - goToPos
    private boolean going = false;

    private Pose2d start = new Pose2d(-63 + 77.0733, -48 + 35.45123, 0);
    private Pose2d throwPos = new Pose2d(-63 + 52.4090, -48 + 27.0084, Math.toRadians(1.5));
    private Pose2d poseReset = new Pose2d(-63 + 61.8728, -48 + 10.4567, 0);

    @Override
    public void runOpMode()  {

        robot = new MugurelRR(hardwareMap, telemetry, this, runtime);

        gaju = new GamepadEx(gamepad1);
        andrei = new GamepadEx(gamepad2);

        robot.drive.setPoseEstimate(start);

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
            move(gaju.left_x, gaju.left_y, gaju.right_x, gaju.right_trigger.toButton(0.3), gaju.left_trigger.toButton(0.3));
            collect(andrei.b, andrei.a);
            goToPosition(gaju.y);
            if(gaju.dpad_left.pressed())  robot.drive.setPoseEstimate(poseReset);
            shoot(andrei.y, andrei.x, andrei.dpad_up, andrei.dpad_down);
            angleChange(andrei.right_bumper);
            woobleClaw(andrei.left_y, andrei.left_bumper, gaju.left_bumper,gaju.right_bumper);

            robot.collector.update();

            if(andrei.dpad_left.pressed())    robot.shooter.incAngle(0.05);
            if(andrei.dpad_right.pressed())    robot.shooter.incAngle(-0.05);

            telemetry.addData("position", robot.claw.rot.getCurrentPosition());
            telemetry.addData("angle", robot.shooter.angleChanger.getPosition());
            telemetry.addData("mode", mode);
            telemetry.update();

        }

    }

    private void setFace(Button front, Button back, Button left, Button right) {
        // Not available with roadrunner
    }

    private void move(Axis lx, Axis ly, Axis rx, Button smallPower, Button mediumPower) {
        if(mode == 1)   return;
        going = false;
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

    void goToPosition(Button x) {
        if(x.pressed()) {
            mode = 1 - mode;
            going = false;
        } else
            return;

        if(mode == 0 || going)   return;

//        robot.shooter.Up(true);
//        robot.collector.setState(0.0);
//        robot.collector.setStateEnd0(runtime.milliseconds() + 1000);
        robot.shooter.setState(1.0);

        going = true;
        Trajectory toShoot = robot.drive.trajectoryBuilder(robot.drive.getPoseEstimate())
                .lineToLinearHeading(throwPos)
                .build();
        robot.drive.followTrajectory(toShoot);
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
            robot.collector.setStateEnd0(runtime.milliseconds() + 1000);
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

    private void woobleClaw(Axis rotate, Button grab, Button mid, Button down) {
        robot.claw.changeState(grab.pressed());
        robot.claw.setRotatePower(-rotate.raw);
        if(mid.pressed()) { robot.claw.grab(); sleep(350); robot.claw.mid();  }
        if(down.pressed())  { robot.claw.down();  }
    }
}
