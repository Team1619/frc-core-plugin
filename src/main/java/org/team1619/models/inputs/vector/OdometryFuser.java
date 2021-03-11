package org.team1619.models.inputs.vector;

import org.uacr.models.inputs.vector.InputVector;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;
import org.uacr.utilities.purepursuit.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class OdometryFuser extends InputVector {

    private final InputValues inputValues;

    private final String relativeOdometryInput;
    private final String absoluteOdometryInput;
    private final int absoluteOdometryDelay;

    private final Map<Long, Vector> movementBuffer;

    private Vector relativeOdometryOffset;

    public OdometryFuser(Object name, Config config, InputValues inputValues) {
        super(name, config);

        this.inputValues = inputValues;

        relativeOdometryInput = config.getString("relative_odometry_input");
        absoluteOdometryInput = config.getString("absolute_odometry_input");
        absoluteOdometryDelay = config.getInt("absolute_odometry_delay");

        movementBuffer = new HashMap<>();

        relativeOdometryOffset = new Vector();
    }

    @Override
    public void initialize() {
        movementBuffer.clear();
    }

    @Override
    public void update() {
        long currentTime = System.currentTimeMillis();

        Map<String, Double> relativeOdometryValues = inputValues.getVector(relativeOdometryInput);
        Map<String, Double> absoluteOdometryValues = inputValues.getVector(absoluteOdometryInput);

        Vector relativeOdometryDelta = new Vector(relativeOdometryValues.get("dx"), relativeOdometryValues.get("dy"));

        movementBuffer.put(currentTime, relativeOdometryDelta);

        for(long time : movementBuffer.keySet()) {
            if(time < currentTime - absoluteOdometryDelay) {
                movementBuffer.remove(time);
            }
        }

        if(absoluteOdometryValues.get("tx") == 1.0) {
            movementBuffer.values().stream().reduce((t, v) -> new Vector(t.add(v))).ifPresent(t -> {
//                relativeOdometryValues.
            });
        }
    }

    @Override
    public Map<String, Double> get() {
        return null;
    }

    @Override
    public void processFlag(String flag) {

    }
}
