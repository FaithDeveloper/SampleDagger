package com.kcs.sampledagger.di_example;

public class A_Heater implements Heater {
    boolean heating;

    @Override
    public void on() {
        System.out.println("A_Heater~ ~ heating ~ ~ ~");
        this.heating = true;
    }

    @Override
    public void off() {

    }

    @Override
    public boolean isHot() {
        return false;
    }
}
