package oit.is.z0407.kaizi.janken.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class Lec03AuthConfiguration extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.inMemoryAuthentication().withUser("player1").password(passwordEncoder().encode("J4nken")).roles("USER");

    auth.inMemoryAuthentication().withUser("player2").password(passwordEncoder().encode("janK3n")).roles("USER");

    auth.inMemoryAuthentication().withUser("ほんだ").password(passwordEncoder().encode("p@ss")).roles("USER");
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin();

    http.authorizeRequests().antMatchers("/lec02").authenticated();

    http.logout().logoutSuccessUrl("/");

    // h2-consoleにアクセスするための設定
    http.csrf().disable();
    http.headers().frameOptions().disable();
  }
}
