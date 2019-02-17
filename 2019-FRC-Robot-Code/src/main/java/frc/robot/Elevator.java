/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.           
                                                    */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Elevator {

    /* Creates instance to prevent memory overflow during robot operation*/
    private static Elevator instance_;
	
	public static Elevator getInstance() {
		if(instance_ == null) {
			instance_ = new Elevator();
		}
		return instance_;
	}

    /* Creates variables of the TalonSRX class for each talon used in the elevator*/
    TalonSRX elevatorMotorOne;
    TalonSRX elevatorMotorTwo;
    TalonSRX elevatorMotorThree;


    /* Elevator method assigns a object of the Talon Class for each physical Talon SRX used*/
    public Elevator(){

        elevatorMotorOne = new TalonSRX(9);
        elevatorMotorTwo = new TalonSRX(8);
        elevatorMotorThree = new TalonSRX(7);
        motionMagic();
        
    }

    public void motionMagic(){
        int kTimeoutMs=10;
        elevatorMotorOne.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
		elevatorMotorOne.setSensorPhase(true);
		elevatorMotorOne.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		elevatorMotorOne.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
		elevatorMotorOne.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);

		/* set the peak and nominal outputs */
		elevatorMotorOne.configNominalOutputForward(0, kTimeoutMs);
		elevatorMotorOne.configNominalOutputReverse(0, kTimeoutMs);
		elevatorMotorOne.configPeakOutputForward(1, kTimeoutMs);
		elevatorMotorOne.configPeakOutputReverse(-1, kTimeoutMs);

		/* set closed loop gains in slot0 - see documentation */
		elevatorMotorOne.selectProfileSlot(0, 0);
		elevatorMotorOne.config_kF(0, 0.5, kTimeoutMs);
		elevatorMotorOne.config_kP(0, 1.25, kTimeoutMs);
		elevatorMotorOne.config_kI(0, 0.0, kTimeoutMs);
		elevatorMotorOne.config_kD(0, 15, kTimeoutMs);
		/* set acceleration and cruise velocity - see documentation */
		elevatorMotorOne.configMotionCruiseVelocity(4000, kTimeoutMs);
		elevatorMotorOne.configMotionAcceleration(9000, kTimeoutMs);
		/* zero the sensor */
		elevatorMotorOne.setSelectedSensorPosition(0, 0, kTimeoutMs);
    }

    /* Methods which control the outputs to the motors */
    /* The follower method causes the talons to follow the output of the master talon assigned in parenthases */
    public void manualElevatorControl(double input){
        elevatorMotorThree.follow(elevatorMotorOne);
        elevatorMotorTwo.follow(elevatorMotorOne);
        elevatorMotorOne.set(ControlMode.PercentOutput, input);
    }
    public int getElevatorPosition(){
        return elevatorMotorOne.getSelectedSensorPosition(0);
    }
        public void zero(){
            elevatorMotorOne.setSelectedSensorPosition(0, 0, 10);
    }
    public void setElevatorPosition(int value){
        elevatorMotorThree.follow(elevatorMotorOne);
        elevatorMotorTwo.follow(elevatorMotorOne);
        elevatorMotorOne.set(ControlMode.MotionMagic, value);
        }
    
        
}



