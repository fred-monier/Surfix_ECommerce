package br.pe.recife.surfix.ecommerce.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

/*
 * Esta "annotation" pode ser utilizada como uma opção de
 * não se passar antes por alguma implementação do ContainerRequestFilter.
 * Para tal, faz-se necessário incluí-la na classe que a implementa e
 * no método do controlador que se deseja acesso direto.
 * */

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Secured {
}
