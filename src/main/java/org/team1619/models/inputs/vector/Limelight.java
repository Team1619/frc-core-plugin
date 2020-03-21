package org.team1619.models.inputs.vector;

import org.uacr.models.inputs.vector.InputVector;
import org.uacr.utilities.Config;

import java.util.HashMap;
import java.util.Map;

public abstract class Limelight extends InputVector {

    protected Map<String, Integer> fPipelines = new HashMap<>();
    private Map<String, Double> fValues = new HashMap<>();

    public Limelight(Object name, Config config) {
        super(name, config);

        if (config.contains("pipelines")) {
            for (Map.Entry<String, Object> pipeline : config.getSubConfig("pipelines", "pipeline_config").getData().entrySet()) {
                fPipelines.put(pipeline.getKey(), Integer.valueOf(pipeline.getValue().toString()));
            }
        }
    }

    @Override
    public void update() {
        fValues = getData();
    }

    @Override
    public Map<String, Double> get() {
        return fValues;
    }

    @Override
    public void processFlag(String flag) {

    }

    public abstract Map<String, Double> getData();
}
