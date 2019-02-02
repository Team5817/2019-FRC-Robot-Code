/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
public class Controller {
    private static Controller instance_ = new Controller();
	
	public static Controller getInstance() {
		return instance_;
	}
	public Joystick driverController;
	public Joystick codriverController;
	
	boolean lastValue;
	boolean lastValueTwo;
	
	private Controller(){
		driverController = new Joystick(0);
		//created a new object of type Joystick which is indexed at zero.
		
		codriverController = new Joystick(1);
		//created a new object of type Joystick indexed at one.
	}
	
	
	public double getXLeftDriver(){
		return driverController.getRawAxis(0);
		//returns the value of the X axis in the left joystick(-1 to 1)
	}
	public double getYLeftDriver(){
		return driverController.getRawAxis(4) * (-1);
		//returns the value of the Y axis on the left joystick(-1 to 1)
	}
	public double getRightTriggerDriver(){
		return driverController.getRawAxis(3);
		//returns the depth to which the trigger is pressed
		//right trigger returns 0 to 1
	}
	public double getLeftTriggerDriver(){
		return driverController.getRawAxis(2);
		//returns the depth to which the trigger is pressed
		//left trigger returns 0 to 1
	}
	public double getXRightDriver(){
		return driverController.getRawAxis(1);
		//returns the value of the X axis in the right joystick(-1 to 1)
	}
	public double getYRightDriver(){
		return driverController.getRawAxis(5);
		//returns the value of the Y axis on the right joystick(-1 to 1)
	}
	public int getDpadDriver(){
		return driverController.getPOV();
		//returns the value of the Dpad in degrees starting at 0(straight up)
		//up = 0
		//up and left = 45
		//left = 90
		//left and down = 135
		//down = 180
		//down and right = 225
		//right = 270
		//right and up = 315
	}
	public boolean getButtonADriver(){
		return driverController.getRawButton(1);
		//returns true when the B button is pressed otherwise false
	}
	public boolean getButtonBDriver(){
		return driverController.getRawButton(2);
		//returns true when the A button is pressed otherwise false
	}
	public boolean getButtonXDriver(){
		return driverController.getRawButton(3);
		//returns true when the X button is pressed otherwise false
	}
	public boolean getButtonYDriver(){
		return driverController.getRawButton(4);
		//returns true when the Y button is pressed otherwise false
	}
	public boolean getLeftBumperDriver(){
		return driverController.getRawButton(5);
		//returns true when the Left Bumper is pressed otherwise false
	}
	public boolean getRightBumperDriver(){
		return driverController.getRawButton(6);
		//returns true when the Right Bumper is pressed otherwise false
	}
	public boolean getBackButtonDriver(){
		return driverController.getRawButton(7);
		//returns true when the Back button is pressed otherwise false
	}
	public boolean getStartButtonDriver(){
		return driverController.getRawButton(8);
		//returns true when the start button is pressed otherwise false
	}
	public boolean getLeftJoystickPressDriver(){
		return driverController.getRawButton(9);
		//returns true when the left Joystick is pressed down otherwise false
	}
	public boolean getRightJoystickPressDriver()
	{
		return driverController.getRawButton(10);
		//returns true when the right Joystick is pressed down otherwise false
	}
	public double getXLeft2CoDriver(){
		return codriverController.getRawAxis(0);
		//returns the value of the X axis in the left joystick(-1 to 1)
	}
	public double getYLeftCoDriver(){
		return codriverController.getRawAxis(1);
		//returns the value of the Y axis on the left joystick(-1 to 1)
	}
	public double getRightTriggerCoDriver(){
		return codriverController.getRawAxis(3);
		//returns the depth to which the trigger is pressed
		//right trigger returns 0 to 1
	}
	public double getLeftTriggerCoDriver(){
		return codriverController.getRawAxis(2);
		//returns the depth to which the trigger is pressed
		//left trigger returns 0 to 1
	}
	public double getXRightCoDriver(){
		return codriverController.getRawAxis(4);
		//returns the value of the X axis in the right joystick(-1 to 1)
	}
	public double getYRightCoDriver(){
		return codriverController.getRawAxis(5);
		//returns the value of the Y axis on the right joystick(-1 to 1)
	}
	public int getDpadCoDriver(){
		return codriverController.getPOV();
		//returns the value of the Dpad in degrees starting at 0(straight up)
		//up = 0
		//up and left = 45
		//left = 90
		//left and down = 135
		//down = 180
		//down and right = 225
		//right = 270
		//right and up = 315
	}
	public boolean getButtonACoDriver(){
		return codriverController.getRawButton(1);
		//returns true when the B button is pressed otherwise false
	}
	public boolean getButtonBCoDriver(){
		return codriverController.getRawButton(2);
		//returns true when the A button is pressed otherwise false
	}
	public boolean getButtonXCoDriver(){
		return codriverController.getRawButton(3);
		//returns true when the X button is pressed otherwise false
	}
	public boolean getButtonYCoDriver(){
		return codriverController.getRawButton(4);
		//returns true when the Y button is pressed otherwise false
	}
	public boolean getLeftBumperCoDriver(){
		return codriverController.getRawButton(5);
		//returns true when the Left Bumper is pressed otherwise false
	}
	public boolean getRightBumperCoDriver(){
		return codriverController.getRawButton(6);
		//returns true when the Right Bumper is pressed otherwise false
	}
	public boolean getBackButtonCoDriver(){
		return codriverController.getRawButton(7);
		//returns true when the Back button is pressed otherwise false
	}
	public boolean getStartButtonCoDriver(){
		return codriverController.getRawButton(8);
		//returns true when the start button is pressed otherwise false
	}
	public boolean getLeftJoystickPressCoDriver(){
		return codriverController.getRawButton(9);
		//returns true when the left Joystick is pressed down otherwise false
	}
	public boolean getRightJoystickPressCoDriver() {
		return codriverController.getRawButton(10);
		//returns true when the right Joystick is pressed down otherwise false
    }
}