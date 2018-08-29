package com.utn.tacs.eventmanager.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Se agrega configuracion de Cross Origin Resource Sharing,
 * mecanismo por el cual se le permite a JavaScript hacer
 * pedidos AJAX a otro dominio, difrente del dominio desde el que se origina.
 *
 * Por default, estos pedidos son prohibidos en los exploradores y producen
 * same origin security policy errors.
 *
 * Usar el filtro Java CORS, permite a la pagina web hacer pedidos desde otros dominios.
 *
 * Permite tambien probar la documentacion de swagger en swagger-ui desde localhost
 *
 */
@Configuration
public class CorsConfig {

	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setMaxAge(0L);
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
