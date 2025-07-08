package br.com.erudio.service;

import br.com.erudio.controller.PersonController;
import br.com.erudio.data.dto.v1.PersonDTO;
import static br.com.erudio.mapper.ObjectMapper.parseListObject;
import static br.com.erudio.mapper.ObjectMapper.parseObject;

import br.com.erudio.data.dto.v2.PersonDTOV2;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {
    private AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonService.class);
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonMapper personMapper;
    public PersonDTO findById(Long id){

        logger.info("Busca realizado por ID");
        var entity = personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Person Não localizado"));
        var dto = parseObject(entity,PersonDTO.class);
        dto.setDataNascimento(new Date());
        dto.setGenero("Masculino");
        dto.setCpf("2424324324243242");
        addHateoasLinks(dto);
        return dto;
    }
    public List<PersonDTO> findByAll(){
        logger.info("Busca Todos os person");
         var dto = parseListObject(personRepository.findAll(),PersonDTO.class);
        dto.forEach(this::addHateoasLinks);
        return dto;
    }

    public void deletar(Long id) {
        var entity = parseObject(this.findById(id),Person.class);
        logger.info("deletar por ID");
        personRepository.delete(entity);
    }

    public PersonDTO atualiza(PersonDTO person) {
        logger.info("Atualizar person");
        var entity = parseObject(person,Person.class);
        var dto = parseObject(personRepository.save(entity),PersonDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public PersonDTO salvar(PersonDTO person) {
        logger.info("Salva os person");
        var entity = parseObject(person, Person.class);
        var dto = parseObject(personRepository.save(entity),PersonDTO.class);
        addHateoasLinks(dto);
        return dto;

    }
    public PersonDTOV2 salvarV2(PersonDTOV2 person) {
        logger.info("Salva os person versao V2");
        var entity = personMapper.converterDTOToEntity(person);
        return personMapper.converterEntityToDTO(personRepository.save(entity));

    }
    private void addHateoasLinks(PersonDTO dto){
        dto.add( linkTo(methodOn(PersonController.class).findById(dto.getId())).withRel("Buscar Por ID").withType("GET"));
        dto.add( linkTo(methodOn(PersonController.class).findByAll()).withRel("buscarTodos").withType("GET"));
        dto.add( linkTo(methodOn(PersonController.class).salvar(dto)).withRel("salvar").withType("POST"));
        dto.add( linkTo(methodOn(PersonController.class).deletar(dto.getId())).withRel("Deletar").withType("DELETE"));
        dto.add( linkTo(methodOn(PersonController.class).atualiza(dto)).withRel("Atualizar").withType("PUT"));


    }
}
