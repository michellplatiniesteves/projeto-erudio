package br.com.erudio.service;

import br.com.erudio.config.EmailConfig;
import br.com.erudio.data.dto.EmailDTO;
import br.com.erudio.mail.EamilSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.AddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class EmailService {
    @Autowired
    private EamilSender sender;
    @Autowired
    private EmailConfig config;

    public void senderEmail(EmailDTO dto) {
            sender.to(dto.getTo()).setSubject(dto.getSubject()).setBody(dto.getBody()).sender(config);

    }
    public void senderEmailAnexo(String emailJson, MultipartFile multipartFile) {
        File temp=null;
        try {
            EmailDTO dto = new ObjectMapper().readValue(emailJson,EmailDTO.class);
             temp = File.createTempFile("anexo",multipartFile.getOriginalFilename()) ;
            multipartFile.transferTo(temp);
            sender
                     .to(dto.getTo())
                     .setSubject(dto.getSubject())
                    .setBody(dto.getBody())
                    .setFile(temp.getAbsolutePath())
                    .sender(config);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if ((temp != null)&& temp.exists())temp.delete();
        }

    }
}
