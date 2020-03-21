package org.team1619.models.inputs.bool;

import org.uacr.models.inputs.bool.InputBoolean;
import org.uacr.utilities.Config;

public abstract class Button extends InputBoolean {

    protected final int fPort;
    protected final String fButton;

    private boolean fIsPressed = false;
    private DeltaType fDelta = DeltaType.NO_DELTA;

    public Button(Object name, Config config) {
        super(name, config);

        fPort = config.getInt("port");
        fButton = config.getString("button");
    }

    @Override
    public void update() {
        boolean nextIsPressed = fIsInverted != isPressed();

        if (nextIsPressed && !fIsPressed) {
            fDelta = DeltaType.RISING_EDGE;
        } else if (!nextIsPressed && fIsPressed) {
            fDelta = DeltaType.FALLING_EDGE;
        } else {
            fDelta = DeltaType.NO_DELTA;
        }

        fIsPressed = nextIsPressed;
    }

    @Override
    public void initialize() {

    }

    @Override
    public boolean get() {
        return fIsPressed;
    }

    @Override
    public DeltaType getDelta() {
        return fDelta;
    }

    public abstract boolean isPressed();

    @Override
    public void processFlag(String flag) {

    }
}
