package com.kcs.sampledagger.dagger_example;


public class MainDraggerExample {
    public static void main(String[] args) {
        {
            //provision method
            DaggerCoffeeComponent.create().make().brew();

            //member-injection method
            Coffeemaker coffeemaker = new Coffeemaker();
            DaggerCoffeeComponent.create().inject(coffeemaker);
            coffeemaker.brew();
        }
    }
}
