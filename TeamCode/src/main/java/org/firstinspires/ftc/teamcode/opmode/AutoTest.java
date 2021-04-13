package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.MugurelRR;
import org.firstinspires.ftc.teamcode.hardware.RingIdentifier;

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
        robot.shooter.setAngle(0.6);
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

        while(opModeIsActive());
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
//        if(rnd == 0) {
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
        Pose2d square = new Pose2d(-63 + 63.44664, -48 -10.25542, 0);
        Trajectory toSquare = drive.trajectoryBuilder(start)
//        Trajectory toSquare = drive.trajectoryBuilder(toShoot.end())
                .splineToLinearHeading(square, Math.toRadians(0))
                .build();
        drive.followTrajectory(toSquare);

        robot.claw.down();
        while(robot.claw.rot.isBusy());
        robot.claw.release();
        sleep(500);
        robot.claw.mid();

        Pose2d wobble2 = new Pose2d(-63 + 32.316362, -48 + 30.121708, Math.toRadians(180));

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

        Pose2d w2drop = new Pose2d(-63 + 56.54664, -48 -11.0542, Math.toRadians(-45));

        Trajectory toSquare2 = drive.trajectoryBuilder(toW2.end())
//        Trajectory toSquare = drive.trajectoryBuilder(toShoot.end())
                .lineToLinearHeading(w2drop)
                .build();
        drive.followTrajectory(toSquare2);

        robot.claw.down();
        while(robot.claw.rot.isBusy());
        robot.claw.release();
        sleep(500);
        robot.claw.mid();
    }

    void scenario1() {
        Pose2d square = new Pose2d(-63 + 86.26319863181189, -48 + 11.82659794210107, Math.toRadians(2.99459));
        Trajectory toSquare = drive.trajectoryBuilder(start)
//        Trajectory toSquare = drive.trajectoryBuilder(toShoot.end())
                .splineToLinearHeading(square, Math.toRadians(0))
                .build();
        drive.followTrajectory(toSquare);

        robot.claw.down();
        while(robot.claw.rot.isBusy());
        robot.claw.release();
        sleep(500);
        robot.claw.mid();

        Pose2d wobble2 = new Pose2d(-63 + 32.316362, -48 + 30.121708, Math.toRadians(180));

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
        robot.claw.mid();
    }

    void scenario4() {
        Pose2d square = new Pose2d(-63 + 104, -40 -8.7063435793775, Math.toRadians(-50));
        Trajectory toSquare = drive.trajectoryBuilder(start)
//        Trajectory toSquare = drive.trajectoryBuilder(toShoot.end())
                .splineToLinearHeading(square, Math.toRadians(0))
                .build();
        drive.followTrajectory(toSquare);

        robot.claw.down();
        while(robot.claw.rot.isBusy());
        robot.claw.release();
        sleep(500);
        robot.claw.mid();

        Pose2d wobble2 = new Pose2d(-63 + 31.216362, -48 + 26.121708, Math.toRadians(180));

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
        robot.claw.mid();
    }
}
