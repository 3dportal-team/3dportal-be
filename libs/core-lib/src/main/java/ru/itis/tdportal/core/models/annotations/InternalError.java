package ru.itis.tdportal.core.models.annotations;

import org.springframework.http.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InternalError {

    HttpStatus httpStatus() default HttpStatus.INTERNAL_SERVER_ERROR;
}
