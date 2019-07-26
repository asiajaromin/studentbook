package pl.jcommerce.joannajaromin.studentbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.jcommerce.joannajaromin.studentbook.entity.User;
import pl.jcommerce.joannajaromin.studentbook.repository.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return Optional.ofNullable(user)
                .map(MyUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("Brak użytkownika o nazwie: " + username));
    }

    @RequiredArgsConstructor
    private static class MyUserDetails implements UserDetails {

        private final User user;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            String authority = user.getAuthority();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
            Collection<GrantedAuthority> authorities = Arrays.asList(grantedAuthority);
            return authorities;
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}