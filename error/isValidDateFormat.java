package error;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class isValidDateFormat extends Exception{
    String format = "dd/MM/yyyy";
    Date date = null;

    public boolean isValidFormat(String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(this.format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            System.out.println("Invalid date format! Try again");
        }
        return date != null;
    }

}
