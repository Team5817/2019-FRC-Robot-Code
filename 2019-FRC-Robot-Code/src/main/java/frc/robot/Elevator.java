/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.           
                                                    */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
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

        elevatorMotorOne = new TalonSRX(8);
        elevatorMotorTwo = new TalonSRX(9);
        elevatorMotorThree = new TalonSRX(10);
        
    }

    /* Methods which control the outputs to the motors */
    /* The follower method causes the talons to follow the output of the master talon assigned in parenthases */
    public void elevatorControl(double input){
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
        elevatorMotorOne.set(ControlMode.PercentOutput, value);

        
        }
    
        
}



