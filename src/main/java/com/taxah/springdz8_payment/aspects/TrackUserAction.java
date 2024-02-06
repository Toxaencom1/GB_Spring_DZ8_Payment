package com.taxah.springdz8_payment.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TrackUserAction Annotation
 * <p>
 * This annotation is used to mark methods that need to be tracked for user actions.
 * It is applicable only to methods.
 * <p>
 * The above method execution will be logged by TrackUserAspect after its execution.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackUserAction {
}
