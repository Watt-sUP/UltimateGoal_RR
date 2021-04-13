package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.MugurelRR;
import org.firstinspires.ftc.teamcode.hardware.RingIdentifier;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name="RingD" , group="Linear Opmode")
//@Disabled
public class RingDectection extends LinearOpMode {
    public ElapsedTime runtime = new ElapsedTime();
    private MugurelRR robot;
    private int rnd = 0;
    private RingIdentifier ring;

    @Override
    public void runOpMode() throws InterruptedException {

//        robot = new MugurelRR(hardwareMap, telemetry, this, runtime);
        ring = new RingIdentifier(hardwareMap);
        ring.setTelemetry(telemetry);
//        SampleMecanumDrive drive = robot.drive;
//        robot.shooter.setAngle(0.6);
//        robot.shooter.Up(true);
        ring.init();
        // Read randomisation
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

        while(opModeIsActive()) {
            ;
        }


//        Pose2d start = new Pose2d(-63, -48, 0);
//        Pose2d parkPose = new Pose2d(-63 + 77.0733, -48 + 35.45123, 0);
//        drive.setPoseEstimate(start);
//
//        Trajectory toShoot = drive.trajectoryBuilder(start)
//                .lineToLinearHeading(new Pose2d(-63 + 25.3, -48 + 13.32551, Math.toRadians(3)))
//                .build();
//        drive.followTrajectory(toShoot);



//        // Shoot rings
//        robot.shooter.pushRingSync();
//        robot.shooter.pushRingSync();
//        robot.shooter.pushRingSync();
//
//        robot.shooter.setState(0);
//
//        // Collect more rings
//
//
//        Pose2d square = new Pose2d();
//        if(rnd == 0) {
//            square = new Pose2d(-63 + 63.44664, -48 -10.25542, 0);
//        } else if(rnd == 1) {
//            square = new Pose2d(-63 + 100, -40 -8.7063435793775, Math.toRadians(-50));
//        } else if(rnd == 2) {
//            square = new Pose2d(-63 + 86.26319863181189, -48 + 11.82659794210107, Math.toRadians(2.99459));
//        }
//
//        Trajectory toSquare = drive.trajectoryBuilder(toShoot.end())
//              .lineToLinearHeading(square)
//              .build();
//        drive.followTrajectory(toSquare);
//
//        robot.claw.down();
//        robot.claw.release();
//        sleep(500);
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
}
