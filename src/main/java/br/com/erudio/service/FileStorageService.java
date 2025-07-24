package br.com.erudio.service;

import br.com.erudio.config.FileStorageConfig;
import br.com.erudio.controller.FileController;
import br.com.erudio.exception.FileNotFoundException;
import br.com.erudio.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private static  final Logger logger = LoggerFactory.getLogger(FileStorageService.class) ;
    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig){
        Path path = Paths.get(fileStorageConfig.getUpload_dir()).toAbsolutePath().toAbsolutePath().normalize();
        this.fileStorageLocation = path;
            try {
                logger.info("Criando diretorio");
                Files.createDirectories(this.fileStorageLocation);
            } catch (IOException e) {
                logger.info("Não foi possivel criar diretorio");
                throw new FileStorageException("Não foi possivel criar diretorio",e);
            }
    }


    public String storeFile(MultipartFile multipartFile){
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            if(fileName.contains("..")){
                throw new FileStorageException("Não foi possivel salvar no diretorio");
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(multipartFile.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Salvando arquivo no diretorio");
            return fileName;
        } catch (Exception e) {
            throw new FileStorageException("Não foi possivel salvar no diretorio",e);
        }

    }
    public Resource loadFileAsResource(String fileName){
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()){
                return resource;
            }else {
                throw new FileNotFoundException("Arquivo nao encontrado");
            }
        } catch (Exception e) {
            throw new FileNotFoundException("Arquivo nao encontrado",e);
        }
    }
}
