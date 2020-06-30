package com.servicematrix.personservice;


public class CoffeeMachineMessage {
    private String coffeeMachineId;
    public String status;
    public Double distance;

    public String getCoffeeMachineId() {
        return coffeeMachineId;
    }

    public void setCoffeeMachineId(String coffeeMachineId) {
        this.coffeeMachineId = coffeeMachineId;
    }
}
