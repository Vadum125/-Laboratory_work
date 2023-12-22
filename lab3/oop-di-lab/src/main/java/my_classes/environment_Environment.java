package my_classes;

import org.fpm.di.Binder;
import org.fpm.di.Configuration;
import org.fpm.di.Container;
import org.fpm.di.Environment;

import java.util.HashMap;
import java.util.Map;

public class environment_Environment implements Environment {
    @Override
    public Container configure(Configuration configuration) {
        Map<Class<?>, Class<?>> classesMap = new HashMap<>();
        Map<Class<?>, Object> instancesMap = new HashMap<>();
        Binder binder = new binder_Binder(classesMap, instancesMap);
        Container container = new container_Container(classesMap, instancesMap);
        configuration.configure(binder);
        return container;
    }
}