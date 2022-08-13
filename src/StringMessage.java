import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringMessage extends Throwable {
    private boolean bool;

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public StringMessage() {
        System.out.println("Note!! input only string");
    }
    public void methodTry(String string, Matcher matcher){

        try {
            setBool(false);
            if (!matcher.find()) {
                throw new StringMessage();
            }

        } catch (StringMessage e) {
            System.out.println("You input: "+string);
            setBool(true);
        }
    }
}
