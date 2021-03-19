package org.team1619.models.inputs.vector;

import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;
import org.uacr.utilities.purepursuit.Point;
import org.uacr.utilities.purepursuit.Pose2d;
import org.uacr.utilities.purepursuit.Vector;

import java.util.Map;

public class NetworkTableOdometry extends BaseOdometry {

    private final String networkTablesInput;

    private double valid;
    private Vector offset;

    public NetworkTableOdometry(Object name, Config config, InputValues inputValues) {
        super(name, config, inputValues, UpdateMode.ABSOLUTE_POSITION);

        networkTablesInput = config.getString("network_table_input");

        initialize();

        offset = new Vector();
    }

    @Override
    public void initialize() {
        valid = 0.0;
    }

    @Override
    public Pose2d getPositionUpdate() {
        Map<String, Double> networkTablesValues = sharedInputValues.getVector(networkTablesInput);

        valid = networkTablesValues.get("valid");

        return new Pose2d(new Point(networkTablesValues.get("x"), networkTablesValues.get("y")), 0.0);
    }

    @Override
    protected void zero() {
        offset = new Vector(new Vector().subtract(getCurrentPosition()));
    }

    @Override
    public Map<String, Double> get() {
//        return Map.of("x", output.getX(), "y", output.getY(), "valid", valid);
        return Map.of();
    }
}
