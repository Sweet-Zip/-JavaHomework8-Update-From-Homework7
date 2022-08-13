import java.util.regex.Matcher;

public class NumberMessage extends Throwable {
    private boolean bool;
    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }


    public NumberMessage() {
        System.out.println("Input only number");
    }
    public void methodTry(String num, Matcher matcher){

        try {
            setBool(false);
            if (!matcher.find()) {
                throw new NumberMessage();
            }
        } catch (NumberMessage e) {
            System.out.println("You input: "+num);
            setBool(true);
        } finally {
            setBool(true);
        }
    }
}
