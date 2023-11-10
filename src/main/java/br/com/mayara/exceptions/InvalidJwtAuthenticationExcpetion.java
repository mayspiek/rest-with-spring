package br.com.mayara.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthenticationExcpetion extends AuthenticationException {

        private static final long serialVersionUID = 1L;

        public InvalidJwtAuthenticationExcpetion(String exception) {
            super(exception);
        }
}
