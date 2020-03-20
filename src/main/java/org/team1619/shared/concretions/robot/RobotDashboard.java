package org.team1619.shared.concretions.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1619.shared.abstractions.Dashboard;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.injection.Inject;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;


public class RobotDashboard implements Dashboard {

	private static final Logger sLogger = LogManager.getLogger(RobotDashboard.class);
	Preferences prefs = Preferences.getInstance();
	SendableChooser<String> fAutoOrigin = new SendableChooser<>();
	SendableChooser<String> fAutoDestination = new SendableChooser<>();
	SendableChooser<String> fAutoAction = new SendableChooser<>();
	private InputValues fSharedInputValues;
	private String fPreviousAutoOrigin = "none";
	private String fPreviousAutoDestination = "none";
	private String fPreviousAutoAction = "none";


	@Inject
	public RobotDashboard(InputValues inputValues) {
		fSharedInputValues = inputValues;
	}

	@Override
	public void initialize() {
//		fAutoOrigin.setDefaultOption("None", "None");
//		fAutoOrigin.addOption("Left", "Left");
//		fAutoOrigin.addOption("Right", "Right");
//		fAutoOrigin.addOption("Center", "Center");
//		SmartDashboard.putData("Origin", fAutoOrigin);
//		fAutoDestination.setDefaultOption("None", "None");
//		fAutoDestination.addOption("Trench", "Trench Left");
//		fAutoDestination.addOption("Shield Generator", "Shield Generator");
//		fAutoDestination.addOption("None", "None");
//		SmartDashboard.putData("Action", fAutoAction);
//		fAutoAction.setDefaultOption("None", "None");
//		fAutoAction.addOption("Shoot 3 Collect 5", "Shoot 3 Collect 5");
//		fAutoAction.addOption("Shoot 3", "Shoot 3");
//		fAutoAction.addOption("8 Ball", "8 Ball");
//		SmartDashboard.putData("Destination", fAutoDestination);
//		fPreviousAutoOrigin = fAutoOrigin.getSelected();
//		fPreviousAutoDestination = fAutoDestination.getSelected();
//		fPreviousAutoAction = fAutoAction.getSelected();

	}

	@Override
	public void putNumber(String name, double value) {
		SmartDashboard.putNumber(name, value);
	}

	@Override
	public void putBoolean(String name, boolean value) {
		SmartDashboard.putBoolean(name, value);
	}

	//Intended for Preferences
	@Override
	public double getNumber(String name) {
		return prefs.getDouble(name, -1);
	}

	@Override
	public void putString(String key, String value) {
		SmartDashboard.putString(key, value);
	}

	@Override
	public void setNetworkTableValue(String table, String entry, Object value) {
		NetworkTableInstance.getDefault().getTable(table).getEntry(entry).setValue(value);
	}

	@Override
	public void smartdashboardSetAuto() {
		fSharedInputValues.setString("ips_auto_origin", fAutoOrigin.getSelected());
		fSharedInputValues.setString("ips_auto_destination", fAutoDestination.getSelected());
		fSharedInputValues.setString("ips_auto_action", fAutoAction.getSelected());
		fSharedInputValues.setString("ips_selected_auto",
				fSharedInputValues.getString("ips_auto_origin") + " to " +
						fSharedInputValues.getString("ips_auto_destination") + ", " +
						fSharedInputValues.getString("ips_auto_action"));
	}

	@Override
	public boolean autoSelectionRisingEdge() {
//		String origin = fAutoOrigin.getSelected();
//		String destination = fAutoDestination.getSelected();
//		String action = fAutoAction.getSelected();
//		if(origin != null && destination != null && action != null && (!fPreviousAutoOrigin.equals(origin) || !fPreviousAutoDestination.equals(destination) || !fPreviousAutoAction.equals(action))) {
//			fPreviousAutoOrigin = origin;
//			fPreviousAutoDestination = destination;
//			fPreviousAutoAction = action;
//			return true;
//		}
		return false;
	}

}
