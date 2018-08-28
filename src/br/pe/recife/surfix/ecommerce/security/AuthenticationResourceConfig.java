package br.pe.recife.surfix.ecommerce.security;

import org.glassfish.jersey.logging.LoggingFeature;

import org.glassfish.jersey.server.ResourceConfig;

public class AuthenticationResourceConfig extends ResourceConfig {

    public AuthenticationResourceConfig() {
    	
        packages("br.pe.recife.surfix.ecommerce.security");
        register(LoggingFeature.class);
        register(GsonMessageBodyHandler.class);
 
        //Registrando o filtro de autenticação
        register(AuthenticationFilter.class);
    }
}

