/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.duong.training.configration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.duong.training.security.AuthEntryPointJwt;
import com.duong.training.security.JwtRequestFilter;
import com.duong.training.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserDetailsServiceImpl customUserDetailsService;
	
	@Autowired
	public JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private AuthEntryPointJwt authEntryPointJwt;
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors()
		.and()
		.csrf().disable().authorizeRequests()
		.antMatchers("users/username/**", "/categories/count").permitAll()
		.antMatchers(HttpMethod.POST, "/generate_token", "/user", "/user/generate-verify-token",
				"/user/generate-pass-reset-token", "/user/active", "/user/changePassword").permitAll()
		.antMatchers(HttpMethod.GET,"/order/**", "/users/**" ,"/user/**", "/products/**", "/categories/**", "/role/**", "/promotions/**", "/banks/**").permitAll()
		.antMatchers(HttpMethod.POST, "/products/**", "/categories/**", "/promotions/**", "/banks/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT, "/products/**", "/categories/**", "/promotions/**", "/banks/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/products/**", "/categories/**", "/promotions/**", "/banks/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT, "/user/update", "/banks/**").hasAnyRole("USER","ADMIN")
		.antMatchers(HttpMethod.POST, "/order/**").hasAnyRole("USER","ADMIN")
		.anyRequest().authenticated()
		.and().exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}
