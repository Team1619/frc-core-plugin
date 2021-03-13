package org.team1619.models.inputs.vector;

import org.uacr.models.inputs.vector.InputVector;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;
import org.uacr.utilities.purepursuit.Point;
import org.uacr.utilities.purepursuit.Pose2d;
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

    private Vector lastAbsoluteOdometryPosition;
    private Vector relativeOdometryOffset;
    private Pose2d odometryPosition;

    public OdometryFuser(Object name, Config config, InputValues inputValues) {
        super(name, config);

        this.inputValues = inputValues;

        relativeOdometryInput = config.getString("relative_odometry_input");
        absoluteOdometryInput = config.getString("absolute_odometry_input");
        absoluteOdometryDelay = config.getInt("absolute_odometry_delay");

        movementBuffer = new HashMap<>();

        lastAbsoluteOdometryPosition = new Vector();
        relativeOdometryOffset = new Vector();
        odometryPosition = new Pose2d();
    }

    @Override
    public void initialize() {
        movementBuffer.clear();

        lastAbsoluteOdometryPosition = new Vector();
        relativeOdometryOffset = new Vector();
    }

    @Override
    public void update() {
        long currentTime = System.currentTimeMillis();

        Map<String, Double> relativeOdometryValues = inputValues.getVector(relativeOdometryInput);
        Map<String, Double> absoluteOdometryValues = inputValues.getVector(absoluteOdometryInput);

        Vector relativeOdometryPosition = new Vector(new Point(relativeOdometryValues.get("x"), relativeOdometryValues.get("y")));
        Vector relativeOdometryDelta = new Vector(new Point(relativeOdometryValues.get("dx"), relativeOdometryValues.get("dy")));

        movementBuffer.put(currentTime, relativeOdometryDelta);

        movementBuffer.keySet().removeIf(t -> t < currentTime - absoluteOdometryDelay);

        if(absoluteOdometryValues.getOrDefault("valid", 0.0) == 1.0) {

            Vector absoluteOdometryPosition = new Vector(new Point(absoluteOdometryValues.get("x"), absoluteOdometryValues.get("y")));

            if(!absoluteOdometryPosition.equals(lastAbsoluteOdometryPosition)) {
                movementBuffer.values().stream().reduce((t, v) -> new Vector(t.add(v))).ifPresent(t -> {
                    relativeOdometryOffset = new Vector(absoluteOdometryPosition.subtract(relativeOdometryPosition));
                });
                lastAbsoluteOdometryPosition = absoluteOdometryPosition;
            }
        }

        odometryPosition = new Pose2d(relativeOdometryPosition.add(relativeOdometryOffset), relativeOdometryValues.get("heading"));
    }

    @Override
    public Map<String, Double> get() {
        return Map.of("x", odometryPosition.getX(), "y", odometryPosition.getY(), "heading", odometryPosition.getHeading());
    }

    @Override
    public void processFlag(String flag) {
        if("zero".equals(flag)) {
            lastAbsoluteOdometryPosition = new Vector(Integer.MAX_VALUE, 0);
        }
    }
}
