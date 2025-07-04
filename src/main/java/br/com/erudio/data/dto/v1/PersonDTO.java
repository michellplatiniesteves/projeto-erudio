package br.com.erudio.data.dto.v1;

import java.io.Serializable;
import java.util.Objects;


public class PersonDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private String sobreNome;
    private String email;
    private String endereco;

    public PersonDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PersonDTO personDTO)) return false;
        return Objects.equals(id, personDTO.id) && Objects.equals(nome, personDTO.nome) && Objects.equals(sobreNome, personDTO.sobreNome) && Objects.equals(email, personDTO.email) && Objects.equals(endereco, personDTO.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sobreNome, email, endereco);
    }
}
