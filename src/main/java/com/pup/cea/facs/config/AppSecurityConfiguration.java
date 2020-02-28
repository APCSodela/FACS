package com.pup.cea.facs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	public AuthenticationProvider authProvider(){
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	
	    provider.setUserDetailsService(userDetailsService);
	    provider.setPasswordEncoder(new BCryptPasswordEncoder());
	    
	    return provider;
	}
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
				 
				  .antMatchers("/home","/home/**").hasAnyRole("ADMIN")
				  .antMatchers("/administrator","/administrator/**").hasRole("ADMIN")
				  .antMatchers("/daily","/daily/**").hasAnyRole("CHECKER", "ADMIN")
				  .antMatchers("/faculty","/faculty/**").hasAnyRole("DEPT", "CHECKER", "ADMIN")
				  .antMatchers("/ticket/view","/ticket/view/**").hasAnyRole("CHECKER", "DEPT", "ADMIN")
				  .antMatchers("/ticket/waive", "/ticket/waive/**").hasAnyRole("CHECKER", "ADMIN")

				/*
				 * .anyRequest().permitAll()
				 */
                
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
				.successForwardUrl("/check-role") 
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logout-success").permitAll();
    }
	
}