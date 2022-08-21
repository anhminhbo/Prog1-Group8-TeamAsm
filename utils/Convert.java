package utils;

import java.text.DecimalFormat;

public class Convert {
    private final static String pattern = "#########.##########";
    private final static DecimalFormat Format = new DecimalFormat(pattern);
    public String toDecimal(int number){
        return Format.format(number);
    }
    public static String toDecimal(double number){
        return Format.format(number);
    }
}
