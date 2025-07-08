package br.com.erudio.service;

import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import br.com.erudio.unitestes.mapper.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;
    @InjectMocks
    PersonService personService;
    @Mock
    PersonRepository personRepository;
    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(input);
    }

    @Test
    void findById() {
        Person person =input.mockEntity(1);
        person.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        var resul = personService.findById(1L);
        assertNotNull(resul);
        assertNotNull(resul.getId());
        assertNotNull(resul.getNome());
        assertNotNull(resul.getLinks());

        assertNotNull(resul.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                && link.getHref().endsWith("/person/buscarID/1")
                && link.getType().equals("GET")));

        assertNotNull(resul.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/person/buscarTodos")
                        && link.getType().equals("GET")));
    }

    @Test
    void findByAll() {
    }

    @Test
    void deletar() {
        Person person =input.mockEntity(1);
        person.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        var resul = personService.findById(1L);
        assertNotNull(resul);
        assertNotNull(resul.getId());
        assertNotNull(resul.getNome());
        assertNotNull(resul.getLinks());

        assertNotNull(resul.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/person/buscarID/1")
                        && link.getType().equals("GET")));

        assertNotNull(resul.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/person/buscarTodos")
                        && link.getType().equals("GET")));
    }

    @Test
    void atualiza() {
    }

    @Test
    void salvar() {
    }

    @Test
    void salvarV2() {
    }
}