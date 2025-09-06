package br.com.erudio.controller;

import br.com.erudio.controller.Docs.EmailControllerDocs;
import br.com.erudio.data.dto.EmailDTO;
import br.com.erudio.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/email")
public class EmailController implements EmailControllerDocs {
    @Autowired
    private EmailService emailService;
    @Override
    @PostMapping("/enviarEmail")
    public ResponseEntity<String> senderEmail(@RequestBody EmailDTO dto) {
        emailService.senderEmail(dto);
        return new ResponseEntity<>("Email enviado com sucesso", HttpStatus.OK);
    }

    @Override
    @PostMapping(value = "/enviarEmailComAnexo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> senderEmailAnexo(@RequestParam("emailJson") String emailJson, @RequestParam("multipartFile") MultipartFile multipartFile) {
        emailService.senderEmailAnexo(emailJson,multipartFile);
        return new ResponseEntity<>("Email enviado com sucesso", HttpStatus.OK);
    }
}
