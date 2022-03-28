package com.dsam.assignment01.configurations;

import com.dsam.assignment01.constants.SessionConstant;
import com.dsam.assignment01.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final UserService userService;

	@Autowired
	public SecurityConfiguration(UserService userService) {
		this.userService = userService;
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(getPasswordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);

		auth.authenticationProvider(authProvider());
		userService.createDefaultUser();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().mvcMatchers("/static/**").permitAll();
		http.authorizeRequests().antMatchers("/", "/**", "/h2-console/**").permitAll()
			.and().formLogin().permitAll()
			.and().csrf().ignoringAntMatchers("/h2-console/**", "/cart/**", "/orders/**")
			.and().headers().frameOptions().sameOrigin()
			.and().logout().permitAll().invalidateHttpSession(true).deleteCookies(SessionConstant.SESSION).logoutSuccessUrl("/");
	}
}