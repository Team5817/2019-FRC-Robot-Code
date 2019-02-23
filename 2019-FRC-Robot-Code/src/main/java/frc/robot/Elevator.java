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
	int velocity=0;
	
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
    TalonSRX wrist;


    /* Elevator method assigns a object of the Talon Class for each physical Talon SRX used*/
    public Elevator(){

        wrist = new TalonSRX(10);
        elevatorMotorOne = new TalonSRX(9);
        elevatorMotorTwo = new TalonSRX(8);
		elevatorMotorThree = new TalonSRX(6);
		//hatch panel finger is talon 7. 
		//for comp switch 7 to elevatorOne 
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
		elevatorMotorOne.config_kF(0, 0.2481, kTimeoutMs);
		elevatorMotorOne.config_kP(0, 1.0, kTimeoutMs);
		elevatorMotorOne.config_kI(0, 0.0001, kTimeoutMs);
		elevatorMotorOne.config_kD(0, 1, kTimeoutMs);
		/* set acceleration and cruise velocity - see documentation */
		elevatorMotorOne.configMotionCruiseVelocity(5000, kTimeoutMs);
		elevatorMotorOne.configMotionAcceleration(3500, kTimeoutMs);
		/* zero the sensor */
        elevatorMotorOne.setSelectedSensorPosition(0, 0, kTimeoutMs);
        
        wrist.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
		wrist.setSensorPhase(true);
		wrist.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		wrist.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
		wrist.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);

		/* set the peak and nominal outputs */
		wrist.configNominalOutputForward(0, kTimeoutMs);
		wrist.configNominalOutputReverse(0, kTimeoutMs);
		wrist.configPeakOutputForward(1, kTimeoutMs);
		wrist.configPeakOutputReverse(-1, kTimeoutMs);

		/* set closed loop gains in slot0 - see documentation */
		wrist.selectProfileSlot(0, 0);
		wrist.config_kF(0, 0.15, kTimeoutMs);
		wrist.config_kP(0, 0.8, kTimeoutMs);
		wrist.config_kI(0, 0.001, kTimeoutMs);
		wrist.config_kD(0, 2.0, kTimeoutMs);
		/* set acceleration and cruise velocity - see documentation */
		wrist.configMotionCruiseVelocity(3000, kTimeoutMs);
		wrist.configMotionAcceleration(1000, kTimeoutMs);
		/* zero the sensor */
		wrist.setSelectedSensorPosition(0, 0, kTimeoutMs);
	}

    /* Methods which control the outputs to the motors */
    /* The follower method causes the talons to follow the output of the master talon assigned in parenthases */
    public void manualElevatorControl(double input){
        elevatorMotorThree.follow(elevatorMotorOne);
        elevatorMotorTwo.follow(elevatorMotorOne);
        elevatorMotorOne.set(ControlMode.PercentOutput, input);
    }
    public void manualWristControl(double input){
        wrist.set(ControlMode.PercentOutput, input);
    }
    public int getElevatorPosition(){
        return elevatorMotorOne.getSelectedSensorPosition(0);
    }
    public int getWristPosition(){
        return wrist.getSelectedSensorPosition(0);
    }
        public void zero(){
            elevatorMotorOne.setSelectedSensorPosition(0, 0, 10);
            wrist.setSelectedSensorPosition(0, 0, 10);
    }
    public void setElevatorPosition(int value){
        elevatorMotorThree.follow(elevatorMotorOne);
        elevatorMotorTwo.follow(elevatorMotorOne);
        elevatorMotorOne.set(ControlMode.MotionMagic, value);
        }
    public void setWristPosition(int value){
		System.out.println(value);
        wrist.set(ControlMode.MotionMagic, value);
		}
	public int maxVelocity(){
		if (elevatorMotorOne.getSelectedSensorVelocity()> velocity){
			velocity= elevatorMotorOne.getSelectedSensorVelocity();
		}else{
			velocity=velocity;
		}
		return velocity;

	}
        
}



