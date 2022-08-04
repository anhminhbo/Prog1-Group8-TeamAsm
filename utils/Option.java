package utils;

public class Option {
    private static final String[] fields = new String[]{"Input", "Action"};
    private final String name;
    private final String toggleKey;
    private final Command command;

    public Option(String toggleKey, String name, Command command) {
        this.toggleKey = toggleKey;
        this.name = name;
        this.command = command;
    }

    public static String[] getFields() {
        return fields;
    }

    public String[] toStringArray() {
        return new String[]{toggleKey, name};
    }

    public void execute() throws Exception {
        try {
            command.execute();
        } catch (Exception err) {
            System.out.println(err
            );
        }
    }

    public String getLabel() {
        return name;
    }

    public String getToggleKey() {
        return toggleKey;
    }
}
