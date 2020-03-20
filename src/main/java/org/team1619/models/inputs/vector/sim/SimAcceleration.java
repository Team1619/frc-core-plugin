package org.team1619.models.inputs.vector.sim;

import org.team1619.models.inputs.vector.Accelerometer;
import org.uacr.shared.abstractions.EventBus;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;

import java.util.HashMap;
import java.util.Map;

public class SimAcceleration extends Accelerometer {

	private SimInputVectorListener fListener;

	public SimAcceleration(EventBus eventBus, Object name, Config config, InputValues inputValues) {
		super(name, config);

		fListener = new SimInputVectorListener(eventBus, name, new HashMap<>());
	}

	@Override
	public Map<String, Double> getAcceleration() {
		return fListener.get();
	}
}
