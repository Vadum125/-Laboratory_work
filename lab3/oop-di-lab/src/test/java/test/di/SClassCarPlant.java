package test.di;
import javax.inject.Inject;
public class SClassCarPlant {
    private final UseSClassCar sClassCar;
    @Inject
    public SClassCarPlant(UseSClassCar sClassCar) {
        this.sClassCar = sClassCar;
    }

    public UseSClassCar getsClassCar() {
        return sClassCar;
    }
}
