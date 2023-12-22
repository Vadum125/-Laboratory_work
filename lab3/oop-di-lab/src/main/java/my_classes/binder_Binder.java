package my_classes;

import org.fpm.di.Binder;
import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class binder_Binder implements Binder {

    private final Map<Class<?>, Class<?>> MapClasses;
    private final Map<Class<?>, Object> MapInstances;

    public binder_Binder(Map<Class<?>, Class<?>> MapClasses, Map<Class<?>, Object> MapInstances) {
        this.MapClasses = MapClasses;
        this.MapInstances = MapInstances;
    }

    @Override
    public <T> void bind(Class<T> clazz) {
        if (clazz==null){
            throw new bind_Exception("class=null bind не виконається");
        }else {
            bind(clazz, clazz);
        }
    }

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        if (MapClasses.containsKey(clazz) || MapInstances.containsKey(clazz)) {
            throw new bind_Exception("Повторний bind заборонений");
        }

        int implMod = implementation.getModifiers();
        if (Modifier.isAbstract(implMod) || Modifier.isInterface(implMod)) {
            throw new bind_Exception("Імплементація не може бути абстрактна");
        }

        int AnnotationsInject =findAllInjectConstructors(implementation).size();
        if(AnnotationsInject>1){
            throw new bind_Exception("Імплементація не може мати декілька ін'єкцій конструктора");
        }

        checkForCircularInjection(implementation);
        MapClasses.put(clazz, implementation);
    }

    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        if (MapClasses.containsKey(clazz) || MapInstances.containsKey(clazz)) {
            throw new bind_Exception("Повторний bind заборонений");
        }
        MapInstances.put(clazz, instance);
    }

    private List<Constructor<?>> findAllInjectConstructors(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors()).filter((constructor) ->
                constructor.getAnnotation(Inject.class) != null).toList();
    }

    private void checkForCircularInjection(Class<?> clazz) {
        Queue<Class<?>> CheckedQueue = new ArrayDeque<>();
        List<Class<?>> checked = new ArrayList<>();
        CheckedQueue.add(clazz);
        while (!CheckedQueue.isEmpty()){
            Class<?> Checked = CheckedQueue.poll();
            Optional<Constructor<?>> constructorChecked = findAnyInjectConstructor(Checked);
            if (constructorChecked.isEmpty()) {
                continue;
            }
            Constructor<?> constructor = constructorChecked.get();
            Collections.addAll(CheckedQueue, constructor.getParameterTypes());
            checked.add(Checked);
            if (checked.stream().distinct().count() != checked.size()) {
                throw new ExceptionForCircularInjection("Циклічна ін'єкція");
            }
        }

    }
    private Optional<Constructor<?>> findAnyInjectConstructor(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors()).filter((constructor) ->
                constructor.getAnnotation(Inject.class) != null).findAny();
    }
}

