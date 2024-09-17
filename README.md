# FTC Power Play Code

This is an archived repository for the Robot Controller App used in Power Play FTC Season by the Perpetuum Mobile Team.

## Project Structure
- Module [MeepMeep](MeepMeep/src/main/java/me/alexutzzu/meepmeep): Contains some classes used to vizualize the paths generated with the use of RoadRunner
- Module [FtcRobotController](FtcRobotController): Internal classes used in the compilation of the app, not to be modified by us
- Module [TeamCode](TeamCode/src/main/java/org/firstinspires/ftc/teamcode): Actual workspace for our robot's program

### More details on the TeamCode module
- Package [hardware](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/hardware): Contains a singleton class which loads and initializes all of the hardware used on our robot (servos, motors and other sensors)
- Package [roadrunner](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/roadrunner): Classes related to the RoadRunner library and its calibration OpModes
- Package [control](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/control): Abstract classes defining key aspects for TeleOp and Autonomous OpModes such as basic controls and other initialization steps
- Package [opmodes](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/opmodes): All Autonomous and TeleOp OpModes created to be used in competition
- Package [util](TeamCode/src/main/java/org/firstinspires/ftc/teamcode/util): Constant definitions and other utilities for OpenCV

## Libraries used
- RoadRunner for path and trajectory generation
- FTCLib
- MeepMeep for vizualizing RoadRunner paths/trajectories
- OpenCV

## Links to docs

- [Robot Controller Docs](https://github.com/FIRST-Tech-Challenge/FtcRobotController/wiki)
- [Javadoc](https://javadoc.io/doc/org.firstinspires.ftc)
- [Samples](FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples)
