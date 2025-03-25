package site.easy.to.build.crm.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import site.easy.to.build.crm.config.JwtTokenProvider;
import site.easy.to.build.crm.controller.dto.LoginRequest;
import site.easy.to.build.crm.controller.dto.LoginResponse;

@RestController
public class AuthServiceController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login_service")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authentifier l'utilisateur
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Récupérer les rôles de l'utilisateur
            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // Générer un token JWT avec les rôles
            String token = jwtTokenProvider.generateToken(loginRequest.getUsername(), roles);

            // Retourner la réponse avec le token
            return ResponseEntity.ok(new LoginResponse(token, null));
        } catch (BadCredentialsException e) {
            // Retourner une erreur en cas d'échec de l'authentification
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null, "Nom d'utilisateur ou mot de passe incorrect"));
        } catch (Exception e) {
            // Retourner une erreur en cas d'erreur interne
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginResponse(null, "Erreur interne du serveur"));
        }
    }
}