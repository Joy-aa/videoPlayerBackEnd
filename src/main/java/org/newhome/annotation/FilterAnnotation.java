package org.newhome.annotation;
import org.newhome.config.FilterType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface FilterAnnotation {
    String url();
    FilterType type() default FilterType.anno;
}

