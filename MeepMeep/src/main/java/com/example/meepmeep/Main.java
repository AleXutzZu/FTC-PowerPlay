package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class Main {

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(52.09776244331133, 52.09776244331133, 4.302544843778562, 4.302544843778562, 13.5)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, -61.5, Math.toRadians(90)))
                                .splineToConstantHeading(new Vector2d(-35,-25),Math.toRadians(90))
                                .splineToSplineHeading(new Pose2d(-28.5,-5,Math.toRadians(45)),Math.toRadians(45))
                                .addSpatialMarker(new Vector2d(-35,-48), () -> {})
                                .setReversed(true)
                                .splineToLinearHeading(new Pose2d(-30.5,-7,Math.toRadians(45)),Math.toRadians(45))
                                .setReversed(false)
                                .splineToSplineHeading(new Pose2d(-34.5,-14.7,Math.toRadians(135)),Math.toRadians(180))
                                .splineToSplineHeading(new Pose2d(-55,-11.7,Math.toRadians(180)),Math.toRadians(180))
                                .forward(5)
                                .splineToLinearHeading(new Pose2d(-40,-11.7,Math.toRadians(180)),Math.toRadians(0))
                                .splineToSplineHeading(new Pose2d(-29, -7, Math.toRadians(45)), Math.toRadians(45))
                                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL).addEntity(myBot).start();
    }
}