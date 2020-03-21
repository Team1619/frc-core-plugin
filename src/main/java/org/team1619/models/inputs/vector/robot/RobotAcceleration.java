package org.team1619.models.inputs.vector.robot;

import org.team1619.models.inputs.vector.Accelerometer;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;

import java.util.HashMap;
import java.util.Map;

public class RobotAcceleration extends Accelerometer {

    private final InputValues fSharedInputValues;
    private Map<String, Double> fNavxValues;

    public RobotAcceleration(Object name, Config config, InputValues inputValues) {
        super(name, config);
        fSharedInputValues = inputValues;
        fNavxValues = new HashMap<>();
    }

    @Override
    public Map<String, Double> getAcceleration() {
        fNavxValues = fSharedInputValues.getVector("ipv_navx");

        double xAcceleration = fNavxValues.getOrDefault("accel_x", 0.0);
        double yAcceleration = fNavxValues.getOrDefault("accel_y", 0.0);
        double zAcceleration = fNavxValues.getOrDefault("accel_z", 0.0);

        return Map.of("xAcceleration", xAcceleration, "yAcceleration", yAcceleration, "zAcceleration", zAcceleration);
    }
}
