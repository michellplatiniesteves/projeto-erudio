package br.com.erudio.data.dto.v2;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class PersonDTOV2 implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private String sobreNome;
    private String email;
    private String endereco;
    private Date dataAniversario;

    public PersonDTOV2() {
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

    public Date getDataAniversario() {
        return dataAniversario;
    }

    public void setDataAniversario(Date dataAniversario) {
        this.dataAniversario = dataAniversario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTOV2 that = (PersonDTOV2) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(sobreNome, that.sobreNome) && Objects.equals(email, that.email) && Objects.equals(endereco, that.endereco) && Objects.equals(dataAniversario, that.dataAniversario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sobreNome, email, endereco, dataAniversario);
    }
}
