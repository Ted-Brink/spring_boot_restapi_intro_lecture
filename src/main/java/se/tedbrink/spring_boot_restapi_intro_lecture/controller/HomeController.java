package se.tedbrink.spring_boot_restapi_intro_lecture.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String helloWorld() {
        return "<h1> Hello World - Message from homeController! </h1>";
    }

    @GetMapping("/message")
    public ResponseEntity<String> responseString(
            @RequestParam(value = "message", defaultValue = "defaultMessage from HomeController") String message) {
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping
    public ResponseEntity<Void> foo() {
        return  ResponseEntity.notFound().build();
    }
}