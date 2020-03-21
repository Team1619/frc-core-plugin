package org.team1619.models.inputs.numeric.sim;

import org.uacr.events.sim.SimInputNumericSetEvent;
import org.uacr.shared.abstractions.EventBus;
import org.uacr.utilities.eventbus.Subscribe;

public class SimInputNumericListener {

    private final Object fName;

    private double fValue = 0.0;

    public SimInputNumericListener(EventBus eventBus, Object name) {
        eventBus.register(this);
        fName = name;
    }

    public double get() {
        return fValue;
    }

    @Subscribe
    public void onInputNumericSet(SimInputNumericSetEvent event) {
        if (event.name.equals(fName)) {
            fValue = event.value;
        }
    }
}
