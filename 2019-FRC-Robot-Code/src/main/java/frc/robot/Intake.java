/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;


public class Intake {

    // Method which prevents memory overflow
    private static Intake instance_;
	
	public static Intake getInstance() {
		if(instance_ == null) {
			instance_ = new Intake();
		}
		return instance_;
    }
    
    public TalonSRX rightIntake;
    public TalonSRX leftIntake;
    TalonSRX panelIntake = new TalonSRX(7);

    public Intake(){
        rightIntake = new TalonSRX(13);
        leftIntake = new TalonSRX(14);

    }

    public void motionMagic(){
        int kTimeoutMs=10;
        panelIntake.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, kTimeoutMs);
		panelIntake.setSensorPhase(true);
		panelIntake.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		panelIntake.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
		panelIntake.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);

		/* set the peak and nominal outputs */
		panelIntake.configNominalOutputForward(0, kTimeoutMs);
		panelIntake.configNominalOutputReverse(0, kTimeoutMs);
		panelIntake.configPeakOutputForward(1, kTimeoutMs);
		panelIntake.configPeakOutputReverse(-1, kTimeoutMs);

		/* set closed loop gains in slot0 - see documentation */
		panelIntake.selectProfileSlot(0, 0);
		panelIntake.config_kF(0, 0.2481, kTimeoutMs);
		panelIntake.config_kP(0, 1.0, kTimeoutMs);
		panelIntake.config_kI(0, 0.0001, kTimeoutMs);
		panelIntake.config_kD(0, 1, kTimeoutMs);
		/* set acceleration and cruise velocity - see documentation */
		panelIntake.configMotionCruiseVelocity(5000, kTimeoutMs);
		panelIntake.configMotionAcceleration(3500, kTimeoutMs);
		/* zero the sensor */
        panelIntake.setSelectedSensorPosition(0, 0, kTimeoutMs);
    }
    // Methods which control the direction and power with which the intake wheels spin
    public void stop(){
        rightIntake.set(ControlMode.PercentOutput, 0.0);
        leftIntake.set(ControlMode.PercentOutput, 0.0);
    }

    public void leftIntakeControl(double input){
        leftIntake.set(ControlMode.PercentOutput, input);
    }

    public void rightIntakeControl(double input){
        rightIntake.set(ControlMode.PercentOutput, input);
    }

    public void setPanelIntakePosition(int value){
    panelIntake.set(ControlMode.MotionMagic, value);
        }
        public int getPanelIntakePosition(){
            return panelIntake.getSelectedSensorPosition(0);
        }
    public void panelIntakeControl(double input){
        panelIntake.set(ControlMode.PercentOutput, input);
    }
}