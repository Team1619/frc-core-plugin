package org.team1619.models.inputs.vector;

import org.uacr.models.inputs.vector.InputVector;
import org.uacr.utilities.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Accelerometer extends InputVector {

    private Map<String, Double> mAcceleration = new HashMap<>();

    public Accelerometer(Object name, Config config) {
        super(name, config);
    }

    @Override
    public void update() {
        mAcceleration = getAcceleration();
    }

    @Override
    public void initialize() {

    }

    @Override
    public Map<String, Double> get() {
        return mAcceleration;
    }

    @Override
    public void processFlags(Set<String> flags) {

    }

    public abstract Map<String, Double> getAcceleration();
}
