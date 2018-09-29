package gb.common.reflect;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;


public class AnnotatedClassFinder {
    public Set<Class<?>> findAnnotatedClasses(
            String packageToScan, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packageToScan);

        return reflections.getTypesAnnotatedWith(annotation);
    }


    public Map<String, Class<?>> buildMapOfNameAndClassAnnotated(
            String packageToScan, Class<? extends Annotation> annotation) {
        final Set<Class<?>> classes =
                findAnnotatedClasses(packageToScan, annotation);

        return classes.stream()
                .collect(Collectors.toMap(
                        AnnotatedClassFinder::getSimpleNameOfClass,
                        c -> c));
    }


    private static String getSimpleNameOfClass(Class<?> clazz) {
        final String lengthyName = clazz.toString();
        final String[] splitted = lengthyName.split("\\.");

        return splitted[splitted.length-1];
    }
}
