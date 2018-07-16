package com.kcs.sampledagger.dagger_example;

import dagger.Component;

@Component(modules = CoffeeMakerModule.class)
public interface CoffeeComponent {
    //provision method
    Coffeemaker make();

    //member-injection method
    void inject(Coffeemaker coffeemaker);
}
