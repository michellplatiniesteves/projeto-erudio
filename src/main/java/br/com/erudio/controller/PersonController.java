package br.com.erudio.controller;

import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.data.dto.v2.PersonDTOV2;
import br.com.erudio.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")

public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/buscarID/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO>findById(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok().body(personService.findById(id));
    }
    @GetMapping(value = "/buscarTodos",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonDTO>>findByAll(){
        return ResponseEntity.ok().body(personService.findByAll());
    }

    @PostMapping(value = "/")
    public ResponseEntity<PersonDTO>salvar(@RequestBody PersonDTO person){
        return ResponseEntity.ok().body(personService.salvar(person));
    }
    @PostMapping(value = "/V2")
    public ResponseEntity<PersonDTOV2>salvarV2(@RequestBody PersonDTOV2 person){
        return ResponseEntity.ok().body(personService.salvarV2(person));
    }

    @PutMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDTO>atualiza(@RequestBody PersonDTO person){
        return ResponseEntity.ok().body(personService.atualiza(person));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void>deletar(@PathVariable(value = "id") Long id){
        personService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
