package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.MarkerCallback;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import sun.text.resources.ext.FormatData_en_IN;

public class NewAutonomous {

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(52.09776244331133, 52.09776244331133, 4.302544843778562, 4.302544843778562, 11.5)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, -60.5, Math.toRadians(90)))
                                .splineToConstantHeading(new Vector2d(-35,-25),Math.toRadians(90))
                                .splineToSplineHeading(new Pose2d(-28.5,-5,Math.toRadians(45)),Math.toRadians(45))
                                .addSpatialMarker(new Vector2d(-35,-48), () -> {})
                                .setReversed(true)
                                .splineToLinearHeading(new Pose2d(-55,-11.7,Math.toRadians(180)),Math.toRadians(180))
                                .addSpatialMarker(new Vector2d(-39,-10.6), () -> {})
                                .forward(5)
                                .splineToLinearHeading(new Pose2d(-28.5, -5, Math.toRadians(45)), Math.toRadians(90))
                                .addSpatialMarker(new Vector2d(-47, -13), () -> {})
                                .splineToLinearHeading(new Pose2d(-35,-12,Math.toRadians(0)),Math.toRadians(0))
                                .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL).addEntity(myBot).start();
    }
}