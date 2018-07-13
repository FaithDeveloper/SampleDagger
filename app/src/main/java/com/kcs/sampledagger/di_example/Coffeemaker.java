package com.kcs.sampledagger.di_example;

public class Coffeemaker {
    private final Heater heater;
    private final Pump pump;

    public Coffeemaker(Heater heater, Pump pump){
        this.heater = heater;
        this.pump = pump;
    }

    public void brew(){
        heater.on();
        pump.pump();
        System.out.println(" [_]P coffee! [_]P ");
        heater.off();
    }
}
