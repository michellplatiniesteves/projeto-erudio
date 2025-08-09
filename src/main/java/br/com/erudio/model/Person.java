package br.com.erudio.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "person")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "seq_person", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    @Column
    private String sobreNome;
    @Column
    private String email;
    @Column
    private String endereco;
    @Column
    private String genero;
    @Column
    private Boolean ativo;

    @Column(name = "wikipedia_profile_url",length = 255)
    private String profileUrl;

    @Column(name = "photo_url",length = 255)
    private String photoUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_books",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns= @JoinColumn(name = "livro_id")
    )
    private List<Livro>livros;


}
