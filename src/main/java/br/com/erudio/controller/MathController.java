package br.com.erudio.controller;

import br.com.erudio.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    @GetMapping("/sum/{numero1}/{numero2}")
    public Double sum(@PathVariable("numero1")String numero1,@PathVariable("numero2")String numero2){
        if(!isNumero(numero1) || !isNumero(numero2))throw  new UnsupportedMathOperationException("Por favor digite um numero");
        return converterTodoble(numero1)+converterTodoble(numero2);
    }

    private Double converterTodoble(String numero) {
        if(numero==null || numero.isEmpty()) throw  new UnsupportedMathOperationException("Por favor digite um numero");
        numero =numero.replace(",",".");
        return Double.parseDouble(numero);
    }

    private boolean isNumero(String numero) {
        if(numero==null || numero.isEmpty()) return false;
        numero=numero.replace(",",".");
        return numero.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
