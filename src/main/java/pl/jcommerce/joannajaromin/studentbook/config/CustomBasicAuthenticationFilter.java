package pl.jcommerce.joannajaromin.studentbook.config;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.security.Key;
import java.util.Collection;

public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {


    @Autowired
    public CustomBasicAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void onSuccessfulAuthentication(final javax.servlet.http.HttpServletRequest request,
                                              final javax.servlet.http.HttpServletResponse response,
                                              final Authentication authResult) {
        //Generate Token
        //Save the token for the logged in user
        //send token in the response
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        String username = authResult.getName();
        //Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Key key = KeyProvider.getKey();
        String jws = Jwts.builder()
                .setSubject(username)
                .claim("username",username)
                .claim("authorities",authorities)
                .signWith(key)
                .compact();
        response.setHeader("mySuperToken" , jws);
    }


}