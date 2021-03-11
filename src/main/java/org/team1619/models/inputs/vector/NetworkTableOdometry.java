package org.team1619.models.inputs.vector;

import org.uacr.models.inputs.vector.InputVector;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;
import org.uacr.utilities.purepursuit.Vector;

import java.util.Map;

public class NetworkTableOdometry extends InputVector {

    private final InputValues inputValues;
    private final String networkTablesInput;

    private double tv;
    private Vector position;
    private Vector offset;

    public NetworkTableOdometry(Object name, Config config, InputValues inputValues) {
        super(name, config);

        this.inputValues = inputValues;

        networkTablesInput = config.getString("network_table_input");

        initialize();
    }

    @Override
    public void initialize() {
        tv = 0.0;
        position = new Vector();
        offset = new Vector();
    }

    @Override
    public void update() {
        Map<String, Double> networkTablesValues = inputValues.getVector(networkTablesInput);

        position = new Vector(networkTablesValues.get("x"), networkTablesValues.get("y"));
    }

    @Override
    public Map<String, Double> get() {
        return Map.of("x", position.getX(), "y", position.getY());
    }

    @Override
    public void processFlag(String flag) {
        if("zero".equals(flag)) {
            offset = new Vector(new Vector().subtract(position));
        }
    }
}
