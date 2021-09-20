package nc.backend.security.jwt;

import nc.backend.common.Role;
import nc.backend.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory (){}

    public static JwtUser create(User user){
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new Role(user.getAdmin()));

        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getSurname(),
                user.getPassword(),
                user.getEmail(),
                true,
                createGrantedAuthorities(roles)
        );
    }

    private static List<GrantedAuthority> createGrantedAuthorities(List<Role> roles){
        return roles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
