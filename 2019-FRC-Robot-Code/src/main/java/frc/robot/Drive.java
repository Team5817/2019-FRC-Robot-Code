/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Drive {

    TalonSRX rightDriveOne;
    TalonSRX rightDriveTwo;
    TalonSRX rightDriveThree;
    TalonSRX leftDriveOne;
    TalonSRX leftDriveTwo;

    public void Drive(){
        rightDriveOne = new TalonSRX(0);
        rightDriveTwo = new TalonSRX(1);
    }

}
