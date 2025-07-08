package br.com.erudio.data.dto.v1;

import br.com.erudio.serializer.GeneroSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"id","email","Nome","Sobre_nome","endereco","data_nascimento","genero","cpf"})
public class PersonDTO extends RepresentationModel<PersonDTO>implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    @JsonProperty("Nome")
    private String nome;
    @JsonProperty("Sobre_nome")
    private String sobreNome;
    private String email;
    private String endereco;
    @JsonProperty("data_nascimento")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;
    @JsonSerialize(using = GeneroSerializer.class)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String genero;

    private String cpf;
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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(id, personDTO.id) && Objects.equals(nome, personDTO.nome) && Objects.equals(sobreNome, personDTO.sobreNome) && Objects.equals(email, personDTO.email) && Objects.equals(endereco, personDTO.endereco) && Objects.equals(dataNascimento, personDTO.dataNascimento) && Objects.equals(genero, personDTO.genero) && Objects.equals(cpf, personDTO.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sobreNome, email, endereco, dataNascimento, genero, cpf);
    }
}
