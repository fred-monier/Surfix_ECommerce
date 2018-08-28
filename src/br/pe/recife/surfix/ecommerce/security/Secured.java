package br.pe.recife.surfix.ecommerce.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

/*
 * Esta "annotation" pode ser utilizada como uma op��o de
 * n�o se passar antes por alguma implementa��o do ContainerRequestFilter.
 * Para tal, faz-se necess�rio inclu�-la na classe que a implementa e
 * no m�todo do controlador que se deseja acesso direto.
 * */

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Secured {
}
