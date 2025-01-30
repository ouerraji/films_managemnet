/*
package ma.sdglr.cinema.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actors/**").authenticated()      // 🔒 Secure actors
                        .requestMatchers("/categories/**").authenticated()  // 🔒 Secure categories
                        .requestMatchers("/films/admin").authenticated()    // 🔒 Secure films/admin
                        .requestMatchers("/languages/**").authenticated()   // 🔒 Secure languages
                        .anyRequest().permitAll() // Allow public access to everything else
                )
                .formLogin(login -> login
                        .loginPage("/login") // Redirect to this page for login
                        .defaultSuccessUrl("/films/admin", true) // Redirect here after login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF for testing (enable in production)

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("oussama")
                .password("oussama123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}
*/