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

    @Override
    public void runOpMode() throws InterruptedException {

        robot = new MugurelRR(hardwareMap, telemetry, this, runtime);
        SampleMecanumDrive drive = robot.drive;

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

        Pose2d start = new Pose2d(-63, -48, 0);
        drive.setPoseEstimate(start);

        Trajectory traj1 = drive.trajectoryBuilder(start)
                .splineTo(new Vector2d(-0.62, -36), 0)
                .build();

        Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                .splineToLinearHeading(new Pose2d(36, 12, Math.toRadians(180)), 0)
                .build();




        drive.followTrajectory(traj1);
        drive.followTrajectory(traj2);
    }
}
