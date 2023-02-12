package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class OldAutonomous {

    public static void main(String[] args) {

        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(52.09776244331133, 52.09776244331133, 4.302544843778562, 4.302544843778562, 11.5)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, -60.5, Math.toRadians(90)))
                                .lineTo(new Vector2d(-22.75, -60.5))
                                .splineToConstantHeading(new Vector2d(-13, -20), Math.toRadians(90))
                                .splineToSplineHeading(new Pose2d(-12, -12, Math.toRadians(135)), Math.toRadians(90))
                                .forward(11.23)
                                .back(11.23)
                                .turn(Math.toRadians(45))
                                .forward(49)
                                .back(25)
                                .turn(Math.toRadians(-135))
                                .forward(11.1)
                                .back(11.1)
                                .turn(Math.toRadians(-45))
                                .build()
                );
        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL).addEntity(myBot).start();
    }
}
