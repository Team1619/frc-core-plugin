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
    Preferences mPrefs = Preferences.getInstance();
    SendableChooser<String> mAutoOrigin = new SendableChooser<>();
    SendableChooser<String> mAutoDestination = new SendableChooser<>();
    SendableChooser<String> mAutoAction = new SendableChooser<>();
    private InputValues mSharedInputValues;
    private String mPreviousAutoOrigin = "none";
    private String mPreviousAutoDestination = "none";
    private String mPreviousAutoAction = "none";


    @Inject
    public RobotDashboard(InputValues inputValues) {
        mSharedInputValues = inputValues;
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
        return mPrefs.getDouble(name, -1);
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
        mSharedInputValues.setString("ips_auto_origin", mAutoOrigin.getSelected());
        mSharedInputValues.setString("ips_auto_destination", mAutoDestination.getSelected());
        mSharedInputValues.setString("ips_auto_action", mAutoAction.getSelected());
        mSharedInputValues.setString("ips_selected_auto",
                mSharedInputValues.getString("ips_auto_origin") + " to " +
                        mSharedInputValues.getString("ips_auto_destination") + ", " +
                        mSharedInputValues.getString("ips_auto_action"));
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
