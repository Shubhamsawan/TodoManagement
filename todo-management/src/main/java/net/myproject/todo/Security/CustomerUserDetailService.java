package net.myproject.todo.Security;

import net.myproject.todo.Entity.User;
import net.myproject.todo.Repository.RoleRepository;
import net.myproject.todo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private RoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // here we can give option what user use name or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail)
                .orElseThrow(()-> new UsernameNotFoundException("user not ecists by username or email"));

        //converting set of roles in to set of authority
        Set<GrantedAuthority> authorities = user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(usernameOrEmail, user.getPassword(), authorities);
    }
}
