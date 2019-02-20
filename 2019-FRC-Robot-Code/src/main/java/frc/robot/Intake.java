/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

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

    public Intake(){
        rightIntake = new TalonSRX(13);
        leftIntake = new TalonSRX(14);
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
}