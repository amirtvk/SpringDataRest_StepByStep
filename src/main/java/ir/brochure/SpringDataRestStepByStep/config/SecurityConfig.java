package ir.brochure.SpringDataRestStepByStep.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(final HttpSecurity http) throws Exception {

        http
                .csrf().disable().headers()
                .frameOptions().disable()
                .and()
                .authorizeRequests().antMatchers("/").permitAll();
    }



}
