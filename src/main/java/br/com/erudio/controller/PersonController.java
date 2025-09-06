package br.com.erudio.controller;

import br.com.erudio.controller.Docs.PersonControllerDocs;
import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.data.dto.v2.PersonDTOV2;
import br.com.erudio.file.exporter.MediaTypes;
import br.com.erudio.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/person")
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

    @GetMapping(value = "/buscarNome/{nome}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findByNome(
            @PathVariable(value = "nome")String nome,
            @RequestParam(value = "page",defaultValue = "0")Integer page,
            @RequestParam(value = "size",defaultValue = "0")Integer size,
            @RequestParam(value = "direction",defaultValue = "asc")String direction){
        var sortDirection = "desc".equalsIgnoreCase(direction)? Sort.Direction.DESC:Sort.Direction.ASC;
        var pageable = PageRequest.of(page,size,Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(personService.findByNome(nome,pageable));
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

    @Override
    @PostMapping(value = "/adicionarPlanilhas")
    public List<PersonDTO> AdicionarPlanilhas(@RequestParam("file") MultipartFile file) {
        try {
            return personService.AdicionarPlanilhas(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @GetMapping(value = "/exportarArquivo",produces = {
        MediaTypes.APPLICATION_CSV_VALUE,
        MediaTypes.APPLICATION_XLSX_VALUE,
        MediaTypes.APPLICATION_PDF_VALUE
    })
    public ResponseEntity<Resource> exportarArquivo (
        @RequestParam(value = "page",defaultValue = "0")Integer page,
        @RequestParam(value = "size",defaultValue = "0")Integer size,
        @RequestParam(value = "direction",defaultValue = "asc")String direction,
        HttpServletRequest request){
            var sortDirection = "desc".equalsIgnoreCase(direction)? Sort.Direction.DESC:Sort.Direction.ASC;
            var pageable = PageRequest.of(page,size,Sort.by(sortDirection, "nome"));
            var acceptionHeader = request.getHeader(HttpHeaders.ACCEPT);
            Resource file = personService.exportarArquivo(pageable,acceptionHeader);
        Map<String,String> extensaoMap = Map.of(
                MediaTypes.APPLICATION_CSV_VALUE,".csv",
                MediaTypes.APPLICATION_XLSX_VALUE, ".xlsx",
                MediaTypes.APPLICATION_PDF_VALUE,".pdf"
        );
            var fileExtension = extensaoMap.getOrDefault(acceptionHeader,"");
            var contenType = acceptionHeader != null ? acceptionHeader:"application/octet-stream";

            var fileName = "pessoas"+fileExtension;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contenType))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment=\""+ fileName +"\"")
                .body(file);
    }
    @Override
    @GetMapping(value = "/exportarPerson/{id}",produces = {
            MediaTypes.APPLICATION_PDF_VALUE
    })
    public ResponseEntity<Resource> exportarPerson (
            @PathVariable("id")Long id,
            HttpServletRequest request){
        var acceptionHeader = request.getHeader(HttpHeaders.ACCEPT);
        Resource file = personService.exportarPerson(id,acceptionHeader);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(acceptionHeader))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=person.pdf")
                .body(file);
    }
}
