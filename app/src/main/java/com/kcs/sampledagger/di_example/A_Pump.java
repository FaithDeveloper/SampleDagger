package com.kcs.sampledagger.di_example;

public class A_Pump implements Pump {

    private final Heater heater;

    public A_Pump(Heater heater) {
        this.heater = heater;
    }

    @Override
    public void pump() {
        if (heater.isHot()) {
            System.out.println("A_Pump> pumping => =>");
        }
    }
}
