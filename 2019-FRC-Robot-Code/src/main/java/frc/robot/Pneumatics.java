/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SolenoidBase;
import edu.wpi.first.wpilibj.Compressor;
/**
 * Add your docs here.
 */
public class Pneumatics {

    /* Creates instance to prevent memory overflow during robot operation*/
    private static Pneumatics instance_;
	
	public static Pneumatics getInstance() {
		if(instance_ == null) {
			instance_ = new Pneumatics();
		}
		return instance_;
    }
    Compressor compressor = new Compressor(0);

    Solenoid solenoidOne = new Solenoid(0);
    Solenoid solenoidTwo = new Solenoid(1);
    Solenoid solenoidThree = new Solenoid(2);
    Solenoid solenoidFour = new Solenoid(3);

    boolean enabled = compressor.enabled();
    boolean pressureSwitch = compressor.getPressureSwitchValue();
    double current = compressor.getCompressorCurrent();
}
