package error;

public class WrongInputType extends Exception {
    public WrongInputType() {
    }

    public WrongInputType(String message, Throwable err) {
        super(message);
    }
}
