package com.example.meepmeep;

import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

public class AlternateTrajectory {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(52.09776244331133, 52.09776244331133, 4.302544843778562, 4.302544843778562, 11.5)
                .followTrajectorySequence(drive ->
                                drive.trajectorySequenceBuilder(new Pose2d(-35, -60.5, Math.toRadians(90)))
                                        .lineTo(new Vector2d(-22.75, -60.5))
                                        .splineToConstantHeading(new Vector2d(-13, -20), Math.toRadians(90))
                                        .splineToSplineHeading(new Pose2d(-12, -12, Math.toRadians(135)), Math.toRadians(90))

                                        .forward(7)
                                        .back(7)
                                        .turn(Math.toRadians(45))
                                        .forward(45)

                                        .back(45)
                                        .turn(Math.toRadians(-45))
                                        .forward(7)
                                        .back(7)


                                        .turn(Math.toRadians(45))
                                        .lineTo(new Vector2d(-12, -30))
                                        .splineToConstantHeading(new Vector2d(-35, -35), Math.toRadians(180))
                                        .lineTo(new Vector2d(-60, -35))
                                        .build()

            );
            meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL).addEntity(myBot).start();
    }
}
