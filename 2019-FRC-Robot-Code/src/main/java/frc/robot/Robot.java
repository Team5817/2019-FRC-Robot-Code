/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 * hello world
 */
public class Robot extends IterativeRobot {
  /**
   *
   */

  private static final int _0 = 0;
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private Position position = Position.ZERO;

  private Drive drive = Drive.getInstance();
  private Controller controller = Controller.getInstance();
  private Intake intake = Intake.getInstance();
  private Elevator elevator = Elevator.getInstance();
  private Gyro gyro = Gyro.getInstance();
  private double controllerJoystickDeadzone = 0.2;
  private Vision vision = Vision.getInstance();


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    CameraServer.getInstance().startAutomaticCapture();
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    //gyro displays to smartdashboard
    SmartDashboard.putNumber("Yaw", gyro.getAngleYaw());
    SmartDashboard.putNumber("Pitch", gyro.getAnglePitch());
    SmartDashboard.putNumber("Roll", gyro.getAngleRoll());
    SmartDashboard.putNumber("LimeLightX", vision.getdegRotationtoTarget());
    SmartDashboard.putNumber("LimeLightY", vision.getdegVerticaltoTarget());
    
    

    /* Controls the six wheel base using the Y axis on the right joystick to control power
     * and the X axis on the left joystick to adjust the output in order to allow the robot
     * to turn */
if (controller.getRightBumperDriver()){
  if (controller.getYLeftDriver() > controllerJoystickDeadzone){
    drive.rightSideControl(controller.getYLeftDriver() - (controller.getXRightDriver()*0.001));
    drive.leftSideControl(controller.getYLeftDriver() + (controller.getXRightDriver()*0.001));
  
  }else if(controller.getYLeftDriver() < controllerJoystickDeadzone * (-1) ){
    drive.rightSideControl(controller.getYLeftDriver() - (controller.getXRightDriver()*0.001));
    drive.leftSideControl(controller.getYLeftDriver() + (controller.getXRightDriver()*0.001));
  
  }else if(controller.getXRightDriver() < controllerJoystickDeadzone *(-1) || controller .getXRightDriver() < controllerJoystickDeadzone){
      drive.rightSideControl(controller.getXRightDriver()*(-1));
      drive.leftSideControl(controller.getXRightDriver());

    }else {
      drive.rightSideControl(0);
      drive.leftSideControl(0);
    }
}else{
    
     if (controller.getYLeftDriver() > controllerJoystickDeadzone){
      drive.rightSideControl(controller.getYLeftDriver() - (controller.getXRightDriver()*0.3));
      drive.leftSideControl(controller.getYLeftDriver() + (controller.getXRightDriver()*0.3));
    
    }else if(controller.getYLeftDriver() < controllerJoystickDeadzone * (-1) ){
      drive.rightSideControl(controller.getYLeftDriver() - (controller.getXRightDriver()*0.3));
      drive.leftSideControl(controller.getYLeftDriver() + (controller.getXRightDriver()*0.3));
    
    }else if(controller.getXRightDriver() > controllerJoystickDeadzone *(-1) || controller .getXRightDriver() < controllerJoystickDeadzone){
        drive.rightSideControl(controller.getXRightDriver()*(-1));
        drive.leftSideControl(controller.getXRightDriver());

      }else {
        drive.rightSideControl(0);
        drive.leftSideControl(0);
    }
  }

  if (controller.getButtonYDriver()){
    if (vision.getdegRotationtoTarget()>0.0){
      drive.leftSideControl(vision.getdegRotationtoTarget()*-.125);
      drive.rightSideControl(vision.getdegRotationtoTarget()*.125);
    }else if(vision.getdegRotationtoTarget()<0.0){
      drive.rightSideControl(vision.getdegRotationtoTarget()*-.125);
      drive.leftSideControl(vision.getdegRotationtoTarget()*.125);
    }else{
      drive.rightSideControl(0.0);
      drive.leftSideControl(0.0);
    }
  }else{
    //do nothing
  }
}
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

//displays to smart dashboard
    SmartDashboard.putNumber("Left Drive Velocity", drive.getLeftDriveVelocity());
    SmartDashboard.putNumber("Right Drive Velocity", drive.getRightDriveVelocity());

    SmartDashboard.putNumber("Yaw", gyro.getAngleYaw());
    SmartDashboard.putNumber("Pitch", gyro.getAnglePitch());
    SmartDashboard.putNumber("Roll", gyro.getAngleRoll());

    SmartDashboard.putNumber("Elevator Position", elevator.getElevatorPosition());

    if(controller.getLeftJoystickPressDriver() && controller.getRightJoystickPressDriver()){
      SmartDashboard.putNumber("Lucky Number 5", 5);
    }

    
    //drive code using velocity
    if (controller.getYLeftDriver() > controllerJoystickDeadzone){
      drive.rightSideControl(drive.getLeftDriveVelocity() - (controller.getXRightDriver()*0.5));
      drive.leftSideControl(controller.getYLeftDriver() + (controller.getXRightDriver()*0.5));
    
    }else if(controller.getYLeftDriver() < controllerJoystickDeadzone * (-1) ){
      drive.rightSideControl(drive.getLeftDriveVelocity() - (controller.getXRightDriver()*0.5));
      drive.leftSideControl(controller.getYLeftDriver() + (controller.getXRightDriver()*0.5));
    
    }else if(controller.getXRightDriver() > controllerJoystickDeadzone *(-1) || controller .getXRightDriver() < controllerJoystickDeadzone){
        drive.rightSideControl(controller.getXRightDriver()*(-1));
        drive.leftSideControl(controller.getXRightDriver());

      }else {
        drive.rightSideControl(0);
        drive.leftSideControl(0);
    }


//elevator code

if(controller.getButtonADriver()){
  position = Position.PANELLOW;
}else if(controller.getButtonXCoDriver()){
  position = Position.PANELMID;
}else if(controller.getButtonBDriver()){
  position = Position.PANELHIGH;
}else if(controller.getDpadDriver()==180){
  position = Position.CARGOLOW;
}else if(controller.getDpadDriver()==90){
  position = Position.CARGOMID;
}else if(controller.getDpadDriver()==270){
  position = Position.PANELHIGH;
}else if(controller.getDpadDriver()==0){
  position = Position.CARGOSHIP;
}
 switch(position){
  case PANELLOW:
  elevator.setElevatorPosition(100);
  break;

  case PANELMID:
  elevator.setElevatorPosition(200);
  break;

  case PANELHIGH:
  elevator.setElevatorPosition(300);
  break;

  case CARGOLOW:
  elevator.setElevatorPosition(150);
  break;
  
  case CARGOMID:
  elevator.setElevatorPosition(250);
  break;

  case CARGOHIGH:
  elevator.setElevatorPosition(350);
  break;

  case CARGOSHIP:
  elevator.setElevatorPosition(250);
  break;

  default:
  elevator.setElevatorPosition(0);
  break;
}

if (controller.getLeftTriggerDriver() > controllerJoystickDeadzone){
  elevator.manualElevatorControl(controller.getLeftTriggerDriver() * (-.125));
}else if(controller.getRightTriggerDriver() > controllerJoystickDeadzone){
  elevator.manualElevatorControl(controller.getRightTriggerDriver());
}else{
  elevator.manualElevatorControl(0);
}

if(controller.getStartButtonDriver()){
  
  elevator.zero();
}
//intake
if (controller.getLeftTriggerCoDriver() > controllerJoystickDeadzone){
  intake.leftIntakeControl(controller.getLeftTriggerCoDriver() * (-1));
  intake.rightIntakeControl(controller.getLeftTriggerCoDriver());
}else if(controller.getRightTriggerCoDriver() > controllerJoystickDeadzone){
  intake.rightIntakeControl(controller.getRightTriggerCoDriver());
  intake.rightIntakeControl(controller.getRightTriggerCoDriver() * (-1));
}else{
  intake.leftIntakeControl(0);
  intake.rightIntakeControl(0);
}

}
}
