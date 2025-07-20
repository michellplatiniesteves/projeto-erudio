package br.com.erudio.controller;

import br.com.erudio.controller.Docs.LivroControllerDocs;
import br.com.erudio.data.dto.LivroDTO;
import br.com.erudio.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController implements LivroControllerDocs {

    @Autowired
    private LivroService livroService;

    @GetMapping("/buscarTodos")
    @Override
    public ResponseEntity<PagedModel<EntityModel<LivroDTO>>> buscarTodos(
             @RequestParam(value = "page")Integer page,
             @RequestParam(value = "size")Integer size,
             @RequestParam(value = "direction")String direction){
        var sort = "desc".equalsIgnoreCase(direction)? Sort.Direction.DESC:Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page,size,Sort.by(sort,"autor"));
        return ResponseEntity.ok(livroService.buscarTodos(pageable));
    }
    @Override
    @GetMapping("/buscarId/{id}")
    public LivroDTO buscarId(@PathVariable Long id) {
        return livroService.buscarId(id);
    }
    @Override
    @PostMapping("/")
    public LivroDTO salvarLivro(@RequestBody LivroDTO livro)  {
        return livroService.salvarLivro(livro);
    }
    @Override
    @PutMapping("/")
    public LivroDTO atualizarLivro(@RequestBody LivroDTO livro)  {
        return livroService.atualizarLivro(livro);
    }
    @Override
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
