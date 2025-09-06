package br.com.erudio.security.jwt;

import br.com.erudio.data.dto.security.TokenDTO;
import br.com.erudio.exception.IvalidJWTAuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@ConfigurationProperties()
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long expireLength =3600000;
    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }
    public TokenDTO creatAccessToken(String userName, List<String>roles){
        Date now = new Date();
        Date validadeToken = new Date(now.getTime()+ expireLength);
        String accessToken = getAccessToken(userName,roles,now,validadeToken);
        String refreshToken = getRefreshToken(userName,roles,now);
        return  new TokenDTO(userName,true,now,validadeToken,accessToken,refreshToken);
    }

    public TokenDTO refreshToken(String refreshToken){
        String userName = "";
        List<String>roles = new ArrayList<>();
        String token = "";       
        if(StringUtils.isNotBlank(refreshToken) && refreshToken.startsWith("Bearer ")) {
            token = refreshToken.substring("Bearer ".length());}
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            userName = decodedJWT.getSubject();
            roles = decodedJWT.getClaim("roles").asList(String.class);
            

        return creatAccessToken(userName, roles);
    }

    private String getRefreshToken(String userName, List<String> roles, Date now) {
        Date refreshValidadeToken = new Date(now.getTime()+ expireLength*3);
        return JWT
                .create()
                .withClaim("roles",roles)
                .withIssuedAt(now)
                .withExpiresAt(refreshValidadeToken)
                .withSubject(userName)
                .sign(algorithm);
    }

    private String getAccessToken(String userName, List<String> roles, Date now, Date validadeToken) {
        String issuserUrl  = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT
                .create()
                .withClaim("roles",roles)
                .withIssuedAt(now)
                .withExpiresAt(validadeToken)
                .withSubject(userName)
                .withIssuer(issuserUrl)
                .sign(algorithm);
    }
    public Authentication getAuthentication(String token){
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(alg).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }
    public String resolveToken(HttpServletRequest request) throws IvalidJWTAuthenticationException {
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring("Bearer ".length());

        }
        return null;
    }
    public Boolean validarToken(String token) throws IvalidJWTAuthenticationException {
        DecodedJWT decodedJWT = decodedToken(token);
        try {
            if(decodedJWT.getExpiresAt().before(new Date())){
              return false;
            }else{
                return  true;
            }
        } catch (Exception e) {
            throw new IvalidJWTAuthenticationException("Token expirado");
        }
    }
}
