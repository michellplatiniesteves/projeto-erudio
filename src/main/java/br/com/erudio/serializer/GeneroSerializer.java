package br.com.erudio.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GeneroSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String genero, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String generoFormatado = "Mulher".equals(genero)? "M" : "H";
        jsonGenerator.writeString(generoFormatado);
    }
}
