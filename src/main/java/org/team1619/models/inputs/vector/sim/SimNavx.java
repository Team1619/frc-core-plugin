package org.team1619.models.inputs.vector.sim;

import org.team1619.models.inputs.vector.Navx;
import org.uacr.shared.abstractions.EventBus;
import org.uacr.utilities.Config;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;

import java.util.HashMap;
import java.util.Map;

public class SimNavx extends Navx {

    private static final Logger sLogger = LogManager.getLogger(SimNavx.class);

    private final SimInputVectorListener fListener;

    private double mNavxYaw;
    private double mNavxRoll;
    private double mNavxPitch;
    private double mNavxCompass;
    private double mNavxAngle;
    private double mNavxFusedHeading;
    private double mNavxAccelX;
    private double mNavxAccelY;
    private double mNavxAccelZ;


    public SimNavx(EventBus eventBus, Object name, Config config) {
        super(name, config);

        mNavxYaw = 0.0;
        mNavxRoll = 0.0;
        mNavxPitch = 0.0;
        mNavxCompass = 0.0;
        mNavxAngle = 0.0;
        mNavxFusedHeading = 0.0;
        mNavxAccelX = 0.0;
        mNavxAccelY = 0.0;
        mNavxAccelX = 0.0;

        fListener = new SimInputVectorListener(eventBus, name, Map.of("yaw", mNavxYaw, "roll", mNavxRoll, "pitch", mNavxPitch, "compass", mNavxCompass, "angle", mNavxAngle, "fused_heading", mNavxFusedHeading, "accel_x", mNavxAccelX, "accel_y", mNavxAccelY, "accel_z", mNavxAccelZ));
    }

    protected Map<String, Double> readHardware() {

        //Inverted
        mNavxYaw = getValue("yaw");
        mNavxRoll = getValue("roll");
        mNavxPitch = getValue("pitch");
        mNavxCompass = getValue("compass");
        mNavxAngle = getValue("angle");
        mNavxFusedHeading = getValue("fused_heading");
        mNavxAccelX = getValue("accel_x");
        mNavxAccelY = getValue("accel_y");
        mNavxAccelZ = getValue("accel_z");

        return Map.of("yaw", mNavxYaw, "roll", mNavxRoll, "pitch", mNavxPitch, "compass", mNavxCompass, "angle", mNavxAngle, "fused_heading", mNavxFusedHeading, "accel_x", mNavxAccelX, "accel_y", mNavxAccelY, "accel_z", mNavxAccelZ);
    }

    private double getValue(String name) {
        double value = mIsInverted.get(name) ? fListener.get().get(name) * -1 : fListener.get().get(name);
        return (mIsRaidans.containsKey(name) && mIsRaidans.get(name)) ? value * Math.PI / 180 : value;
    }

    protected void zeroYaw() {
        sLogger.debug("SimNavxInput -> Zeroing yaw");
        double yaw = mNavxValues.get("yaw");

        Map<String, Double> lastNavxValues = mNavxValues;
        mNavxValues = new HashMap<>();
        mNavxValues.putAll(lastNavxValues);

        mNavxValues.put("yaw", 0.0);
    }
}
