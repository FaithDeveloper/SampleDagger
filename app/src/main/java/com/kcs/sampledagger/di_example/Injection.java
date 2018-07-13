package com.kcs.sampledagger.di_example;

/*
 * DI 는 CoffeMaker 사용자가 의존성을 모르는 상태(A_Heater, A_Pump 의 존재를 모르는 상태)에서도 커피를 내릴 수 있도록 해줍니다.
 * Injection 클래스는 DI 역할을 합니다.
 */
public class Injection {
    public static Heater provideHeater(){
        return new A_Heater();
    }

    public static Pump providePump(){
        return new A_Pump(provideHeater());
    }

    public static Coffeemaker provideCoffeeMager(){
        return new Coffeemaker(provideHeater(), providePump());
    }
}
