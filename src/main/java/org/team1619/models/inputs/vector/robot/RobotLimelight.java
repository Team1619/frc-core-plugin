package org.team1619.models.inputs.vector.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import org.team1619.models.inputs.vector.Limelight;
import org.uacr.utilities.Config;

import java.util.HashMap;
import java.util.Map;

	/* ***** Defintitions  ******
	tv 	Whether the limelight has any valid targets (0 or 1)
	tx 	Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
	ty 	Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
	ta 	Target Area (0% of image to 100% of image)
	ts 	Skew or rotation (-90 degrees to 0 degrees)
	tl 	The pipeline’s latency contribution (ms) Add at least 11ms for image capture latency.
	dx      Delta left/right from target (negative = target it to the right of the robot)
	dy      Delta up/down from target
	dz      Delta to target (negative = target is in front of the robot)
	pitch   Angle up/down (negative = target is below robot)
	roll    Angle left/right (negative = target is to the left of robot)
	yaw     Target angle relative to floor (negative = target is rotated  counter clock wise)
	*****************************/


public class RobotLimelight extends Limelight {

	private NetworkTable fTable;
	private double fAngleConversion;

	public RobotLimelight(Object name, Config config) {
		super(name, config);
		fAngleConversion = config.getBoolean("degrees", false) ? 1.0 : (Math.PI / 180.0);
		if (config.getString("host").isBlank()) {
			fTable = NetworkTableInstance.getDefault().getTable("limelight");
		} else {
			fTable = NetworkTableInstance.getDefault().getTable("limelight-" + config.getString("host"));
		}
		if(config.contains("pnp")) {
			processFlag(config.getString("pnp"));
		}
	}

	@Override
	public Map<String, Double> getData() {
		Map<String, Double> values = new HashMap<>();
		values.put("tv", fTable.getEntry("tv").getDouble(0));
		values.put("tx", fAngleConversion * fTable.getEntry("tx").getDouble(0));
		values.put("ty", fAngleConversion * fTable.getEntry("ty").getDouble(0));
		values.put("ta", fTable.getEntry("ta").getDouble(0));
		values.put("ts", fAngleConversion * fTable.getEntry("ts").getDouble(0));
		values.put("tl", fTable.getEntry("tl").getDouble(0));
		Number[] myDefault = new Number[]{0, 0, 0, 0, 0, 0};
		Number[] camtran = fTable.getEntry("camtran").getNumberArray(myDefault);
		values.put("dx", camtran[0].doubleValue());
		values.put("dy", camtran[1].doubleValue());
		values.put("dz", camtran[2].doubleValue());
		values.put("pitch", fAngleConversion * camtran[3].doubleValue());
		values.put("roll", fAngleConversion * camtran[4].doubleValue());
		values.put("yaw", fAngleConversion * camtran[5].doubleValue());

		return values;
	}

	@Override
	public void processFlag(String flag) {
		if(fPipelines.containsKey(flag)) {
			NetworkTableEntry pipelineEntry = fTable.getEntry("pipeline");
			pipelineEntry.setNumber(fPipelines.get(flag));
		} else if(flag.contains("pnp")){
			NetworkTableEntry pnpEntry = fTable.getEntry("stream");
			switch (flag) {
				case "pnp-standard":
					pnpEntry.setNumber(0);
					break;
				case "pnp-main":
					pnpEntry.setNumber(1);
					break;
				case "pnp-secondary":
					pnpEntry.setNumber(2);
					break;
			}
		} else if (flag.contains("led")) {
			switch (flag) {
				case "led-on":
					fTable.getEntry("ledMode").setValue(0);
					break;
				case "led-off":
					fTable.getEntry("ledMode").setValue(1);
					break;
			}
		}
	}

	@Override
	public void initialize() {
	}
}
