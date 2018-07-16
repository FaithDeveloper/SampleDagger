package com.kcs.sampledagger.dagger_example;


import javax.inject.Inject;

public class A_Pump implements Pump {

    private final Heater heater;

    @Inject
    public A_Pump(Heater heater) {
        this.heater = heater;
    }

    public Heater getHeater() {
        return heater;
    }

    @Override
    public void pump() {
        if (heater.isHot()) {
            System.out.println("A_Pump => pumping ~");
        }
    }
}
