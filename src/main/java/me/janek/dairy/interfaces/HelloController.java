package me.janek.dairy.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String healthCheck() {
        return "Dairy api service is running";
    }

}
