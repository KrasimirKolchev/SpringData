package com.productsShop.utils.validationUtil;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidationUtil {

    <T> boolean isValid(T entity);

    <T> Set<ConstraintViolation<T>> getViolationsData(T entity);
}
