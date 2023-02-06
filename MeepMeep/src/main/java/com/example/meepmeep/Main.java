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
                                /*.lineTo(new Vector2d(-20, -60))
                                .splineTo(new Vector2d(-12, -55), Math.toRadians(0))
                                .splineTo(new Vector2d(-6, -12), 0)
                                .addDisplacementMarker(() -> {

                                })*/
                                .lineTo(new Vector2d(-13, -59))
                                .splineTo(new Vector2d(-1, -47), Math.toRadians(45))
                                .splineToConstantHeading(new Vector2d(-7, -38), Math.toRadians(90))
                                .splineToSplineHeading(new Pose2d(-11, -30, Math.toRadians(90)), Math.toRadians(90))
                                .splineToSplineHeading(new Pose2d(-12, -12, Math.toRadians(125)), Math.toRadians(90))
//                                .forward(8)
                                /*.forward(7)
                                .addDisplacementMarker(() -> {

                                })
                                .lineToLinearHeading(new Pose2d(-12, -12, Math.toRadians(180)))
                                .forward(48)
                                .addDisplacementMarker(() -> {

                                })
                                .lineToLinearHeading(new Pose2d(-12, -12, Math.toRadians(135)))*/
                                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL).addEntity(myBot).start();
    }
}
//.strafeTo(new Vector2d(-12, -60.5))
//.strafeTo(new Vector2d(-12, -12))
//.splineToSplineHeading(new Pose2d(-12, -60.5, Math.toRadians(90)), 0)
//.splineToSplineHeading(new Pose2d(-12, -12, Math.toRadians(135)), Math.toRadians(90))