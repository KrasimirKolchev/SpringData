package alararestaurant.util;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidationUtil {

    <E> boolean isValid(E entity);

//    public <T> Set<ConstraintViolation<T>> getViolationsData(T entity);
}
