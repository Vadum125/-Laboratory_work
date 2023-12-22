package test.di;
import javax.inject.Inject;

public class TwoConstructorInjection {

    private SClassCar SClasscar;
    private UseSClassCar useSClassCar;
    @Inject
    public TwoConstructorInjection(SClassCar SClasscar) {
        this.SClasscar = SClasscar;
    }
    @Inject
    public TwoConstructorInjection(UseSClassCar useSClassCar) {
        this.useSClassCar = useSClassCar;
    }
}
