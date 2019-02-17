/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;



/**
 * 
 * 
 * Add your docs here.
 */


public class Vision {
	 private static Vision instance_;
	public static Vision getInstance() {
		if(instance_ == null) {
			instance_ = new Vision();
		}
		return instance_;
	}


	private NetworkTable m_table;
	private String m_tableName; 



public Vision() {
	m_tableName = "limelight";
	m_table = NetworkTableInstance.getDefault().getTable(m_tableName);


}

public Vision(String tableName){
	m_tableName = tableName; 
	m_table = NetworkTableInstance.getDefault().getTable(m_tableName);
}
public Vision(NetworkTable table){
	m_table = table;
}

public void LimeLightInit(){}

private void testAllTab(){
	ShuffleboardTab LimeLightTab = Shuffleboard.getTab(m_tableName);
}
public boolean getIsTargetFound() {
	NetworkTableEntry tv = m_table.getEntry("tv");
	double v = tv.getDouble(0);
	if (v == 0.0f){
		return false;
	}else {
		return true;}
	}



public double getdegRotationtoTarget() {
	NetworkTableEntry tx = m_table.getEntry("tx");
	double x = tx.getDouble(0.0);
	return x; 
}

public double getdegVerticaltoTarget() {
	NetworkTableEntry ty = m_table.getEntry("ty");
	double y = ty.getDouble(0.0);
	return y;
}
public double getTargetArea() {
	NetworkTableEntry ta = m_table.getEntry("ta");
	double a = ta.getDouble(0.0);
	return a;
}

public double getSkew_Rotation() {
	NetworkTableEntry ts = m_table.getEntry("ts");
	double s = ts.getDouble(0.0);
	return s;
}


 /**
     * tl The pipeline’s latency contribution (ms) Add at least 11ms for image capture latency.
     * @return
     */
    public double getPipelineLatency() {
        NetworkTableEntry tl = m_table.getEntry("tl");
        double l = tl.getDouble(0.0);
        return l;
    }

    private void resetPilelineLatency(){
        m_table.getEntry("tl").setValue(0.0);
    }
    
    }















   


