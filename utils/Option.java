package utils;

public class Option {
    private static final String[] fields = new String[]{"Input", "Action"};
    private final String labelAction;
    private final String toggleKey;
    private final Command command;

    public Option(String toggleKey, String labelAction, Command command) {
        this.toggleKey = toggleKey;
        this.labelAction = labelAction;
        this.command = command;
    }

    public static String[] getFields() {
        return fields;
    }

    public String[] toStringArray() {
        return new String[]{toggleKey, labelAction};
    }

    public void execute() {
        command.execute();
    }

    public String getLabelAction() {
        return labelAction;
    }

    public String getToggleKey() {
        return toggleKey;
    }
}
