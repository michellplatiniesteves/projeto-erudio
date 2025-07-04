package br.com.erudio.converter;

import br.com.erudio.exception.UnsupportedMathOperationException;
import org.springframework.stereotype.Service;

@Service
public class NumberConverter {
    public Double converterTodoble(String numero) {
        if(numero==null || numero.isEmpty()) throw  new UnsupportedMathOperationException("Por favor digite um numero");
        numero =numero.replace(",",".");
        return Double.parseDouble(numero);
    }

    public boolean isNumero(String numero) {
        if(numero==null || numero.isEmpty()) return false;
        numero=numero.replace(",",".");
        return numero.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
