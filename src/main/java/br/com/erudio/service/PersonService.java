package br.com.erudio.service;

import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {
    private AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonService.class);
    @Autowired
    private PersonRepository personRepository;
    public Person findById(Long id){
        logger.info("Busca realizado por ID");
     return personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Person Não localizado"));
    }
    public List<Person> findByAll(){
        logger.info("Busca Todos os person");
        return personRepository.findAll();
    }

    public void deletar(Long id) {
        var result = this.findById(id);
        logger.info("deletar por ID");
        personRepository.delete(result);
    }

    public Person atualiza(Person person) {
        logger.info("Atualizar person");
        return personRepository.saveAndFlush(person);
    }

    public Person salvar(Person person) {
        logger.info("Salva os person");
        this.findById(person.getId());
        return personRepository.save(person);
    }
}
