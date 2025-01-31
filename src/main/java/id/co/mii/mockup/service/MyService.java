package id.co.mii.mockup.service;

import org.springframework.stereotype.Service;

@Service
public class MyService {
    public String getGreeting(String name) {
        return "Hello, " + name + "!";
    }
}
