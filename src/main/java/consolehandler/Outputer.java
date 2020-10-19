package consolehandler;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Outputer {
    private static Locale current = Locale.getDefault();

    public static String getString(String string){
        ResourceBundle res = ResourceBundle.getBundle("gui", current);
        return res.getString(string);
    }

    public static String getNumber(Number number){
        NumberFormat nf = NumberFormat.getInstance(current);
        return nf.format(number);
    }

    public static Locale getCurrent() {
        return current;
    }

    public static void setCurrent(Locale current) {
        Outputer.current = current;
    }
}
