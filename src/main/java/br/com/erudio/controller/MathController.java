package br.com.erudio.controller;

import br.com.erudio.converter.NumberConverter;
import br.com.erudio.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {

    private static NumberConverter numberConverter;
    @GetMapping("/sum/{numero1}/{numero2}")
    public Double sum(@PathVariable("numero1")String numero1,@PathVariable("numero2")String numero2){
        if(!numberConverter.isNumero(numero1) || !numberConverter.isNumero(numero2))throw  new UnsupportedMathOperationException("Por favor digite um numero");
        return numberConverter.converterTodoble(numero1)+numberConverter.converterTodoble(numero2);
    }
    @GetMapping("/subtracao/{numero1}/{numero2}")
    public Double subtracao(@PathVariable("numero1")String numero1,@PathVariable("numero2")String numero2){
        if(!numberConverter.isNumero(numero1) || !numberConverter.isNumero(numero2))throw  new UnsupportedMathOperationException("Por favor digite um numero");
        return numberConverter.converterTodoble(numero1)-numberConverter.converterTodoble(numero2);
    }
    @GetMapping("/multiplicacao/{numero1}/{numero2}")
    public Double multiplicacao(@PathVariable("numero1")String numero1,@PathVariable("numero2")String numero2){
        if(!numberConverter.isNumero(numero1) || !numberConverter.isNumero(numero2))throw  new UnsupportedMathOperationException("Por favor digite um numero");
        return numberConverter.converterTodoble(numero1)*numberConverter.converterTodoble(numero2);
    }

}
