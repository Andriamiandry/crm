package site.easy.to.build.crm.config;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CrmUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Recherchez l'utilisateur dans la base de données
        List<User> users = userRepository.findByUsername(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        User user = users.get(0); // Prenez le premier utilisateur trouvé

        // Vérifiez si l'utilisateur est suspendu
        if ("suspended".equals(user.getStatus())) {
            HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
            if (response != null) {
                try {
                    response.sendRedirect("/account-suspended");
                } catch (IOException e) {
                    throw new RuntimeException("Failed to redirect to account-suspended page", e);
                }
            }
            return null; // Retournez null pour éviter de créer un UserDetails invalide
        }

        // Récupérez les rôles de l'utilisateur
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        // Stockez l'ID de l'utilisateur dans la session
        session.setAttribute("loggedInUserId", user.getId());

        // Créez et retournez un objet UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}