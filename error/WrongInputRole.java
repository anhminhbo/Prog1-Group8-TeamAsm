package error;

public class WrongInputRole extends Exception {
    public WrongInputRole() {
    }

    public WrongInputRole(String message) {
        super(message);
    }
}
