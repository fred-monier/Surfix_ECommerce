package br.pe.recife.surfix.ecommerce.security;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.pe.recife.surfix.ecommerce.entity.Empresa;
import br.pe.recife.surfix.ecommerce.exception.NegocioException;
import br.pe.recife.surfix.ecommerce.service.EmpresaService;

/**
 * Este filtro verifica as permissões de acesso para um usuário
 * baseado no login e senha informados no request
 * */
@Component
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

     
    @Context
    private ResourceInfo resourceInfo;
     
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    
    //private static final String ID_COMERCIAL_ADQUIRENTE_PROPERTY =  "idComAdq";
    private static final String ID_COMERCIAL_PROPERTY =  "idComercial";
        
    @Autowired
	private EmpresaService empresaService;      
    
	//@Autowired
	//private EmpresaAdquirenteService empresaAdquirenteService;   
      
    @Override
    public void filter(ContainerRequestContext requestContext) {
    
        Method method = resourceInfo.getResourceMethod();
        
        //Access allowed for all
        if (!method.isAnnotationPresent(PermitAll.class)) {
        
            //Access denied for all
            if (method.isAnnotationPresent(DenyAll.class)) {
                        	
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                        .entity("Acesso bloqueado para qualquer usuário").build());
                return;
            }
              
            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
              
            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
               
            /*
            //idComercial ou idComAdq
            final List<String> idComercial = headers.get(ID_COMERCIAL_PROPERTY);
            final List<String> idComAdq = headers.get(ID_COMERCIAL_ADQUIRENTE_PROPERTY);
                        
            final String idC;           
            final String idCA;
            if (idComercial == null || idComercial.isEmpty()) { 
            	idC = "";
            } else {
            	idC = idComercial.get(0);
            }
            if (idComAdq == null || idComAdq.isEmpty()) { 
            	idCA = "";
            } else {
            	idCA = idComAdq.get(0);
            }            
            */
            //**************
              
            //If no authorization information present; block access
            if (authorization == null || authorization.isEmpty()) {                            
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).
                		entity("Accesso não Autorizado").build());
                                                         
                return;
            }
              
            //Get encoded username and password
            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
              
            //Decode username and password
            String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));;
  
            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();              
              
            //Verify user access
            if (method.isAnnotationPresent(RolesAllowed.class)) {
            
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                  
                try {
	                //Is user valid?
	                if (!isUserAllowed(username, password, rolesSet, requestContext)) {                
	                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
	                            .entity("Acesso não autorizado").build());
	                    return;
	                }
                } catch (NegocioException e) {
                	requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Erro manipulando credenciais de acesso").build());
                } catch (Exception e) {
                	requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Erro acessando BD").build());
                }
            }
        }
    }
    
    private boolean isUserAllowed(final String username, final String password, 
    		final Set<String> rolesSet, ContainerRequestContext requestContext) 
    				throws NegocioException {
    
        boolean isAllowed = false;
          
        //Step 1. Fetch password from database and match with password in argument
        //If both match then get the defined role for user from database and continue; 
        //else return isAllowed [false]
        //Access the database and do this part yourself
        //String userRole = userMgr.getUserRole(username);
        
        Empresa empresa = null;
        
        try {
        	        	
        	empresa = empresaService.consultarPorUsuarioSenha(username, password);
        	        	        	        	
        } catch (Exception e) {
        	throw new NegocioException("Usuário/Senha não encontrado");
        }
                        
        if (empresa != null) {  
        	
            String userRole = "ADMIN";
             
            //Step 2. Verify user role
            if(rolesSet.contains(userRole)) {     
            	
            	requestContext.getHeaders().add(ID_COMERCIAL_PROPERTY, empresa.getId() + "");	            	
                isAllowed = true;
            }	          
        }
        
        return isAllowed;
    }
}
