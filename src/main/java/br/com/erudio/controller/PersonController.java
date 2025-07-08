package br.com.erudio.controller;

import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.data.dto.v2.PersonDTOV2;
import br.com.erudio.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@Tag(name = "Person",description ="end Point de Person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/buscarID/{id}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    public PersonDTO findById(@PathVariable(value = "id") Long id){
        return personService.findById(id);
    }
    @GetMapping(value = "/buscarTodos",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    @Operation(description = "buscar todos as pessoas",tags = "buscar todos as pessoas",
            responses = {
                         @ApiResponse(description ="Success",
                                 responseCode = "200",
                                 content = {@Content(
                                                 mediaType=MediaType.APPLICATION_JSON_VALUE,
                                                 array =@ArraySchema(schema = @Schema(implementation = PersonDTO.class))
                                 )

                                 }),
                         @ApiResponse(description ="No Content" ,responseCode = "204",content = @Content),
                         @ApiResponse(description ="Bad Request" ,responseCode = "400",content = @Content),
                         @ApiResponse(description ="Unauthorized" ,responseCode = "401",content = @Content),
                         @ApiResponse(description ="Not Found" ,responseCode = "404",content = @Content),
                         @ApiResponse(description ="Internal Server Error" ,responseCode = "500",content = @Content)

    })
    public List<PersonDTO> findByAll(){
        return personService.findByAll();
    }

    @PostMapping(value = "/")
    public PersonDTO salvar(@RequestBody PersonDTO person){
        return personService.salvar(person);
    }
    @PostMapping(value = "/V2")
    public PersonDTOV2 salvarV2(@RequestBody PersonDTOV2 person){
        return personService.salvarV2(person);
    }

    @PutMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO atualiza(@RequestBody PersonDTO person){
        return personService.atualiza(person);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void>deletar(@PathVariable(value = "id") Long id){
        personService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
