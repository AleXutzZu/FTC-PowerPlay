package com.example.meepmeep;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class ThirdTrajectory {

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(52.09776244331133, 52.09776244331133, 4.302544843778562, 4.302544843778562, 13.5)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, -60.5, Math.toRadians(90)))
                                .splineToConstantHeading( new Vector2d(-35,-25),Math.toRadians(90))
                                .splineToSplineHeading(new Pose2d(-29,-6,Math.toRadians(45)),Math.toRadians(45))
                                .setReversed(true)
                                .splineToSplineHeading(new Pose2d(-55,-11,Math.toRadians(180)),Math.toRadians(135))
                                .setReversed(false)
                                .splineToLinearHeading(new Pose2d(-35,-16,Math.toRadians(90)),Math.toRadians(0))
                                .splineToLinearHeading(new Pose2d(-29,-6,Math.toRadians(45)),Math.toRadians(180))
                                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL).addEntity(myBot).start();
    }
}
