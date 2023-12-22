package test.di;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UseSClassCar {
    private final SClassCar SclassCar;
    @Inject
    public UseSClassCar(SClassCar SclassCar) {
        this.SclassCar = SclassCar;
    }

    public SClassCar getSclassCar() {
        return SclassCar;
    }
}
