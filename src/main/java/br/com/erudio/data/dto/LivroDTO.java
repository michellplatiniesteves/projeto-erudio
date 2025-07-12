package br.com.erudio.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

public class LivroDTO extends RepresentationModel<LivroDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String autor;
    @JsonProperty(value = "dd/MM/yyyy")
    private Locale data_lancamento;
    private double preco;
    private String titulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Locale getData_lancamento() {
        return data_lancamento;
    }

    public void setData_lancamento(Locale data_lancamento) {
        this.data_lancamento = data_lancamento;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LivroDTO livroDTO = (LivroDTO) o;
        return Double.compare(preco, livroDTO.preco) == 0 && Objects.equals(id, livroDTO.id) && Objects.equals(autor, livroDTO.autor) && Objects.equals(data_lancamento, livroDTO.data_lancamento) && Objects.equals(titulo, livroDTO.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, autor, data_lancamento, preco, titulo);
    }

    @Override
    public String toString() {
        return "LivroDTO{" +
                "id=" + id +
                ", autor='" + autor + '\'' +
                ", data_lancamento=" + data_lancamento +
                ", preco=" + preco +
                ", titulo='" + titulo + '\'' +
                '}';
    }
}
