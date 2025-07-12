package br.com.erudio.controller;

import br.com.erudio.controller.Docs.LivroControllerDocs;
import br.com.erudio.data.dto.LivroDTO;
import br.com.erudio.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    public List<LivroDTO> buscarTodos(){
        return livroService.buscarTodos();
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
