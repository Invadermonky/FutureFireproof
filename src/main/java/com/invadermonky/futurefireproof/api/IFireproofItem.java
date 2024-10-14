package com.invadermonky.futurefireproof.api;

public interface IFireproofItem {
    /**
     * How fast the item will decay when in lava or fire. A value of 1 will have the vanilla item decay rate. A value of 2
     * will cause the item to decay twice as fast in lava.
     */
    default int getPerTickLavaDecay() {
        return 1;
    }
}
