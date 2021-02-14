package org.team1619.models.inputs.vector;

import org.uacr.models.inputs.vector.InputVector;
import org.uacr.utilities.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Limelight extends InputVector {

    protected final Map<String, Integer> fPipelines;
    private Map<String, Double> mValues;

    public Limelight(Object name, Config config) {
        super(name, config);

        fPipelines = new HashMap<>();
        if (config.contains("pipelines")) {
            for (Map.Entry<String, Object> pipeline : config.getSubConfig("pipelines", "pipeline_config").getData().entrySet()) {
                fPipelines.put(pipeline.getKey(), Integer.valueOf(pipeline.getValue().toString()));
            }
        }
        mValues = new HashMap<>();
    }

    @Override
    public void update() {
        mValues = getData();
    }

    @Override
    public Map<String, Double> get() {
        return mValues;
    }

    @Override
    public void processFlag(Set<String> flags) {
        if(!flags.isEmpty()){
            System.out.println(flags);
        }
    }

    public abstract Map<String, Double> getData();
}
