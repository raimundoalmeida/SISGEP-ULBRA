package com.soulsoftware.sisgep.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public AppUserDetailsService userDetailsService(){
		return new AppUserDetailsService();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
		jsfLoginEntry.setLoginFormUrl("/Login.xhtml");
		jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());
		
		JsfAccessDeniedHandler jsfAccessDeniedHandler = new JsfAccessDeniedHandler();
		jsfAccessDeniedHandler.setLoginPath("/AcessoNegado.xhtml");
		jsfAccessDeniedHandler.setContextRelative(true);
		
		http
			.csrf().disable()
			.headers().frameOptions().sameOrigin()
			.and()
			
		.authorizeRequests()
			.antMatchers("/Login.xhtml", "/Erro.xhtml", "/javax.faces.resource/**").permitAll()
			.antMatchers("/Home.xhtml", "/AcessoNegado.xhtml").authenticated()
			.antMatchers("/nota/**").hasAnyRole("FUNCIONARIOS", "AUXILIARES", "ADMINISTRADORES")
			.antMatchers("/usuarios/**").hasAnyRole("ADMINISTRADORES")
			.antMatchers("/relatorios/**").hasAnyRole("FUNCIONARIOS", "AUXILIARES", "ADMINISTRADORES")
			.and()
			
		 .formLogin()
		 	.loginPage("/Login.xhtml")
		 	.failureUrl("/Login.xhtml?invalid=true")
		 	.and()
		 	
		 .logout()
		 	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		 	.and()
		 	
		 .exceptionHandling()
		 	.accessDeniedPage("/AcesssoNegado.xhtml")
		 	.authenticationEntryPoint(jsfLoginEntry)
		 	.accessDeniedHandler(jsfAccessDeniedHandler);
	}

}
