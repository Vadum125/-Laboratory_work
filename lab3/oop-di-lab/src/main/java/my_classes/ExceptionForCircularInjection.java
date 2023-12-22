package my_classes;

public class ExceptionForCircularInjection extends RuntimeException {
    public ExceptionForCircularInjection(String message) {
        super(message);
    }

}
