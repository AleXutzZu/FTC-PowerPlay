package org.firstinspires.ftc.teamcode.control;

import lombok.Getter;

public interface ElevatorControl {

    enum ElevatorLevel {
        /**
         * The lowest level of the elevator (at the base of the robot)
         */
        BASE(0),
        /**
         * The low level of the elevator (slightly above the shortest bar)
         */
        LOW(35.2),
        /**
         * The middle level of the elevator (slightly above the medium bar)
         */
        MIDDLE(60.8),
        /**
         * The highest level of the elevator (slightly above the tallest bar)
         */
        HIGH(85.9);

        @Getter
        private double height = 0;


        ElevatorLevel(double height) {
            this.height = height;
        }
    }

    void home();

    void update();

    void setPosition(int position);

    void setPosition(ElevatorLevel level);
}
