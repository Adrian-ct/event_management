package com.example.pajproject.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless
@LocalBean // Means that the EJB does not implement any business interface
public class MyFirstEJB {
    public String sayHello() {
        return "Hello, World from the EJB!";
    }
}

