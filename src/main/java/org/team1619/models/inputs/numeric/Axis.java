package org.team1619.models.inputs.numeric;

import org.uacr.models.inputs.numeric.InputNumeric;
import org.uacr.utilities.Config;

public abstract class Axis extends InputNumeric {

    protected final int fPort;
    protected final int fAxis;

    private double fAxisValue = 0.0;
    private double fDelta = 0.0;
    private double fDeadBand = 0.0;
    private double fScale = 0.0;

    public Axis(Object name, Config config) {
        super(name, config);

        fPort = config.getInt("port");
        fAxis = config.getInt("axis");
        fDeadBand = config.getDouble("deadband", 0.0);
        fScale = config.getDouble("scale", 1.0);
    }

    @Override
    public void update() {
        double nextAxis = fIsInverted ? -getAxis() : getAxis();
        if (Math.abs(nextAxis) < fDeadBand) {
            nextAxis = 0.0;
        }
        nextAxis = nextAxis * fScale;
        fDelta = nextAxis - fAxisValue;
        fAxisValue = nextAxis;
    }

    @Override
    public void initialize() {

    }

    @Override
    public double get() {
        return fAxisValue;
    }

    @Override
    public double getDelta() {
        return fDelta;
    }

    protected abstract double getAxis();

    @Override
    public void processFlag(String flag) {

    }
}
