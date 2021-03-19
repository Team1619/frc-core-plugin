package org.team1619.models.inputs.vector;

import org.uacr.models.inputs.vector.InputVector;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.utilities.Config;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;
import org.uacr.utilities.purepursuit.Pose2d;
import org.uacr.utilities.purepursuit.Vector;

import java.util.Map;

public abstract class BaseOdometry extends InputVector {

    private static final Logger LOGGER = LogManager.getLogger(BaseOdometry.class);

    protected final Config config;
    protected final InputValues sharedInputValues;
    protected final UpdateMode updateMode;

    private Pose2d currentPosition;
    private Pose2d positionUpdate;
    private Pose2d lastPositionUpdate;
    private Vector positionDelta;

    public BaseOdometry(Object name, Config config, InputValues inputValues, UpdateMode updateMode) {
        super(name, config);

        this.config = config;
        this.sharedInputValues = inputValues;
        this.updateMode = updateMode;

        currentPosition = new Pose2d();
        positionUpdate = new Pose2d();
        lastPositionUpdate = new Pose2d();
        positionDelta = new Vector();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void update() {
        lastPositionUpdate = positionUpdate;
        positionUpdate = getPositionUpdate();

        if(UpdateMode.DELTA_POSITION == updateMode) {
            currentPosition = new Pose2d(currentPosition.add(positionUpdate), positionUpdate.getHeading());
            positionDelta = new Vector(positionUpdate);
        } else {
            currentPosition = positionUpdate;
            positionDelta = new Vector(positionDelta.subtract(lastPositionUpdate));
        }
    }

    @Override
    public Map<String, Double> get() {
        return Map.of("x", currentPosition.getX(), "y", currentPosition.getY(), "dx", positionDelta.getX(), "dy", positionDelta.getY(), "heading", currentPosition.getHeading());
    }

    @Override
    public void processFlag(String flag) {
        if("zero".equals(flag)) {
            zero();
            currentPosition = new Pose2d();

            LOGGER.debug("{} -> Zero", fName);
        }
    }

    protected Pose2d getCurrentPosition() {
        return currentPosition;
    }

    protected abstract Pose2d getPositionUpdate();

    protected abstract void zero();

    protected enum UpdateMode {
        ABSOLUTE_POSITION,
        DELTA_POSITION
    }
}
