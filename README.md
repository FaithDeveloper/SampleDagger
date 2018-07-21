# DI 란 무엇인가?

> Dagger는 자바와 안드로이드를 위한 완전히 Static한 컴파일 타임 의존성 주입 프레임 워크입니다. Square에서 작성한 초기 버전의 개정으로 현재 Google에서 관리하고 있습니다.     -  google.gihub.io/dagger

프로그래머 사이에 `DI`의 중요성이 강조되고 있습니다. 그러면서 도입되고 `Dagger`는 필수 라이브러리로 주목받고 있습니다. DI 개념부터 Dagger 사용법 까지 간단히 공유하겠습니다.

# DI와 IoC

Dagger 는 DI을 도와주는 프레임워크 입니다. 그렇다면 DI 무엇일까요? DI는 Dependency Injection(의존성 주입)의 준말입니다. 

> DI(의존성 주입)은 프로그래밍에서 구성요소간의 의존 관계가 소스코드 내부가 아닌 외부의 설정파일 등을 통해 정의되게 하는 디자인 패턴 중의 하나이다.   - 위키백과

조금 더 쉽게 설명하자면 `의존성 주입`이란 **외부에서 의존 객체를 생성하여 넘겨주는 것**을 의미합니다. 예를들어 A Class가 B Class를 의존할 때 B Object를 A가 직접 생성하지 않고 외부에서 생성하여 넘겨주면 의존성을 주입했다고 합니다.

![img](../res/Dagger/dagger_001.png)

왼쪽은 A에서 B를 생성하는 일반적인 의존 형태이고, 오른쪽은 외부에서 의존 객체를 생성하고 주입하는 형태입니다.

DI를 위해서 객체를 생성하고 넘겨주는 일은 DI Framework가 하는 일입니다. 외부에서 객체를 생성하고 넘겨주는 것을 스프링에서는 컨테이너, Dagger에서는 Component와 Module이라고 부릅니다. 

> **IoC(Inversion of Control) - 제어의 역전**
>
> 제어가 거꾸로 가는 개념을 IoC 라고 합니다. 쉽게 말하면 외부 컨테이너가 객체를 생성, 주입 하는 경우 IoC 컨테이너에서 객체를 생성한 후 생성된 객체를 다른 객체에 주입하는 것을 의미합니다. DI 는 IoC 를 구현하는 방법 중 한 가지 방법입니다.

<br/>

## DI는 왜 필요할까?

1. 의존성 파라미터를 생성자에 작성하지 않아도 되므로 Boilerplate code(보일러 플레이트 코드)를 줄일 수 있습니다. Boilerplate code(보일러 플레이트 코드)를 줄이는 것만으로도 유연한 프로그래밍이 가능합니다.

   > **Boilerplate code (보일러 플레이트 코드)**
   >
   > 꼭 필요하면서 간단한 기능에 비해 많은 코드를 필요로 하는 코드를 의미 한다. 예를 들면 setter, getter을 의미한다.

2. Interface에 구현체를 쉽게 교체하면서 상황에 따라 적절한 행동을 정의할 수 있습니다. 따라서 유닛 테스트 시 간결한 구현을 가능하게 도와줍니다.

<Br/>

# 간단한 예제로 알아보는 DI

Dagger 에서도 사용하고 있는 Coffee Maker 예제를 갖고 DI 개념을 알아보겠습니다.

Coffee 를 내리는데 필요한 열을 가하는 Heater와 압력을 가하는 Pump 을 인터페이스로 구현합니다. isHot()은 Heater가 뜨거운지 체크해주는 함수입니다.

```java
public interface Heater {
    void on();
    void off();
    boolean isHot();
}
```

```java
public interface Pump {
    void pump();
}
```

그리고 Heater 와 Pump 를 사용해 커피를 내려주는 Coffee Maker 클래스를 구현해봅시다.

```java
public class CoffeeMaker {
    private final Heater heater;
    private final Pump pump;

    public CoffeeMaker(Heater heater, Pump pump){
        this.heater = heater;
        this.pump = pump;
    }

    public void brew(){
        heater.on();
        pump.pump();
        System.out.println(" ===== coffee... ===== ");
        heater.off();
    }
}
```

A_Heater 와 A_Pump 인터페이스 구현체를 구현하면 다음과 같습니다.

```java
public class A_Heater implements Heater {

    boolean heating;

    public void on() {
        System.out.println("A_Heater! heating... .. . ");
        this.heating = true;
    }

    public void off() { this.heating = false; }

    public boolean isHot() { return heating; }
}
```

```java
public class A_Pump implements Pump {

    private final Heater heater;

    public A_Pump(Heater heater) {
        this.heater = heater;
    }

    public void pump() {
        if (heater.isHot()) {
            System.out.println("A_Pump => pumping ~");
        }
    }
}
```

<br/>

## DI 사용하지 않는 상태

DI를 사용하지 않은 상태에서 메인함수에서 커피를 내려보겠습니다.

```java
public static void main(String[] args) {
	Heater heater = new A_Heater();
	Pump pump = new A_Pump(heater);
	CoffeeMaker coffeeMaker = new CoffeeMaker(heater,pump);
	coffeeMaker.brew();
}
```

커피를 내리기 위해서는 CoffeeMaker 객체 사용자가 A_Heater 라는 인터페이스 구현체와 A_Pump 라는 구현체를 알아야 커피를 내릴수 있습니다. 커피를 마시는 사람은 어떤 heater 와 pump 를 쓰는지 알고 싶어 하지 않습니다. 

<br/>

## DI 을 활용한 상태

DI는 CoffeeMaker 사용자가 의존성을 모르는 상태(어떤 heater 와 pump 를 쓰는지 모르는 상태)에서도 커피를 내릴수 있도록 해줍니다. DI로 CoffeeMaker 사용자 대신에 의존성을 주입해주는 Injection 이라는 클래스를 생성합니다.

```java
public class Injection {
    public static Heater provideHeater(){
        return new A_Heater();
    }

    public static Pump providePump(){
        return new A_Pump(provideHeater());
    }

    public static CoffeeMaker provideCoffeeMaker(){
        return new CoffeeMaker(provideHeater(),providePump());
    }
    
}
```

 

Injection 클래스는 DI`의존성 주입`을 해주는 설정 파일로 생각하시면 이해기 편하실 것입니다. 이 설정 파일인 Injection에서 `인터페이스 구현체`를 `리턴`해주고 있습니다. DI 를 구현한 상태에서 CoffeeMaker 사용자는 어떻게 커피를 내리는지 보겠습니다.

```java
CoffeeMaker coffeeMaker = new CoffeeMaker(Injection.provideHeater(),Injection.providePump());
coffeeMaker.brew();
```



Injection 클래스에서 heater와 pump 를 제공받습니다. Injection을 통해 CoffeeMaker 사용자는 A_Heater를 사용해야하는지 B_Heater를 사용해야하는지 알 필요가 없습니다. 단지 Heater와 Pump 의 종류만 알고 쓰게 됩니다.

더 간단하게는 이렇게 구현 할 수 있습니다.

```java
Injection.provideCoffeeMaker().brew();
```

이 경우 CoffeeMaker 사용자는 heater 나 pump 가 필요한지 모르는 상태에서도 커피를 내릴수 있습니다. 

갑자기 A_Heater 말고 B_Heater 를 사용해야하는 상황이 왔을 때 DI 가 구현된 코드에서 CoffeeMaker관리자는 사용자에게 아무 얘기 없이 Injection 설정만 B_Heater로 바꿔주면 됩니다. 그러나 DI 가 구현되어 있지 않다면 CoffeeMaker 관리자는 사용자에게 커피 내릴때 A_Heater 객체 말고 B_Heater 객체를 사용해야 한다고 요청해야하는 번거로운 일이 생기게됩니다.

이처럼 DI 를 이용하면 의존관계가 외부에 있기에 사용하는데 있어서 유연하게 사용할 수 있게 됩니다.

<br/>

# 정리

친절히 정리해 놓은 사이트에서 공부한 내용을 바탕으로 작성하다보니 많이 겹치는 부분이 있을 수 있습니다. 보다 자세한 설명은 제가 참고한 사이트에 직접 가시면 보다 친절한 설명으로 접근할 수 있을 것으로 예상됩니다. 

DI 를 간단히 말해 외부에서 의존 객체를 생성하여 넘겨주는 것을 의미합니다.따라서 의존 관계가 내부가 아닌 외부의 설정 파일에 의해서 결정되게 됩니다. DI 을 쉽고 간결하게 해주는 프레임워크은 Dagger 라는 오픈소스로 유닛 테스트 및 앱 개발에 활용되고 있습니다.  다양한 분야에서 사용하는 DI 에 대해서 알아봤습니다.

예제 소스코드는 [GitHub](https://github.com/FaithDeveloper/SampleDagger)에서 확인 할 수 있습니다.



# 참고

[DI(Dependency Injection) 와 Dagger2](https://cmcmcmcm.blog/2017/08/02/didependency-injection-%EC%99%80-dagger2-2/)

[DI 기본개념부터 사용법까지, Dagger2 시작하기
