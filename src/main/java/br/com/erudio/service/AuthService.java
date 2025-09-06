package br.com.erudio.service;

import br.com.erudio.data.dto.security.AccountCredentialDTO;
import br.com.erudio.data.dto.security.TokenDTO;
import br.com.erudio.data.dto.v1.PersonDTO;
import br.com.erudio.model.Person;
import br.com.erudio.model.User;
import br.com.erudio.repository.UserRepository;
import br.com.erudio.security.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static br.com.erudio.mapper.ObjectMapper.parseObject;

@Service
public class AuthService {
    private Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<TokenDTO> signIn(AccountCredentialDTO credentialDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentialDTO.getUserName(),
                        credentialDTO.getPassword()
                )
        );
        var user = userRepository.findByUserName(credentialDTO.getUserName());
        if(user == null){
            throw  new UsernameNotFoundException("Usuario nao localizado");
        }
        var token = jwtTokenProvider.creatAccessToken(credentialDTO.getUserName(),user.getRoles());
        return ResponseEntity.ok(token);
    }
    public ResponseEntity<TokenDTO> refreshToken(String userName,String refreshToken){

        var user = userRepository.findByUserName(userName);
        if(user != null){
            var token = jwtTokenProvider.refreshToken(refreshToken);
            return ResponseEntity.ok(token);
        }else{
            throw  new UsernameNotFoundException("Usuario nao localizado");
        }

    }
    private String passwordEncoder(String password){
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("",8,185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        encoders.put("pbkdf2",pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2",encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
        return passwordEncoder.encode(password);
    }
    public AccountCredentialDTO salvarUser(AccountCredentialDTO user) {
        logger.info("Salvando os User");
        userRepository.findByUserName(user.getUserName());
        if(userRepository.findByUserName(user.getUserName()) != null) throw new UsernameNotFoundException("Usuario já cadastrado");
        var entity = new User();
        entity.setFullName(user.getFullName());
        entity.setUserName(user.getUserName());
        entity.setPassword(passwordEncoder(user.getPassword()));
        entity.setEnabled(true);
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        return parseObject(userRepository.save(entity), AccountCredentialDTO.class);


    }

}
