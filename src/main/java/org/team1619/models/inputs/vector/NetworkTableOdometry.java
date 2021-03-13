package org.team1619.models.inputs.vector;

import org.uacr.models.inputs.vector.InputVector;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;
import org.uacr.utilities.purepursuit.Point;
import org.uacr.utilities.purepursuit.Vector;

import java.util.Map;

public class NetworkTableOdometry extends InputVector {

    private final InputValues inputValues;
    private final String networkTablesInput;

    private Vector position;
    private double valid;
    private Vector offset;

    public NetworkTableOdometry(Object name, Config config, InputValues inputValues) {
        super(name, config);

        this.inputValues = inputValues;

        networkTablesInput = config.getString("network_table_input");

        initialize();

        offset = new Vector();
    }

    @Override
    public void initialize() {
        position = new Vector();
        valid = 0.0;
    }

    @Override
    public void update() {
        Map<String, Double> networkTablesValues = inputValues.getVector(networkTablesInput);

        position = new Vector(new Point(networkTablesValues.get("x"), networkTablesValues.get("y")));
        valid = networkTablesValues.get("valid");
    }

    @Override
    public Map<String, Double> get() {
        Vector output = new Vector(position.add(offset));
        return Map.of("x", output.getX(), "y", output.getY(), "valid", valid);
    }

    @Override
    public void processFlag(String flag) {
        if("zero".equals(flag)) {
            offset = new Vector(new Vector().subtract(position));
        }
    }
}
