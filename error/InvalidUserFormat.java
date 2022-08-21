package error;

public class InvalidUserFormat extends Exception {
    public InvalidUserFormat() {
    }

    public InvalidUserFormat(String message) {
        super(message);
    }
}
