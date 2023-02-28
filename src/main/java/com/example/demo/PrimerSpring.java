package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PrimerSpring {

    @RequestMapping("/test")
    public String test() {
        return "Baruj Ata Adonai, Elohenu Melech HaOlam";
    } // end test

} // end class PrimerSpring
