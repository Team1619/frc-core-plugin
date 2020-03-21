package org.team1619.models.inputs.vector.sim;

import org.uacr.events.sim.SimInputVectorSetEvent;
import org.uacr.shared.abstractions.EventBus;
import org.uacr.utilities.eventbus.Subscribe;

import java.util.Map;

public class SimInputVectorListener {

    private final Object fName;
    private Map<String, Double> fValues;

    public SimInputVectorListener(EventBus eventBus, Object name, Map<String, Double> startingValues) {
        eventBus.register(this);
        fName = name;
        fValues = startingValues;
    }

    public Map<String, Double> get() {
        return fValues;
    }

    @Subscribe
    public void onInputVectorSet(SimInputVectorSetEvent event) {
        if (event.name.equals(fName)) {
            fValues = event.values;
        }
    }
}
