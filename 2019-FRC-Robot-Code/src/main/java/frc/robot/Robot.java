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
  private Position position = Position.MANUALOVERRIDE;
  private PositionTwo positionTwo = PositionTwo.FINGEROFF;
  private Drive drive = Drive.getInstance();
  private Controller controller = Controller.getInstance();
  private Intake intake = Intake.getInstance();
  private Elevator elevator = Elevator.getInstance();
  private Gyro gyro = Gyro.getInstance();
  private double controllerJoystickDeadzone = 0.2;
  private double controllerTriggerDeadzone = 0.05;
  private Vision vision = Vision.getInstance();
  private Pneumatics pneumatics = Pneumatics.getInstance();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */

  @Override
  public void robotInit() {
    elevator.motionMagic();
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
    SmartDashboard.putNumber("Wrist Position", elevator.getWristPosition());
    SmartDashboard.putNumber("Elevator Velocity", elevator.maxVelocity());
    SmartDashboard.putNumber("Elevator Position", elevator.getElevatorPosition());
    SmartDashboard.putNumber("Intake Position", intake.getPanelIntakePosition());
    SmartDashboard.putNumber("LimeLightX", vision.getdegRotationtoTarget());
    SmartDashboard.putNumber("LimeLightY", vision.getdegVerticaltoTarget());
    SmartDashboard.putNumber("Panel Intake Position", intake.getPanelIntakePosition());
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
    SmartDashboard.putNumber("Wrist Position", elevator.getWristPosition());
    SmartDashboard.putNumber("Elevator Velocity", elevator.maxVelocity());
    SmartDashboard.putNumber("Elevator Position", elevator.getElevatorPosition());

    /*
     * Controls the six wheel base using the Y axis on the right joystick to control power  *
     * and the X axis on the left joystick to adjust the output in order to allow the robot *
     * to turn
     */
    
    if (controller.getYLeftDriver() > controllerJoystickDeadzone){
      drive.rightSideControl(controller.getYLeftDriver() - (controller.getXRightDriver()*0.3));
      drive.leftSideControl(controller.getYLeftDriver() + (controller.getXRightDriver()*0.3));
    }else if(controller.getYLeftDriver() < controllerJoystickDeadzone * (-1) ){
      drive.rightSideControl(controller.getYLeftDriver() - (controller.getXRightDriver()*0.3));
      drive.leftSideControl(controller.getYLeftDriver() + (controller.getXRightDriver()*0.3));
    }else if(controller.getXRightDriver() < controllerJoystickDeadzone *(-1) || controller .getXRightDriver() > controllerJoystickDeadzone){
      drive.rightSideControl(controller.getXRightDriver()*(-1));
      drive.leftSideControl(controller.getXRightDriver());
    }else {
      drive.rightSideControl(0);
      drive.leftSideControl(0);
    }

    /*
    * Controls the elevator manually based on the input from the    *
    * codriver controller. The left joystick controls the elevator  *
    * input and the right joystick controls the wrist manually      *
    */

    if(controller.getYLeftCoDriver() > controllerJoystickDeadzone || controller.getYLeftCoDriver() < controllerJoystickDeadzone*-1){
      position = Position.MANUALOVERRIDE;
    }else{
    }
    if(controller.getYRightCoDriver() > controllerJoystickDeadzone || controller.getYRightCoDriver() < controllerJoystickDeadzone*-1){
      position = Position.MANUALOVERRIDE;
    }else{
    }

    /*
    * Changes the state of the vartiable 'position' which adjusts   *
    * the height of the elevator based on the positions of the      *
    * switch statement below                                        *
    */

if(controller.getLeftBumperDriver()){
drive.clawControl(1);;
}else if(controller.getRightBumperDriver()){
drive.clawControl(-1);
}else{
  drive.clawControl(0);
}

if(controller.getBackButtonDriver() || controller.getBackButtonCoDriver()){
  elevator.zero();
}

    if(controller.getButtonADriver()){
      positionTwo = PositionTwo.FINGEROUT;
    }else if(controller.getButtonBDriver()){
 
      positionTwo = PositionTwo.FINGERIN;
    }else if(controller.getButtonACoDriver()){
      position = Position.INTAKE;
    }else if(controller.getButtonBCoDriver()){
      position = Position.PANELLOW;
    }else if(controller.getButtonXCoDriver()){
      position = Position.PANELMID;
    }else if(controller.getButtonYDriver()){
      position = Position.PANELHIGH;
    }else if(controller.getRightTriggerCoDriver()>0.05){
      position = Position.MANUALOVERRIDE;
    }else if(controller.getLeftTriggerCoDriver() > controllerTriggerDeadzone){
      position = Position.CARGOHIGH;
    }else if(controller.getRightBumperDriver()){
      position = Position.CARGOMID;
    }else if(controller.getLeftBumperCoDriver()){
      position = Position.CARGOLOW;
    }else if(controller.getRightBumperDriver()){
      position = Position.ZERO;
    }else{
      // does not change state if no button is pressed
    }
  

    /*
    * switch statment switches the elevator position based on the state of the variable     *
    * 'position' which is determined by the if/else statement above by setting the variable *
    * and continuously checking it the robot is able to hold its position until the state   *
    * is changed by pressing a different button
    */

  switch(positionTwo){
    
    case FINGERIN:
    intake.panelIntakeControl(0);
    break;

    case FINGEROUT:
    intake.panelIntakeControl(0.6);
    break;

    case FINGEROFF:
    intake.panelIntakeControl(0);;
    break;

    default:
    positionTwo = PositionTwo.FINGERIN;
    break;
  }

  switch(position){
    case PANELLOW:
    elevator.setElevatorPosition(-4466);
    elevator.setWristPosition(0);
    break;

    case WRISTOUT:
    elevator.setWristPosition(500);
    break;

    case INTAKE:
    elevator.setElevatorPosition(0);
    elevator.setWristPosition(1320);
    intake.setPanelIntakePosition(0);
    break;
    
    case PANELMID:
    elevator.setElevatorPosition(-12733);
    elevator.setWristPosition(0);
    break;
    
    case PANELHIGH:
    elevator.setElevatorPosition(300);//over 9000
    elevator.setWristPosition(0);
    break;
    
    case CARGOLOW:
    elevator.setElevatorPosition(-3089);
    elevator.setWristPosition(779);
    break;
      
    case CARGOMID:
    elevator.setElevatorPosition(-10309);
    elevator.setWristPosition(779);
    break;
    
    case CARGOHIGH:
    elevator.setElevatorPosition(-16763);
    elevator.setWristPosition(548);
    break;
    
    case CARGOSHIP:
    elevator.setElevatorPosition(250);
    elevator.setWristPosition(250);
    break;

    case  ZERO:
    elevator.setElevatorPosition(500);
    elevator.setWristPosition(50);
    break;
    

    case MANUALOVERRIDE:
    if (controller.getYLeftCoDriver() > controllerJoystickDeadzone || controller.getYLeftCoDriver() < controllerJoystickDeadzone*-1){
      elevator.manualElevatorControl(controller.getYLeftCoDriver());
    }else{
      elevator.manualElevatorControl(0);
    }
    if(controller.getYRightCoDriver() > controllerJoystickDeadzone || controller.getYRightCoDriver() < controllerJoystickDeadzone*-1){
      elevator.manualWristControl(controller.getYRightCoDriver());
    }else{
      elevator.manualWristControl(0.0);
    }
    System.out.println("testing");
    break;


    default:
      position=Position.MANUALOVERRIDE;
    break;
    }
    /*
    * The left trigger on the driver controller pulls in
    * The right trigger on the driver controller pushes out
    */
    if (controller.getRightTriggerDriver() > controllerTriggerDeadzone){
      intake.leftIntakeControl(0.75);
      intake.rightIntakeControl((0.75) * (-1));
    }else if(controller.getLeftTriggerDriver() > controllerTriggerDeadzone){
      intake.leftIntakeControl((0.75) * (-1));
      intake.rightIntakeControl(0.75);
    }else{
      intake.leftIntakeControl(0);
      intake.rightIntakeControl(0);
    }
  }
  

  /**
   * This function is called periodically during operator control.
   */

  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("Wrist Position", elevator.getWristPosition());
    SmartDashboard.putNumber("Elevator Velocity", elevator.maxVelocity());
    SmartDashboard.putNumber("Elevator Position", elevator.getElevatorPosition());

    /*
     * Controls the six wheel base using the Y axis on the right joystick to control power  *
     * and the X axis on the left joystick to adjust the output in order to allow the robot *
     * to turn
     */
    
    if (controller.getYLeftDriver() > controllerJoystickDeadzone){
      drive.rightSideControl(controller.getYLeftDriver() - (controller.getXRightDriver()*0.3));
      drive.leftSideControl(controller.getYLeftDriver() + (controller.getXRightDriver()*0.3));
    }else if(controller.getYLeftDriver() < controllerJoystickDeadzone * (-1) ){
      drive.rightSideControl(controller.getYLeftDriver() - (controller.getXRightDriver()*0.3));
      drive.leftSideControl(controller.getYLeftDriver() + (controller.getXRightDriver()*0.3));
    }else if(controller.getXRightDriver() < controllerJoystickDeadzone *(-1) || controller .getXRightDriver() > controllerJoystickDeadzone){
      drive.rightSideControl(controller.getXRightDriver()*(-1));
      drive.leftSideControl(controller.getXRightDriver());
    }else {
      drive.rightSideControl(0);
      drive.leftSideControl(0);
    }

    /*
    * Controls the elevator manually based on the input from the    *
    * codriver controller. The left joystick controls the elevator  *
    * input and the right joystick controls the wrist manually      *
    */

    if(controller.getYLeftCoDriver() > controllerJoystickDeadzone || controller.getYLeftCoDriver() < controllerJoystickDeadzone*-1){
      position = Position.MANUALOVERRIDE;
    }else{
    }
    if(controller.getYRightCoDriver() > controllerJoystickDeadzone || controller.getYRightCoDriver() < controllerJoystickDeadzone*-1){
      position = Position.MANUALOVERRIDE;
    }else{
    }

    /*
    * Changes the state of the vartiable 'position' which adjusts   *
    * the height of the elevator based on the positions of the      *
    * switch statement below                                        *
    */

if(controller.getLeftBumperDriver()){
drive.clawControl(1);;
}else if(controller.getRightBumperDriver()){
drive.clawControl(-1);
}else{
  drive.clawControl(0);
}

if(controller.getBackButtonDriver() || controller.getBackButtonCoDriver()){
  elevator.zero();
  intake.zero();
}

    if(controller.getButtonADriver()){
      positionTwo = PositionTwo.FINGEROUT;
    }else if(controller.getButtonBDriver()){
      positionTwo = PositionTwo.FINGERIN;
    }else if(controller.getButtonACoDriver()){
      position = Position.INTAKE;
    }else if(controller.getButtonBCoDriver()){
      position = Position.PANELLOW;
    }else if(controller.getButtonXCoDriver()){
      position = Position.PANELMID;
    }else if(controller.getButtonYDriver()){
      position = Position.PANELHIGH;
    }else if(controller.getRightTriggerCoDriver()>0.05){
      position = Position.MANUALOVERRIDE;
    }else if(controller.getLeftTriggerCoDriver() > controllerTriggerDeadzone){
      position = Position.CARGOHIGH;
    }else if(controller.getRightBumperCoDriver()){
      position = Position.CARGOMID;
    }else if(controller.getLeftBumperCoDriver()){
      position = Position.CARGOLOW;
    }else if(controller.getRightBumperCoDriver()){
      position = Position.ZERO;
    }else{
      // does not change state if no button is pressed
    }
  

    /*
    * switch statment switches the elevator position based on the state of the variable     *
    * 'position' which is determined by the if/else statement above by setting the variable *
    * and continuously checking it the robot is able to hold its position until the state   *
    * is changed by pressing a different button
    */

  switch(positionTwo){
    
    case FINGERIN:
    intake.setPanelIntakePosition(0);
    break;

    case FINGEROUT:
    intake.setPanelIntakePosition(-1200);
    break;

    case FINGEROFF:
    intake.panelIntakeControl(0);
    break;

    default:
    positionTwo = PositionTwo.FINGERIN;
    break;
  }

  switch(position){
    case PANELLOW:
    elevator.setWristPosition(225);
    elevator.setElevatorPosition(4300);
    break;

    case WRISTOUT:
    elevator.setWristPosition(500);
    break;

    case INTAKE:
    elevator.setElevatorPosition(711);
    elevator.setWristPosition(1412);
    intake.setPanelIntakePosition(0);
    break;
    
    case PANELMID:
    elevator.setWristPosition(225);
    elevator.setElevatorPosition(12733);
    break;
    
    case PANELHIGH:
    elevator.setElevatorPosition(12733);
    elevator.setWristPosition(100);
    break;
    
    case CARGOLOW:
    elevator.setElevatorPosition(3089);
    elevator.setWristPosition(770);
    break;
      
    case CARGOMID:
    elevator.setElevatorPosition(10309);
    elevator.setWristPosition(770);
    break;
    
    case CARGOHIGH:
    elevator.setElevatorPosition(16763);
    elevator.setWristPosition(548);
    break;
    
    case CARGOSHIP:
    elevator.setElevatorPosition(10309);
    elevator.setWristPosition(770);
    break;

    case  ZERO:
    elevator.setElevatorPosition(500);
    elevator.setWristPosition(50);
    break;
    

    case MANUALOVERRIDE:
    if (controller.getYLeftCoDriver() > controllerJoystickDeadzone || controller.getYLeftCoDriver() < controllerJoystickDeadzone*-1){
      elevator.manualElevatorControl(controller.getYLeftCoDriver());
    }else{
      elevator.manualElevatorControl(0);
    }
    if(controller.getYRightCoDriver() > controllerJoystickDeadzone || controller.getYRightCoDriver() < controllerJoystickDeadzone*-1){
      elevator.manualWristControl(controller.getYRightCoDriver());
    }else{
      elevator.manualWristControl(0.0);
    }
    System.out.println("testing");
    break;


    default:
      position=Position.MANUALOVERRIDE;
    break;
    }
    /*
    * The left trigger on the driver controller pulls in
    * The right trigger on the driver controller pushes out
    */
    if (controller.getRightTriggerDriver() > controllerTriggerDeadzone){
      intake.leftIntakeControl(0.75);
      intake.rightIntakeControl((0.75) * (-1));
    }else if(controller.getLeftTriggerDriver() > controllerTriggerDeadzone){
      intake.leftIntakeControl((0.75) * (-1));
      intake.rightIntakeControl(0.75);
    }else{
      intake.leftIntakeControl(0);
      intake.rightIntakeControl(0);
    }
  }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

    SmartDashboard.putBoolean("pressureSwitch", pneumatics.pressureSwitch);
    SmartDashboard.putBoolean("enabled", pneumatics.enabled);
    SmartDashboard.putNumber("compressor current", pneumatics.compressor.getCompressorCurrent());
    /*
    * Controls the drive system of the robot based on the output of the 
    * Limelight positioning software
    */

    pneumatics.solenoidOne.set(true);
    pneumatics.solenoidTwo.set(false);
    pneumatics.solenoidThree.set(true);
    pneumatics.solenoidFour.set(false);

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
   

    pneumatics.compressor.setClosedLoopControl(true);
    if(controller.getLeftBumperDriver()){
      pneumatics.solenoidOne.set(false);
      pneumatics.solenoidTwo.set(true);
      pneumatics.solenoidThree.set(false);
      pneumatics.solenoidFour.set(true);
    }else if(controller.getRightBumperDriver()){
      pneumatics.solenoidOne.set(true);
      pneumatics.solenoidTwo.set(false);
      pneumatics.solenoidThree.set(true);
      pneumatics.solenoidFour.set(false);
    }else{

    }

    if(controller.getButtonADriver() && intake.getPanelIntakePosition() == 1000){
      intake.setPanelIntakePosition(0);
    }else if(controller.getButtonADriver() && intake.getPanelIntakePosition() == 0){
      intake.setPanelIntakePosition(1000);
    }
    // controls the claws with the bumpers

   /* if(controller.getLeftBumperDriver() && controller.getRightTriggerDriver() > controllerTriggerDeadzone){
      drive.clawControl(controller.getRightTriggerDriver());
    }else if(controller.getLeftBumperDriver() && controller.getLeftTriggerDriver() > controllerTriggerDeadzone){
      drive.clawControl(controller.getLeftTriggerDriver() * (-1));
    }else{
      drive.clawControl(0);
    }*/
  }
}