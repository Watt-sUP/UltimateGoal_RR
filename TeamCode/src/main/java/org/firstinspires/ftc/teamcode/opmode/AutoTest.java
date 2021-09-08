package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.MugurelRR;
import org.firstinspires.ftc.teamcode.hardware.RingIdentifier;

import java.security.ProtectionDomain;
import java.util.Arrays;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name="AutoTest" , group="Linear Opmode")
//@Disabled
public class AutoTest extends LinearOpMode {
    public ElapsedTime runtime = new ElapsedTime();
    private MugurelRR robot;
    private int rnd = 0;
    private RingIdentifier ring;
    SampleMecanumDrive drive;

    Pose2d start = new Pose2d(-63, -48, 0);
    Pose2d parkPose = new Pose2d(-63 + 77.0733, -48 + 35.45123, 0);

    @Override
    public void runOpMode() throws InterruptedException {

        robot = new MugurelRR(hardwareMap, telemetry, this, runtime);
        ring = new RingIdentifier(hardwareMap);
        ring.setTelemetry(telemetry);
        drive = robot.drive;
        robot.shooter.setAngle(0.35);
        robot.shooter.Up(true);
        ring.init();
        drive.setPoseEstimate(start);

        telemetry.addData("Status", "Initialized");

        while (!opModeIsActive() && !isStopRequested()) {
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
        runtime.reset();

        waitForStart();

        if (isStopRequested()) return;

        /**
          * length = 17.75, width = 17.5
          * 1. read randomisation
          * 2. shoot rings
          * 3. ? collect more
          * 4. score first wobble
          * 5. go for second wobble
          * 6. score second wobble
          * 7. park
          */

//        robot.shooter.setState(1);
        rnd = ring.getRand();
        telemetry.addData("Rand", rnd);
        telemetry.update();

        if(rnd == 0)    scenario0();
        else if(rnd == 1)   scenario1();
        else if(rnd == 2)   scenario4();

//        Trajectory toShoot = drive.trajectoryBuilder(start)
//                .splineToLinearHeading(new Pose2d(-63 + 25.3, -48 + 13.32551, Math.toRadians(3)), Math.toRadians(90))
//                .build();
//        drive.followTrajectory(toShoot);



//        // Shoot rings
//        robot.shooter.pushRingSync();
//        robot.shooter.pushRingSync();
//        robot.shooter.pushRingSync();
//
//        robot.shooter.setState(0);
//            sleep(1500);
//        // Collect more rings
//
//
//        Trajectory toPark = drive.trajectoryBuilder(toSquare.end())
//                .lineToLinearHeading(parkPose)
//                .build();
//        drive.followTrajectory(toPark)



       /* Trajectory toSquare1 = drive.trajectoryBuilder(toShoot.end())
                .splineToLinearHeading(new Pose2d(-63 + 63.44664, -48 -10.25542, 0), 0)
                .build();
        drive.followTrajectory(toSquare1);

        sleep(1000);

        Trajectory toSquare2 = drive.trajectoryBuilder(toSquare1.end())
                .splineToLinearHeading(new Pose2d(-63 + 86.26319863181189, -48 + 11.82659794210107, Math.toRadians(2.99459)), 0)
                .build();
        drive.followTrajectory(toSquare2);

        sleep(1000);

        Trajectory toSquare3 = drive.trajectoryBuilder(toSquare2.end())
                .lineToLinearHeading(new Pose2d(-63 + 100, -40 -8.7063435793775, Math.toRadians(-50)))
                .build();
        drive.followTrajectory(toSquare3);

        */

//        sleep(1000);
//
//        Pose2d square = new Pose2d();
//        if(rnd == 0) {sleep(300);
//            square = new Pose2d(0, -60, 0);
//        } else if(rnd == 1) {
//            square = new Pose2d(24, -36, 0);
//        } else if(rnd == 2) {
//            square = new Pose2d(48, -60, 0);
//        }
//
//        Trajectory toSquare = drive.trajectoryBuilder(toShoot.end())
//                .splineToSplineHeading(square, 0)
//                .build();
//        drive.followTrajectory(toSquare);
//
//        // Score pre-loaded wobble
//        sleep(1000);
//
//        Trajectory toWobble2 = drive.trajectoryBuilder(toSquare.end())
//                .splineToSplineHeading(new Pose2d(-40, -24, Math.toRadians(180)), Math.toRadians(180))
//                .build();
//        drive.followTrajectory(toWobble2);
//
//        // Grab 2nd wobble
//        sleep(1000);
//
//        Trajectory toSquare2 = drive.trajectoryBuilder(toWobble2.end())
//                .splineToSplineHeading(square, 0)
//                .build();
//        drive.followTrajectory(toSquare2);
//
//        // Score 2nd wobble
//        sleep(1000);
//
//        Trajectory toPark = drive.trajectoryBuilder(toSquare2.end())
//                .splineToLinearHeading(new Pose2d(0, -37, 0), 0)
//                .build();
//        drive.followTrajectory(toPark);
    }

    void scenario0() {
        Pose2d square = new Pose2d(-63 + 63.44664, -48 -9.25542, 0);
        Pose2d w2drop = new Pose2d(-63 + 56.54664, -48 -10.0542, Math.toRadians(-37));
        Pose2d wobble2 = new Pose2d(-63 + 31.316362, -48 + 30.121708, Math.toRadians(180));
        Pose2d shootPose = new Pose2d(-14.5, -48, Math.toRadians(11.5));

        robot.shooter.setState(1);

        Trajectory toShoot = drive.trajectoryBuilder(start)
                .splineToLinearHeading( shootPose, 0)
                .build();
        drive.followTrajectory(toShoot);

        sleep(1000);
        robot.shooter.pushRingSync();
        robot.shooter.pushRingSync();
        robot.shooter.pushRingSync();
        robot.shooter.setState(0);

        Trajectory toSquare = drive.trajectoryBuilder(toShoot.end())
                .splineToLinearHeading(square, Math.toRadians(0))
                .build();
        drive.followTrajectory(toSquare);

        robot.claw.down();
        while(robot.claw.rot.isBusy());
        robot.claw.release();
        sleep(500);
        robot.claw.mid();


        Trajectory back2 = drive.trajectoryBuilder(toSquare.end())
                .back(12)
                .build();
        drive.followTrajectory(back2);

        robot.claw.down();

        Trajectory toW2 = drive.trajectoryBuilder(back2.end())
                .lineToLinearHeading(wobble2)
//                .addTemporalMarker(1, () -> { robot.claw.down(); })
                .build();
        drive.followTrajectory(toW2);

        sleep(200);
        robot.claw.grab();
        sleep(500);
        robot.claw.lowmid();


        Trajectory toSquare2 = drive.trajectoryBuilder(toW2.end())
                .lineToLinearHeading(w2drop)
                .build();
        drive.followTrajectory(toSquare2);

        robot.claw.down();
        while(robot.claw.rot.isBusy());
        robot.claw.release();
        sleep(500);
        robot.claw.up();

        Trajectory toPark = drive.trajectoryBuilder(toSquare2.end())
                .lineToLinearHeading(parkPose)
                .build();
        drive.followTrajectory(toPark);

    }

    void scenario1() {
        Pose2d square = new Pose2d(-63 + 86.26319863181189, -48 + 11.82659794210107, Math.toRadians(2.99459));
        Pose2d shootPose = new Pose2d(-5, -48, Math.toRadians(12.5));
        Pose2d collect = new Pose2d(-8, -35, Math.toRadians(180));
        Pose2d wobble2 = new Pose2d(-63 + 35, -48 + 30.1, Math.toRadians(175));
        Pose2d w2drop = new Pose2d(-63 + 82.26319863181189, -48 + 3.82659794210107, Math.toRadians(2.99459));
        Pose2d shootPose2 = new Pose2d(-10, -48, Math.toRadians(10));

        robot.shooter.setState(1.0);

        Trajectory toShoot = drive.trajectoryBuilder(start)
                .splineToLinearHeading(shootPose, Math.toRadians(45))
                .build();
        drive.followTrajectory(toShoot);

        sleep(1000);
        robot.shooter.pushRingSync();
        robot.shooter.pushRingSync();
        robot.shooter.pushRingSync();
//        sleep(200);

//        sleep(1000);

        robot.shooter.setState(0);
        robot.claw.lowmid();

        Trajectory toSquare = drive.trajectoryBuilder(toShoot.end())
                .splineToLinearHeading(square, Math.toRadians(0))
                .build();
        drive.followTrajectory(toSquare);

        robot.claw.down();
        while(robot.claw.rot.isBusy());
        robot.claw.release();
        sleep(200);
        robot.claw.mid();

        robot.shooter.Down(true);

        Trajectory back2 = drive.trajectoryBuilder(toSquare.end())
                .back(12)
                .build();
        drive.followTrajectory(back2);



        Trajectory toCollect = drive.trajectoryBuilder(back2.end())
                .addTemporalMarker(1, () -> {robot.collector.setState(1.0);})
                .splineToLinearHeading(collect, Math.toRadians(0))
                .build();
        drive.followTrajectory(toCollect);

        robot.claw.down();

        Trajectory Collect = drive.trajectoryBuilder(toCollect.end())
                .forward(8)
                .build();
        drive.followTrajectory(Collect);

        Trajectory toW2 = drive.trajectoryBuilder(Collect.end())
                .lineToLinearHeading(wobble2)
                .build();
        drive.followTrajectory(toW2);

        robot.shooter.Up(true);
        robot.collector.setState(0.0);

        Trajectory grabWb = drive.trajectoryBuilder(toW2.end())
                .forward(2.75, new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(5.0, DriveConstants.TRACK_WIDTH)
                                )
                        ),
                        new ProfileAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
        drive.followTrajectory(grabWb);

        sleep(200);
        robot.claw.grab();
        sleep(500);
        robot.claw.up();

        robot.shooter.setState(1);

        Trajectory toShoot2 = drive.trajectoryBuilder(grabWb.end())
                .lineToLinearHeading(shootPose2)
                .build();
        drive.followTrajectory(toShoot2);

        robot.shooter.pushRingSync();
        robot.shooter.setState(0);

        robot.claw.lowmid();

        Trajectory toSquare2 = drive.trajectoryBuilder(toShoot2.end())
                .lineToLinearHeading(w2drop)
                .build();
        drive.followTrajectory(toSquare2);

        robot.claw.down();
        while(robot.claw.rot.isBusy());
        robot.claw.release();
        sleep(500);
        robot.claw.up();

        Trajectory toPark = drive.trajectoryBuilder(toSquare2.end())
                .back(6)
                .build();
        drive.followTrajectory(toPark);


    }


    void scenario4() {
        DriveConstants.MAX_VEL = 58;

        Pose2d square = new Pose2d(46, -58, Math.toRadians(0));
        Pose2d wobble2 = new Pose2d(-30, -17, Math.toRadians(180));
        Pose2d collect = new Pose2d(-40, -34, Math.toRadians(0));
        Pose2d square2 = new Pose2d(41, -52, Math.toRadians(-50));
        Pose2d shootPose = new Pose2d(-5, -48, Math.toRadians(12.5));

//        robot.shooter.setState(1.0);

        /*Trajectory toShoot = drive.trajectoryBuilder(start)
                .splineToLinearHeading(shootPose, Math.toRadians(45))
                .build();
        drive.followTrajectory(toShoot);

        sleep(1000);
        robot.shooter.pushRingSync();
        robot.shooter.pushRingSync();
        robot.shooter.pushRingSync();*/

        robot.claw.lowmid();
        Trajectory toSquare = drive.trajectoryBuilder(start)
                .splineToLinearHeading(square, Math.toRadians(0))
                .build();
        drive.followTrajectory(toSquare);

        robot.claw.down();
        while(robot.claw.rot.isBusy());
        robot.claw.release();
        sleep(200);
        robot.claw.mid();

        Trajectory back2 = drive.trajectoryBuilder(toSquare.end())
                .back(19)
                .build();
        drive.followTrajectory(back2);


        Trajectory toWobble = drive.trajectoryBuilder(back2.end())
                .addTemporalMarker(1, () -> {robot.claw.down();})
                .splineToLinearHeading(wobble2, Math.toRadians(-145))
                .build();
        drive.followTrajectory(toWobble);

        Trajectory grabWb = drive.trajectoryBuilder(toWobble.end())
                .forward(1.75, new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(5.0, DriveConstants.TRACK_WIDTH)
                                )
                        ),
                        new ProfileAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
        drive.followTrajectory(grabWb);

        sleep(200);
        robot.claw.grab();
        sleep(500);
        robot.claw.mid();

//        robot.shooter.Down(true);
//        robot.collector.setState(1.0);

        robot.shooter.setState(1);
        robot.shooter.setAngle(0.35);

        Trajectory toRings = drive.trajectoryBuilder(grabWb.end())
                .splineToLinearHeading(collect, Math.toRadians(-47))
                .build();
        drive.followTrajectory(toRings);

        sleep(150);
        robot.shooter.pushRingSync();
        robot.shooter.pushRingSync();
        robot.shooter.pushRingSync();

        robot.shooter.setState(0);
        robot.shooter.Down(true);
        robot.collector.setState(1);

        Trajectory Collect = drive.trajectoryBuilder(toRings.end())
                .forward(18,
                        new MinVelocityConstraint(
                                Arrays.asList(
                                        new AngularVelocityConstraint(DriveConstants.MAX_ANG_VEL),
                                        new MecanumVelocityConstraint(15.0, DriveConstants.TRACK_WIDTH)
                                )
                        ),
                        new ProfileAccelerationConstraint(DriveConstants.MAX_ACCEL))
                .build();
        drive.followTrajectory(Collect);

        robot.shooter.setState(1);
        robot.shooter.setAngle(0.34);
        sleep(1000);
        robot.shooter.Up(true);
        sleep(200);
        robot.collector.setState(0.0);

        robot.shooter.pushRingSync();
        robot.shooter.pushRingSync();
        robot.shooter.pushRingSync();

        robot.shooter.Down(true);
        robot.collector.setState(1);

        robot.claw.lowmid();


        Trajectory toWb2 = drive.trajectoryBuilder(Collect.end())
                .addTemporalMarker(1, () -> {robot.claw.lowmid();})
                .lineToLinearHeading(square2)
                .build();
        drive.followTrajectory(toWb2);

        robot.claw.down();
        while(robot.claw.rot.isBusy());
        robot.claw.release();
        sleep(300);

        robot.claw.up();

        Trajectory toPark = drive.trajectoryBuilder(toWb2.end())
                .back(10)
                .build();
        drive.followTrajectory(toPark);









        /*Trajectory back2 = drive.trajectoryBuilder(toSquare.end())
                .back(12)
                .build();
        drive.followTrajectory(back2);

        robot.claw.down();

        Trajectory toW2 = drive.trajectoryBuilder(back2.end())
                .lineToLinearHeading(wobble2)
//                .addTemporalMarker(1, () -> { robot.claw.down(); })
                .build();
        drive.followTrajectory(toW2);
        sleep(200);
        robot.claw.grab();
        sleep(500);
        robot.claw.mid();*/
    }
}
