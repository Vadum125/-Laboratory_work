package my_classes;

import org.fpm.di.Container;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class  container_Container implements Container {

    private final Map<Class<?>, Class<?>> MapClasses;
    private final Map<Class<?>, Object> MapInstances;

    public container_Container(Map<Class<?>, Class<?>> MapClasses, Map<Class<?>, Object> MapInstances) {
        this.MapClasses = MapClasses;
        this.MapInstances = MapInstances;
    }

    public <T> T getComponent(Class<T> clazz) {

        if (MapInstances.containsKey(clazz)) {
            return (T) MapInstances.get(clazz);
        }

        if (MapClasses.containsKey(clazz)) {
            Class<T> targetClass = (Class<T>) MapClasses.get(clazz);
            if (!targetClass.equals(clazz) && (MapClasses.containsKey(targetClass) || MapInstances.containsKey(targetClass))) {
                return getComponent(targetClass);
            }
            return getInstance(targetClass);
        } else {
            List<Class<? extends T>> implementations = getAllImplementations(clazz);
            if (implementations.isEmpty()) {
                throw new UnknownClassException("Невідомий клас");
            }

            if (implementations.size() > 1) {
                throw new containerException("Забагато імплементацій");
            }
            return getComponent(implementations.get(0));
        }
    }

    private <T> T getInstance(Class<T> clazz){
        T instance=null;
        try {
            instance = getInstanceInjectConstructor(clazz);
            if (instance == null) {
                instance = getInstanceEmptyConstructor(clazz);
            }
        } catch (Exception e) {
            throw new containerException("Не вийшло створити екземпляр класу");
        }
        return instance;
    }

    private <T> T getInstanceInjectConstructor(Class<T> clazz) {
        try{
            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                if (constructor.getAnnotation(Inject.class) != null) {
                    Class<?>[] parameters = constructor.getParameterTypes();
                    Object[] arguments = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        arguments[i] = getComponent(parameters[i]);
                    }
                    constructor.setAccessible(true);
                    T instance = (T) constructor.newInstance(arguments);
                    if (clazz.getAnnotation(Singleton.class) != null) {
                        MapInstances.put(clazz, instance);
                    }
                    return instance;
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private <T> T getInstanceEmptyConstructor(Class<T> clazz)  {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            if (clazz.getAnnotation(Singleton.class) != null) {
                MapInstances.put(clazz, instance);
            }
            return instance;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private <T> List<Class<? extends T>> getAllImplementations(Class<T> clazz) {
        List<Class<? extends T>> implementations = new ArrayList<>();
        for (Class<?> key : MapClasses.keySet()) {
            while (!key.equals(Object.class)) {
                if (key.equals(clazz)) {
                    implementations.add((Class<? extends T>) key);
                }
                key = key.getSuperclass();
            }
        }
        return implementations;
    }

}
class containerException extends RuntimeException{
    public containerException(String message){
        super(message);
    }
}