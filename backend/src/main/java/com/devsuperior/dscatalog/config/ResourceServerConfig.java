package com.devsuperior.dscatalog.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private Environment env; 
	
	@Autowired
	private JwtTokenStore tokenStore;
	
	private static final String[] PUBLIC = { "/oauth/token", "/h2-console/**" }; //rotas publicas
	
	private static final String[] OPERATOR_OR_ADMIN = { "/product/**", "/categories/**" }; //libera rotas para oper e admin
	
	private static final String[] ADMIN = { "/users/**" }; //crud de usuarios somente acesso de admin
			
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore); //analisa token que chega pra ver se ta expirado, o secret..
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {//define quem pode acessar o q		
		
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) { //poder acessar o banco h2
			http.headers().frameOptions().disable();
		}
		
		http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll()                                   //public permite todo mundo de ver
		.antMatchers(HttpMethod.GET, OPERATOR_OR_ADMIN).permitAll()       //qdo metodo get permite de todos de ver
		.antMatchers(OPERATOR_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN")   //rotas podem ser acessadas qm tiver alguma das roles
		.antMatchers(ADMIN).hasRole("ADMIN")
		.anyRequest().authenticated();                                    //acessar qualquer outra rora precisa estar logado
	}
	
	
	
}
