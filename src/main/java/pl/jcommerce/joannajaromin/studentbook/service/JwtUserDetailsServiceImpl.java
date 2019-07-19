package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        if ("studentbook".equals(username)){
            return new User("studentbook", "student", new ArrayList<>());
        }
        else {
            throw new UsernameNotFoundException("Nie znalezniono użytkownika o nazwie: " + username);
        }
    }
}
