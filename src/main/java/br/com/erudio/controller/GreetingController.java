package br.com.erudio.controller;

import br.com.erudio.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    private static final String template = "Seja bem vindo %s;";
    private AtomicLong counter = new AtomicLong();

    @GetMapping("")
    public Greeting greeting(@RequestParam(value = "nome", defaultValue = "Thor")String nome){
        return new Greeting(counter.incrementAndGet(),String.format(template,nome));
    }
}
