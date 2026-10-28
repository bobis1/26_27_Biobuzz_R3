package org.firstinspires.ftc.teamcode.Teleop;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Config
@TeleOp(name = "RedTeleop")
public class RedTeleop extends OpMode {

    private Follower follower;
    private DcMotorEx pollenTurretMotor;
    private DcMotorEx nectarTurretMotor;
    private DcMotorEx intakeMotor;
    private Servo pollenGateServo;
    private Servo nectarGateServo;
    //N for normal and T for torque
    private Servo barServoN;
    private Servo barServoT;
    private double neutralBarPos = 0;
    private double pollenBarPos = 0.25;
    private double nectarBarPos = 0.5;
    private double openPollenGatePos = 1;
    private double closedPollenGatePos = 0;
    private double openNectarGatePos = 1;
    private double closedNectarGatePos = 0;
    private boolean isRunningTurret =false;
    private boolean isRunningIntake = false;



    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        nectarTurretMotor = hardwareMap.get(DcMotorEx.class, "nectarTurretMotor");
        pollenTurretMotor = hardwareMap.get(DcMotorEx.class, "pollenTurretMotor");
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
    }

    @Override
    public void loop() {
        follower.manual(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x
        );
        if(gamepad1.xWasPressed()){
            //Pollen Allowed
            barServoN.setPosition(pollenBarPos);
            barServoT.setPosition(pollenBarPos);
            pollenGateServo.setPosition(openPollenGatePos);
            nectarGateServo.setPosition(closedNectarGatePos);
        }
        if(gamepad1.aWasPressed()){
            // Netrual/No game elements Allowed.
            barServoN.setPosition(neutralBarPos);
            barServoT.setPosition(neutralBarPos);
            pollenGateServo.setPosition(closedPollenGatePos);
            nectarGateServo.setPosition(closedNectarGatePos);
        }
        if(gamepad1.yWasPressed()){
            //Nectar Pose
            barServoN.setPosition(nectarBarPos);
            barServoT.setPosition(nectarBarPos);
            pollenGateServo.setPosition(closedPollenGatePos);
            nectarGateServo.setPosition(openNectarGatePos);
        }

        if(gamepad1.bWasPressed()){
            isRunningTurret = !isRunningTurret;
            if (isRunningTurret) {
                nectarTurretMotor.setVelocity(1250);
                pollenTurretMotor.setVelocity(1250);
            } else{
                nectarTurretMotor.setVelocity(0);
                pollenTurretMotor.setVelocity(0);
            }
        }

        if(gamepad1.rightTriggerWasPressed()){
            isRunningIntake = !isRunningIntake;
            if(isRunningIntake){
                intakeMotor.setVelocity(1500);
            } else{
                intakeMotor.setVelocity(0);
            }
        }

        follower.update();
    }
}