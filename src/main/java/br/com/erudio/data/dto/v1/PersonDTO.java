package br.com.erudio.data.dto.v1;

import br.com.erudio.model.Livro;
import br.com.erudio.serializer.GeneroSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({"id","email","Nome","Sobre_nome","endereco","data_nascimento","genero","cpf","profileUrl","photoUrl","livros"})
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

    private Boolean ativo;

    private String cpf;

    private String profileUrl;
    private String photoUrl;
    @JsonIgnore
    private List<Livro> livros;

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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PersonDTO dto = (PersonDTO) o;
        return Objects.equals(id, dto.id) && Objects.equals(nome, dto.nome) && Objects.equals(sobreNome, dto.sobreNome) && Objects.equals(email, dto.email) && Objects.equals(endereco, dto.endereco) && Objects.equals(dataNascimento, dto.dataNascimento) && Objects.equals(genero, dto.genero) && Objects.equals(ativo, dto.ativo) && Objects.equals(cpf, dto.cpf) && Objects.equals(profileUrl, dto.profileUrl) && Objects.equals(photoUrl, dto.photoUrl) && Objects.equals(livros, dto.livros);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, nome, sobreNome, email, endereco, dataNascimento, genero, ativo, cpf, profileUrl, photoUrl, livros);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sobreNome='" + sobreNome + '\'' +
                ", email='" + email + '\'' +
                ", endereco='" + endereco + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", genero='" + genero + '\'' +
                ", ativo=" + ativo +
                ", cpf='" + cpf + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", livros=" + livros +
                '}';
    }
}
