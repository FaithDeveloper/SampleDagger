package com.kcs.sampledagger.di_example;

public class MainDIExample {
    public static void main(String[] args) {

        /**
        * ------------------------------------
        *  DI 적용 안한 상태에서 실행
        * ------------------------------------
        */
        /*
        Heater heater = new A_Heater();
        Pump pump = new A_Pump(heater);
        // Coffeemaker는 A_Heater와 B_Pump 구현체를 알아야 커피를 내릴 수 있다.
        // 정작 마시고 싶은 사람은 그것을 몰라도 되는데 의미가 없는 데이터가 된다.
        Coffeemaker coffeemaker = new Coffeemaker(heater, pump);
        coffeemaker.brew();
        */

        /**
        * ------------------------------------
        *  DI 적용 상태에서 실행
        * ------------------------------------
        */
         // Coffeemaker coffeemaker = new Coffeemaker(Injection.provideHeater(), Injection.providePump());
         // coffeemaker.brew();
        // Injection 클래스에서는 이미 CoffeeMaker 도 제공하고 있다.
        // 만약 A_Heater 말고 B_Heater 로 변경해야할 경우 Injection 설정 값만 변경하면 된다.
        Injection.provideCoffeeMager().brew();

    }
}
