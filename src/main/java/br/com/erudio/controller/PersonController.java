package br.com.erudio.controller;

import br.com.erudio.controller.Docs.PersonControllerDocs;
import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.data.dto.v2.PersonDTOV2;
import br.com.erudio.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@Tag(name = "Person",description ="End Point de Person")
public class PersonController implements PersonControllerDocs {

    @Autowired
    private PersonService personService;
    @GetMapping(value = "/buscarID/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public PersonDTO findById(@PathVariable(value = "id") Long id){
        return personService.findById(id);
    }
    @GetMapping(value = "/buscarTodos", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findByAll(
            @RequestParam(value = "page",defaultValue = "0")Integer page,
            @RequestParam(value = "size",defaultValue = "0")Integer size,
            @RequestParam(value = "direction",defaultValue = "asc")String direction){
        var sortDirection = "desc".equalsIgnoreCase(direction)? Sort.Direction.DESC:Sort.Direction.ASC;
        var pageable = PageRequest.of(page,size,Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(personService.findByAll(pageable));
    }

    @PostMapping(value = "/")
    @Override
    public PersonDTO salvar(@RequestBody PersonDTO person){
        return personService.salvar(person);
    }
    @PostMapping(value = "/V2")
    @Override
    public PersonDTOV2 salvarV2(@RequestBody PersonDTOV2 person){
        return personService.salvarV2(person);
    }

    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public PersonDTO atualiza(@RequestBody PersonDTO person){
        return personService.atualiza(person);
    }
    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Void>deletar(@PathVariable(value = "id") Long id){
        personService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping (value = "/{id}")
    @Override
    public PersonDTO desablePerson(@PathVariable(value = "id") Long id){
        return personService.desablePerson(id);
    }
}
