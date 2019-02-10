/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.kauailabs.navx.frc.AHRS;
import com.kauailabs.navx.frc.AHRS.SerialDataType;
import edu.wpi.first.wpilibj.SerialPort;


/**
 * Add your docs here.
 */
public class Gyro {
    private final AHRS navx;
	
	private static Gyro instance_;
	
	private Gyro() {
		byte update_rate = 50;
		navx = new AHRS(SerialPort.Port.kUSB, SerialDataType.kProcessedData, update_rate);
	}
    public static Gyro getInstance() {
		if(instance_ == null) {
			instance_ = new Gyro();
		}
		return instance_;
	}
    public double getAngleYaw() {
		return navx.getYaw();
	}
    public double getAnglePitch() {
		return navx.getPitch();
    }
    public double getAngleRoll() {
		return navx.getRoll();
	}
}
