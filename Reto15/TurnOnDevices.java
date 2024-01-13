package Reto15;

import java.util.*;

public class TurnOnDevices{
    public static void main(String[] args) {
        turnOnDevice(new Lamp());
        turnOnDevice(new Computer());
        turnOnDevice(new CoffeeMakerAdapter());
    }
    private static void turnOnDevice(Connectable device) {
        device.turnOn();
        System.out.println(device.isOn());
    }
}


class Connectable{
    private boolean status;
    public void turnOn(){
        status = true;
    };
    public void turnOff(){
        status = false;
    };
    public boolean isOn(){
        return status;
    };
}

class Lamp extends Connectable{}
class Computer extends Connectable{}



class CoffeeMakerAdapter extends Connectable{
    CoffeeMaker adaptee = new CoffeeMaker();

    public void turnOn(){
        adaptee.on();
    };
    public void turnOff(){
        adaptee.off();
    };
    public boolean isOn(){
        return !adaptee.isOff();
    };
    
}

class CoffeeMaker{
    private boolean statusDevice;
    public void on(){
        statusDevice = true;
    };
    public void off(){
        statusDevice = false;
    };
    public boolean isOff(){
        return !statusDevice;
    }
}