package br.com.fiap.gitdash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests -> 
                authorizeRequests
                    .anyRequest().authenticated() // Protege todas as rotas
            )
            .oauth2Login(oauth2 -> 
                oauth2
                    .loginPage("/oauth2/authorization/github") // Customiza a página de login
                    .defaultSuccessUrl("/") // Redireciona após sucesso no login
            )
            .logout(logout -> 
                logout
                    .logoutSuccessUrl("/") // Logout padrão
            );

        return http.build();
    }
}
