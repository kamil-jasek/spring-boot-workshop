package pl.alx.workshop.expenses.config;

import java.util.UUID;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/home", true)
				.permitAll()
				.and()
			.logout()
				.deleteCookies("JSESSIONID")
				.permitAll()
				.and()
			.rememberMe()
				.key(UUID.randomUUID().toString())
				.tokenValiditySeconds(3600)
				.rememberMeParameter("remember-me");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/vendor/**", "/dist/**");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("kamil@test.com")
				.password("{noop}test123")
				.roles()
				.and()
			.withUser("test@test.com")
				.password("{noop}test123")
				.roles();
	}
}
