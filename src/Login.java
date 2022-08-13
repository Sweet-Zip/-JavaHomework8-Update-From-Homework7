import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Login {
    Scanner sc = new Scanner(System.in);
    private List<String> accStorage = new ArrayList<String>();
    private String userName;
    private String password;
    ArrayList<Account> array = new ArrayList<Account>();
    public static final int PASSWORD_LENGTH = 8;
    StudentManagement studentManagement = new StudentManagement();

    public Login() throws IOException {
        readAccount();
        String option;
        System.out.println("Login Option:");
        System.out.println("a: Login");
        System.out.println("b: Register");
        System.out.println("exit: to exit the program");
        option = sc.next();
        while (!option.equals("a") && !option.equals("b") && !option.equals("exit")) {
            System.out.println("""
                    Enter only
                    a: to login
                    b: to register
                    exit: to exit the program
                    """);
            option = sc.next();
        }
        switch (option) {
            case "a":
                login();
                break;
            case "b":
                register();
                break;
            case "exit":
                System.exit(0);
        }
    }

    public void login() throws IOException {

        while (true) {
            System.out.print("Enter Username: ");
            userName = sc.next();
            System.out.print("Enter Password: ");
            password = sc.next();
            if (!checkAccount(userName, password)) {
                System.out.println("Username or password is incorrect. Try again");
            } else {
                break;
            }
        }
        studentManagement.menuShow();
    }

    public void register() throws IOException {
        while (true) {
            System.out.print("Enter New Username: ");
            userName = sc.next();
            while (!isValidUser(userName)) {
                System.out.println("Username must be at least 6 character and only char");
                userName = sc.next();
            }
            System.out.print("Enter New Password: ");
            password = sc.next();
            while (!isValidPass(password)) {
                System.out.println("Password must be at least 8 character including digits and chars");
                password = sc.next();
            }
            if (checkUserName(userName)) {
                System.out.println("Username '" + userName + "' Already have. Try another username.");
            } else {
                Account account = new Account(userName, password);
                array.add(account);
                break;
            }
        }
        saveToFile();
        System.out.println("Account Created Successfully");
        new Login();
    }

    private void saveToFile() throws IOException {
        FileWriter fw = new FileWriter("account.txt");
        PrintWriter pw = new PrintWriter(fw);
        if (array.size() != 0) {
            for (int i = 0; i < array.size(); i++) {
                pw.println(array.get(i).getUsername() + ":" + array.get(i).getPassword());
            }
            pw.close();
        }
    }

    public void readAccount() throws IOException {
        File f = new File("account.txt");
        if (f.exists()) {
            FileReader fr = new FileReader("account.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                accStorage.add(line);
                line = br.readLine();
            }
            br.close();
            for (int i = 0; i < accStorage.size(); i++) {
                String[] acc = accStorage.get(i).split(":");
                array.add(new Account(acc[0], acc[1]));
            }
        } else {
            saveToFile();
        }
    }

    public boolean checkUserName(String userName) {
        for (int i = 0; i < array.size(); i++) {
            if (userName.equals(array.get(i).getUsername())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkAccount(String userName, String password) {
        for (int i = 0; i < array.size(); i++) {
            if (userName.equals(array.get(i).getUsername()) && password.equals(array.get(i).getPassword())) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidUser(String username) {
        if (username.length() < 6) {
            return false;
        }
        int charCount = 0;
        for (int i = 0; i < username.length(); i++) {
            char ch = username.charAt(i);
            if (is_Letter(ch)) {
                charCount++;
            } else return false;
        }
        return (charCount >= 1);
    }

    public boolean isValidPass(String password) {
        if (password.length() < PASSWORD_LENGTH) {
            return false;
        }
        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (is_Numeric(ch)) {
                numCount++;
            } else if (is_Letter(ch)) {
                charCount++;
            } else {
                return false;
            }
        }
        return (charCount >= 1 && numCount >= 1);
    }

    public static boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }

    public static boolean is_Numeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }
    /*private boolean validateNewPass(String p){
        boolean bool = true;
        if (!isValidPass(p)) {
            System.out.println("Password must be 8 including digits and chars");
            bool = false;
        }
        return bool;
    }*/
}
