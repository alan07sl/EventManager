package com.utn.tacs.eventmanager.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceServerConfiguration.class);
	@Autowired
	TokenStore tokenStore;
	@Autowired
	JwtAccessTokenConverter tokenConverter;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/**").authenticated();
	}


	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		LOG.info("Configuring ResourceServerSecurityConfigurer");
		resources.resourceId("foo").tokenStore(tokenStore);
	}

}