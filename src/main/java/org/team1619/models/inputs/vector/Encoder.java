package org.team1619.models.inputs.vector;

import org.uacr.models.inputs.vector.InputVector;
import org.uacr.utilities.Config;

import java.util.HashMap;
import java.util.Map;

public abstract class Encoder extends InputVector {

    protected final Config config;
    protected final int deviceNumber;
    protected final boolean readPosition;
    protected final boolean readAbsolutePosition;
    protected final boolean readVelocity;
    protected final boolean bootToAbsolutePosition;
    protected final boolean sensorRange360;
    protected final boolean inverted;
    protected final double positionScalar;
    protected final double velocityScalar;
    protected final int magnetOffset;

    protected  Map<String, Double> encoderValues;

    public Encoder(Object name, Config config){
        super (name, config);
        this.config = config;
        deviceNumber = config.getInt("device_number");
        magnetOffset = config.getInt("magnet_offset");
        readPosition = config.getBoolean("read_position");
        readAbsolutePosition = config.getBoolean("read_absolute_position");
        readVelocity = config.getBoolean("read_velocity");
        sensorRange360 = config.getBoolean("sensor_range_360");
        inverted = config.getBoolean("inverted");
        positionScalar = config.getDouble("position_scalar");
        velocityScalar = config.getDouble("velocity_scalar");
        bootToAbsolutePosition = config.getBoolean("boot_to_absolute_position");



        encoderValues = new HashMap<>();
    }

    @Override
    public void initialize() {
            encoderValues = Map.of("position", 0.0, "velocity", 0.0);
    }

    @Override
    public void update() {
        encoderValues = readHardware();
    }

    @Override
    public Map<String, Double> get() {
        return encoderValues;
    }

    @Override
    public void processFlag(String flag) {
        if (flag.equals("zero")) {
            zeroEncoder();
        }
    }

    protected abstract void zeroEncoder();

    protected abstract Map<String,Double> readHardware();


}
