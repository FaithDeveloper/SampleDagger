package com.kcs.sampledagger.dagger_example;


public class A_Heater implements Heater {
    boolean heating;

    @Override
    public void on() {
        System.out.println("A_Heater! heating... .. . ");
        this.heating = true;
    }

    @Override
    public void off() {
        this.heating = false;
    }

    @Override
    public boolean isHot() {
        return false;
    }
}
