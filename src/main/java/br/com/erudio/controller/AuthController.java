package br.com.erudio.controller;

import br.com.erudio.data.dto.security.AccountCredentialDTO;
import br.com.erudio.data.dto.security.TokenDTO;
import br.com.erudio.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO>signIn(@RequestBody AccountCredentialDTO credential){
        if (validarCredential(credential))ResponseEntity.status(HttpStatus.FORBIDDEN).body("Credenciais Invalidas");
        var token = authService.signIn(credential).getBody();
        if(token == null) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Credenciais Invalidas");
        return ResponseEntity.ok(token);
    }
    @PutMapping("/refresh/{userName}")
    public ResponseEntity<TokenDTO>refreshToken(@PathVariable("userName")String userName,
                                                @RequestHeader("Authorization")String refreshToken){
        if (validarRefreshToken(userName, refreshToken))ResponseEntity.status(HttpStatus.NOT_FOUND).body("Credenciais Invalidas");
        var token = authService.refreshToken(userName,refreshToken).getBody();
        if(token == null) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Credenciais Invalidas");
        return ResponseEntity.ok(token);
    }
    @PostMapping("/createUser")
    public AccountCredentialDTO createUser(@RequestBody AccountCredentialDTO credential){
        return authService.salvarUser(credential);
    }
    private static boolean validarRefreshToken(String userName, String refreshToken) {
        return StringUtils.isBlank(userName) || StringUtils.isBlank(refreshToken);
    }

    private Boolean validarCredential(AccountCredentialDTO credential){
        return credential == null || StringUtils.isBlank(credential.getPassword())|| StringUtils.isBlank(credential.getUserName());
    }
}
