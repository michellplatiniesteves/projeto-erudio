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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

    @Autowired
    private PagedResourcesAssembler<LivroDTO> assembler;

    public PagedModel<EntityModel<LivroDTO>> buscarTodos(Pageable pageable) {
        logger.info("Buscando todos os livros");
        var livros = livroRepository.findAll(pageable);
        var colecaolivros = livros.map(livro -> {
            var dto = parseObject(livro,LivroDTO.class);
            addHateoasLinks(dto);
            return dto;
        });
        Link findall= WebMvcLinkBuilder
                      .linkTo(WebMvcLinkBuilder
                              .methodOn(LivroController.class)
                              .buscarTodos(pageable.getPageNumber()
                                           ,pageable.getPageSize()
                                           ,String.valueOf(pageable.getSort())))
                              .withSelfRel();
             return assembler.toModel(colecaolivros,findall);
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
        var entity = parseObject(livro,Livro.class);
        entity = livroRepository.save(entity);
        var dto = parseObject(entity,LivroDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public LivroDTO atualizarLivro(LivroDTO livro) {
        logger.info("Atualizando os livros");
        var entity = parseObject(livro,Livro.class);
        var dto = parseObject(livroRepository.save(entity),LivroDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void deletar(Long id) {
        logger.info("Deletando os livros");
        livroRepository.deleteById(id);

    }
    public void addHateoasLinks(LivroDTO dto){
        dto.add( linkTo(methodOn(LivroController.class).buscarTodos(1,12,"asc")).withRel("buscarTodos").withType("GET"));
        dto.add( linkTo(methodOn(LivroController.class).salvarLivro(dto)).withRel("salvar").withType("POST"));
        dto.add( linkTo(methodOn(LivroController.class).deletar(dto.getId())).withRel("Deletar").withType("DELETE"));
        dto.add( linkTo(methodOn(LivroController.class).atualizarLivro(dto)).withRel("Atualizar").withType("PUT"));
    }
}
