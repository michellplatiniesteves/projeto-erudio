package br.com.erudio.controller;

import br.com.erudio.controller.Docs.FileControllerDocs;
import br.com.erudio.data.dto.UploadFileResponseDTO;
import br.com.erudio.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/file/v1")
public class FileController implements FileControllerDocs {
    private static  final Logger logger = LoggerFactory.getLogger(FileController.class) ;
    @Autowired
    private FileStorageService storageService;
    @Override
    @PostMapping("/uploadFile")
    public UploadFileResponseDTO uploadFile(@RequestParam("file")  MultipartFile file) {
        var fileName = storageService.storeFile(file);
        var fileDownloadUri = ServletUriComponentsBuilder
                              .fromCurrentRequestUri()
                              .path("api/file/v1/downloadFile/")
                              .path(fileName)
                .toUriString();
        return new UploadFileResponseDTO(fileName,fileDownloadUri,file.getContentType(),file.getSize());
    }

    @Override
    @PostMapping("/uploadMultipartFiles")
    public List<UploadFileResponseDTO> uploadMultipartFiles(MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file->uploadFile(file))
                .collect(Collectors.toList());
    }

    @Override
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource =  storageService.loadFileAsResource(fileName);
        String contenType = null;
        try {
            contenType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            logger.error("Não foi possivel obter o tipo do arquivo");
        }
        if (contenType == null){
        contenType = "application/octet-stream";
        }
        logger.info("Download do arquivo com sucesso");
        return ResponseEntity.ok()
                             .contentType(MediaType.parseMediaType(contenType))
                             .header(HttpHeaders.CONTENT_DISPOSITION,"attachment=\""+ resource.getFilename()+ "\"")
                             .body(resource);
    }
}
