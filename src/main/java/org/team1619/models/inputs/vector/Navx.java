package org.team1619.models.inputs.vector;

import org.uacr.models.inputs.vector.InputVector;
import org.uacr.utilities.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Navx extends InputVector {

    protected Map<String, Boolean> mIsRaidans;
    protected Map<String, Boolean> mIsInverted;
    protected Map<String, Double> mNavxValues;

    public Navx(Object name, Config config) {
        super(name, config);

        mNavxValues = new HashMap<>();

        //Is Inverted
        mIsInverted = new HashMap<>();
        mIsInverted.put("yaw", config.getBoolean("yaw_is_inverted", false));
        mIsInverted.put("roll", config.getBoolean("roll_is_inverted", false));
        mIsInverted.put("pitch", config.getBoolean("pitch_is_inverted", false));
        mIsInverted.put("compass", config.getBoolean("compass_is_inverted", false));
        mIsInverted.put("angle", config.getBoolean("angle_is_inverted", false));
        mIsInverted.put("fused_heading", config.getBoolean("fused_heading_is_inverted", false));
        mIsInverted.put("accel_x", config.getBoolean("accel_x_is_inverted", false));
        mIsInverted.put("accel_y", config.getBoolean("accel_y_is_inverted", false));
        mIsInverted.put("accel_z", config.getBoolean("accel_z_is_inverted", false));

        // Is radians
        mIsRaidans = new HashMap<>();
        mIsRaidans.put("yaw", config.getBoolean("yaw_is_radians", false));
        mIsRaidans.put("roll", config.getBoolean("roll_is_radians", false));
        mIsRaidans.put("pitch", config.getBoolean("pitch_is_radians", false));
        mIsRaidans.put("compass", config.getBoolean("compass_is_radians", false));
        mIsRaidans.put("angle", config.getBoolean("angle_is_radians", false));
        mIsRaidans.put("fused_heading", config.getBoolean("fused_heading_is_radians", false));
    }

    @Override
    public void update() {
        mNavxValues = readHardware();
    }

    @Override
    public void initialize() {
        mNavxValues = Map.of("Yaw", 0.0, "roll", 0.0, "pitch", 0.0, "compass", 0.0, "angle", 0.0, "fused_heading", 0.0, "accel_x", 0.0, "accel_y", 0.0, "accel_z", 0.0);
    }

    @Override
    public Map<String, Double> get() {
        return mNavxValues;
    }

    public void processFlags(Set<String> flags) {
        if (flags.equals("zero")) {
            zeroYaw();
        }
    }


    protected abstract Map<String, Double> readHardware();

    protected abstract void zeroYaw();
}
