package org.team1619.models.outputs.numeric;

import org.uacr.models.outputs.numeric.OutputNumeric;
import org.uacr.utilities.Config;

/**
 * Rumble is a motor object, which is extended to control the xbox controller rumble motors
 */

public abstract class Rumble extends OutputNumeric {

	protected final Object fName;
	protected final int fPort;
	protected final String fRumbleSide;

	public Rumble(Object name, Config config) {
		super(name, config);

		fName = name;
		fPort = config.getInt("port");
		fRumbleSide = config.getString("rumble_side", "none");
	}
}
