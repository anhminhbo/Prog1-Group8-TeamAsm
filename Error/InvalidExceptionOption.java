package Error;

public class InvalidExceptionOption extends Exception{
    public InvalidExceptionOption() {
    }
    public InvalidExceptionOption(String message, Throwable err) {
        super(message);
    }
}
