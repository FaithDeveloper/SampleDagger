package com.kcs.sampledagger.dagger_example;

import javax.inject.Inject;

public class Coffeemaker {
    @Inject Heater heater;
    @Inject Pump pump;

    @Inject
    public Coffeemaker(Heater heater, Pump pump){
        this.heater = heater;
        this.pump = pump;
    }

    public Coffeemaker(){

    }

    public void brew(){
        heater.on();
        pump.pump();
        System.out.println(" ===== coffee... =====  ");
        heater.off();
    }

    public Heater getHeater() {
        return heater;
    }

    public Pump getPump() {
        return pump;
    }
}
