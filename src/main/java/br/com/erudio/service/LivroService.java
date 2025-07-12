package br.com.erudio.service;

import br.com.erudio.controller.LivroController;
import br.com.erudio.controller.PersonController;
import br.com.erudio.data.dto.LivroDTO;
import br.com.erudio.mapper.ObjectMapper;
import br.com.erudio.model.Livro;
import br.com.erudio.repository.LivroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static br.com.erudio.mapper.ObjectMapper.parseListObject;
import static br.com.erudio.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class LivroService {
    private Logger logger = LoggerFactory.getLogger(LivroService.class);

    @Autowired
    private LivroRepository livroRepository;

    public List<LivroDTO> buscarTodos() {
        logger.info("Buscando todos os livros");
        var dto = parseListObject(livroRepository.findAll(),LivroDTO.class);
        dto.forEach(this::addHateoasLinks);
        return dto;
    }

    public LivroDTO buscarId(Long id) {
        logger.info("Buscando os livros pelo id");
        var entity = livroRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Livro não encontrado"));
        var dto = parseObject(entity,LivroDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public LivroDTO salvarLivro(LivroDTO livro) {
        logger.info("Salvando os livros");
        var dto = parseObject(livroRepository.save(parseObject(LivroDTO.class,Livro.class)),LivroDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public LivroDTO atualizarLivro(LivroDTO livro) {
        logger.info("Atualizando os livros");
        var entity = parseObject(LivroDTO.class,Livro.class);
        var dto = parseObject(livroRepository.save(entity),LivroDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void deletar(Long id) {
        logger.info("Deletando os livros");
        livroRepository.deleteById(id);

    }
    public void addHateoasLinks(LivroDTO dto){
        dto.add( linkTo(methodOn(LivroController.class).buscarTodos()).withRel("buscarTodos").withType("GET"));
        dto.add( linkTo(methodOn(LivroController.class).salvarLivro(dto)).withRel("salvar").withType("POST"));
        dto.add( linkTo(methodOn(LivroController.class).deletar(dto.getId())).withRel("Deletar").withType("DELETE"));
        dto.add( linkTo(methodOn(LivroController.class).atualizarLivro(dto)).withRel("Atualizar").withType("PUT"));
    }
}
