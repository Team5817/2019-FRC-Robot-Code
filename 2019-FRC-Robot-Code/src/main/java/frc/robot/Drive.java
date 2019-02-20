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

public class Drive {

    /* Creates instance to prevent memory overflow during robot operation*/
    private static Drive instance_;
	
	public static Drive getInstance() {
		if(instance_ == null) {
			instance_ = new Drive();
		}
		return instance_;
	}

    /* Creates variables of the TalonSRX class for each talon used in the drivetrain*/
    TalonSRX rightDriveOne;
    TalonSRX rightDriveTwo;
    TalonSRX rightDriveThree;
    TalonSRX leftDriveOne;
    TalonSRX leftDriveTwo;
    TalonSRX leftDriveThree;
    TalonSRX clawRight;
    TalonSRX clawLeft;

    /* Drive method assigns a object of the Talon Class for each physical Talon SRX used*/
    public Drive(){

        rightDriveOne = new TalonSRX(0);
        rightDriveTwo = new TalonSRX(1);
        rightDriveThree = new TalonSRX(2);
        leftDriveOne = new TalonSRX(3);
        leftDriveTwo = new TalonSRX(4);
        leftDriveThree = new TalonSRX(5);
        clawRight = new TalonSRX(11);
        clawLeft = new TalonSRX(12);

    }

    /* Methods which control the outputs to the motors */
    /* The follower method causes the talons to follow the output of the master talon assigned in parenthases */
    public void rightSideControl(double input){
        rightDriveThree.follow(rightDriveOne);
        rightDriveTwo.follow(rightDriveOne);
        rightDriveOne.set(ControlMode.PercentOutput, input);
    }

    public void leftSideControl(double input){
        leftDriveThree.follow(leftDriveOne);
        leftDriveTwo.follow(leftDriveOne);
        leftDriveOne.set(ControlMode.PercentOutput, input);
    }

    // Controls the climbing claws with a single input
    public void clawControl(double input){
        clawLeft.follow(clawRight);
        clawRight.set(ControlMode.PercentOutput, input);
    }

    // Returns the velocity of the right and left drive
    public int getRightDriveVelocity(){
        return rightDriveOne.getSelectedSensorVelocity(0);
    }

    public int getLeftDriveVelocity(){
        return leftDriveTwo.getSelectedSensorVelocity(0);
    }
}



