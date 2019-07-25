package pl.jcommerce.joannajaromin.studentbook.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.jcommerce.joannajaromin.studentbook.entity.User;

import java.util.Arrays;
import java.util.Collection;

class MyUserDetails implements UserDetails {

    private UserService userService;
    private final String username;

    User user;

    public MyUserDetails(UserService userService, String username) {
        this.userService = userService;
        this.username = username;
        user = userService.userRepository.findByUsername(username);
    }

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
        return username;
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
