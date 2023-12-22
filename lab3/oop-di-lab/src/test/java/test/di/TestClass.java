package test.di;
import my_classes.ExceptionForCircularInjection;
import my_classes.UnknownClassException;
import my_classes.bind_Exception;
import my_classes.environment_Environment;
import org.fpm.di.Container;
import org.fpm.di.Environment;
import static org.junit.Assert.assertSame;
import org.junit.Test;
public class TestClass {
    private final Environment environment = new environment_Environment();

    @Test(expected = ExceptionForCircularInjection.class)
    public void CircularInjectTest() {
        environment.configure((binder) -> {
            binder.bind(A.class);
            binder.bind(B.class);
        });
    }

    @Test(expected = bind_Exception.class)
    public void RegisterNullTest() {
        environment.configure((binder) -> binder.bind(null));
    }

    @Test(expected = bind_Exception.class)
    public void RegisterInterfaceTest() {
        environment.configure((binder) -> binder.bind(Engine.class));
    }

    @Test(expected = bind_Exception.class)
    public void RRegisterAbstractTest() {
        environment.configure((binder) -> binder.bind(Car.class));
    }

    @Test(expected = bind_Exception.class)
    public void RebindClassTest() {
        environment.configure((binder) -> {
            binder.bind(Car.class, SClassCar.class);
            binder.bind(Car.class,Truck.class);
        });
    }

    @Test(expected = bind_Exception.class)
    public void RebindInstanceTest() {
        environment.configure((binder) -> {
            binder.bind(Car.class, SClassCar.class);
            binder.bind(Car.class, new Truck());
        });
    }

    @Test(expected = bind_Exception.class)
    public void TwoConstructorInjectionTest() {
        environment.configure((binder) ->binder.bind(TwoConstructorInjection.class));
    }

    @Test(expected = my_classes.UnknownClassException.class)
    public void NotDependenciesTest() {
        Container container = environment.configure((binder) ->binder.bind(UseSClassCar.class));
        container.getComponent(SClassCar.class);
    }

    @Test
    public void SingletonTest() {
        Container container = environment.configure((binder) -> {
            binder.bind(SClassCar.class);
            binder.bind(UseSClassCar.class);
        });
        UseSClassCar useSClassCar1 = container.getComponent(UseSClassCar.class);
        UseSClassCar useSClassCar2 = container.getComponent(UseSClassCar.class);
        assertSame(useSClassCar1, useSClassCar2);
        assertSame(useSClassCar1.getSclassCar(), useSClassCar2.getSclassCar());
    }

    @Test
    public void InjectDependencyTest() {
        Container container = environment.configure((binder) -> {
            binder.bind(SClassCarPlant.class);
            binder.bind(SClassCar.class);
            binder.bind(UseSClassCar.class);
        });
        SClassCarPlant clazz = container.getComponent(SClassCarPlant.class);
        assertSame(clazz.getsClassCar(), container.getComponent(UseSClassCar.class));
    }

}
