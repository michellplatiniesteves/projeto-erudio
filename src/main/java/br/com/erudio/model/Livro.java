package br.com.erudio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Entity
@Table
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "seq_livro", initialValue = 1, allocationSize = 1)
    private Long id;
    @Column
    @NotEmpty(message = "Informe o nome do autor do livro")
    private String autor;
    @Column
    private Date data_lancamento;
    @Column
    @Min(value = 1)
    private double preco;
    @Column
    @NotEmpty(message = "Informe o nome do autor do livro")
    private String titulo;
}
