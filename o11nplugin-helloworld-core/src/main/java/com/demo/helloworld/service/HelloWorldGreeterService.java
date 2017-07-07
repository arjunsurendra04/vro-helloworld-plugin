package com.demo.helloworld.service;

import org.springframework.stereotype.Component;

@Component
public class HelloWorldGreeterService {

    public String greet(String name) {
        return "Hello, " + name + "! Welcome to 'HelloWorld' plug-in";
    }
}
