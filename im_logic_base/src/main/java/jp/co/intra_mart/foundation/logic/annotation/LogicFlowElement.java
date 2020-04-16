package jp.co.intra_mart.foundation.logic.annotation;

import jp.co.intra_mart.foundation.logic.element.category.ElementCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogicFlowElement {

    String id();

    Class<? extends ElementCategory> category();

    int index() default  0;

    String pairId() default "";
}
