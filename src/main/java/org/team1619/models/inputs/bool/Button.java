package org.team1619.models.inputs.bool;

import org.uacr.models.inputs.bool.InputBoolean;
import org.uacr.utilities.Config;

import java.util.Set;

public abstract class Button extends InputBoolean {

    protected final int fPort;
    protected final String fButton;

    private DeltaType mDelta = DeltaType.NO_DELTA;
    private boolean mIsPressed = false;

    public Button(Object name, Config config) {
        super(name, config);

        fPort = config.getInt("port");
        fButton = config.getString("button");
    }

    @Override
    public void update() {
        boolean nextIsPressed = fIsInverted != isPressed();

        if (nextIsPressed && !mIsPressed) {
            mDelta = DeltaType.RISING_EDGE;
        } else if (!nextIsPressed && mIsPressed) {
            mDelta = DeltaType.FALLING_EDGE;
        } else {
            mDelta = DeltaType.NO_DELTA;
        }

        mIsPressed = nextIsPressed;
    }

    @Override
    public void initialize() {

    }

    @Override
    public boolean get() {
        return mIsPressed;
    }

    @Override
    public DeltaType getDelta() {
        return mDelta;
    }

    public abstract boolean isPressed();

    @Override
    public void processFlags(Set<String> flags) {

    }
}
