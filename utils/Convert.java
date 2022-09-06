package utils;

import java.text.DecimalFormat;

public class Convert {
    private static final String pattern = "#########.##########";
    private static final DecimalFormat Format = new DecimalFormat(pattern);

    public static String toDecimal(double number) {
        return Format.format(number);
    }
}
