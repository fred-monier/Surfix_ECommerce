package br.pe.recife.surfix.ecommerce.security;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class AuthenticationResourceConfig extends ResourceConfig {

    public AuthenticationResourceConfig() {
    	
        packages("br.pe.recife.surfix.ecommerce.security");
        register(LoggingFeature.class);
        register(GsonMessageBodyHandler.class);
 
        //Registrando o filtro de autenticação
        register(AuthenticationFilter.class);
        
        
        //Registrando o JAX-RS application components (compatibilidade com Spring)            
        register(RequestContextFilter.class);        
        
    }
}

