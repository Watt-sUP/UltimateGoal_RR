package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.MugurelRR;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name="AutoTest" , group="Linear Opmode")
//@Disabled
public class AutoTest extends LinearOpMode {
    public ElapsedTime runtime = new ElapsedTime();
    private MugurelRR robot;
    private int rnd = 2;

    @Override
    public void runOpMode() throws InterruptedException {

        robot = new MugurelRR(hardwareMap, telemetry, this, runtime);
        SampleMecanumDrive drive = robot.drive;
        robot.shooter.setAngle(0.6);
        robot.shooter.Up(true);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

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

        robot.shooter.setState(1);

        Pose2d start = new Pose2d(-63, -48, 0);
        Pose2d parkPose = new Pose2d(-63 + 77.0733, -48 + 35.45123, 0);
        drive.setPoseEstimate(start);

        // Read randomisation

        Trajectory toShoot = drive.trajectoryBuilder(start)
                .lineToLinearHeading(new Pose2d(-63 + 24.87185, -48 + 13.32551, Math.toRadians(5)))
                .build();
        drive.followTrajectory(toShoot);

        // Shoot rings
        robot.shooter.pushRingSync();
        robot.shooter.pushRingSync();
        robot.shooter.pushRingSync();

        robot.shooter.setState(0);

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
