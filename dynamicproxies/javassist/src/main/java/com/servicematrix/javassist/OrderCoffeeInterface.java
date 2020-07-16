package com.servicematrix.javassist;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface OrderCoffeeInterface {
    public void orderCoffeeService() throws IOException, TimeoutException;
}
