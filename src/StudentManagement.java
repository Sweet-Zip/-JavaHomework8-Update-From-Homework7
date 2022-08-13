import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StudentManagement extends Student {
    List<Student> array = new ArrayList<Student>();
    private List<String> Storage = new ArrayList<String>();
    Scanner sc = new Scanner(System.in);
    Pattern patternMenuOption = Pattern.compile("^[abcdefgh]");
    Pattern patternABCOption = Pattern.compile("^[abc]");
    Pattern patternString = Pattern.compile("^[aA-zZ]+$");
    Pattern patternDay = Pattern.compile("\\b([1-9]|[12][0-9]|3[01])\\b");
    Pattern patternNumber = Pattern.compile("\\d+");
    private int age;
    private String firstName;
    private String lastName;
    private String address;
    private String ID = null;
    private char confirm;
    private String sex;

    public void menuShow() throws IOException {
        System.out.println("""
                Welcome to java student management demo
                Choose option below
                a: Add Student
                b: Update Student
                c: Delete Student
                d: View all Students
                e: Search Student
                f: Sort Student
                g: Save Data
                h: Load Data
                exit: to go to previous page""");
        menuOption();
    }


    private void menuOption() throws IOException {
        String menuOpt;
        menuOpt = sc.next();
        Matcher matcherMenuOpt = patternMenuOption.matcher(menuOpt);
        while (!matcherMenuOpt.find() && !menuOpt.equals("exit")) {
            System.out.print("Please select from a to h or exit only: ");
            menuOpt = sc.next();
            matcherMenuOpt = patternMenuOption.matcher(menuOpt);
        }
        switch (menuOpt) {
            case "a":
                addStudent();
                break;
            case "b":
                updateStudent();
                break;
            case "c":
                deleteStudent();
                break;
            case "d":
                viewStudent();
                break;
            case "e":
                searchStudent();
                break;
            case "f":
                sortStudent();
                break;
            case "g":
                saveFile();
                break;
            case "h":
                readFile();
                break;
            case "exit":
                Login login=new Login();
                break;
        }
    }

    private void addStudent() throws IOException {

        StringMessage stringMessage = new StringMessage();
        NumberMessage numberMessage = new NumberMessage();


        do {
            System.out.print("Enter Student First Name: ");
            firstName = sc.next();
            Matcher matcherFirstName = patternString.matcher(firstName);
            stringMessage.methodTry(firstName, matcherFirstName);
        } while (stringMessage.isBool());

        do {
            System.out.print("Enter Student Last Name: ");
            lastName = sc.next();
            Matcher matcherLastName = patternString.matcher(lastName);
            stringMessage.methodTry(lastName, matcherLastName);
        } while (stringMessage.isBool());

        System.out.print("Enter Student age: ");
        age = sc.nextInt();

        do {
            System.out.print("Enter Student Address: ");
            address = sc.next();
            Matcher matcherAddress = patternString.matcher(address);
            stringMessage.methodTry(address, matcherAddress);
        } while (stringMessage.isBool());

        while (true) {
            do {
                System.out.print("Enter Student ID: ");
                ID = sc.next();
                Matcher matcherID = patternNumber.matcher(ID);
                numberMessage.methodTry(ID, matcherID);
            } while (stringMessage.isBool());
            if (checkID(ID)) {
                System.out.println("The ID you entered already in used please try another ID");
            } else {
                break;
            }
        }


        System.out.print("Enter Student Sex: ");
        sex = sc.next();
        while (!sex.equals("f") && !sex.equals("F") && !sex.equals("m") && !sex.equals("M")) {
            System.out.print("Input only m or f: ");
            sex = sc.next();
        }

        System.out.println("\nSuccess add this student\n");
        System.out.println("Student first name: " + firstName);
        System.out.println("Student last name: " + lastName);
        System.out.println("Student age: " + age);
        System.out.println("Student address: " + address);
        System.out.println("Student ID: " + ID);
        System.out.println("Student sex: " + sex);
        System.out.println("\n");
        Student student = new Student(firstName, lastName, ID, age, sex, address);
        array.add(student);
        System.out.print("Do you want to add more or go back to main menu?'Y'es and 'N'o: ");
        confirm = sc.next().charAt(0);
        while (confirm != 'y' && confirm != 'Y' && confirm != 'n' && confirm != 'N') {
            System.out.print("Input only Y'es or 'N'o: ");
            confirm = sc.next().charAt(0);
        }
        if (confirm == 'y' || confirm == 'Y') {
            addStudent();
        }
        menuShow();
    }

    private void displayStudentInfo() throws IOException {
        if (array.isEmpty()) {
            System.out.println("There is no student in data.");
            System.out.print("Enter to continues... ");
            sc.nextLine();
            sc.nextLine();
            menuShow();
        } else {
            for (int i = 0; i < array.size(); i++) {
                Student student = array.get(i);
                System.out.println("Name: " + student.getFirstName() + " " + student.getLastName());
                System.out.println("ID: " + student.getID());
                System.out.println("Age: " + student.getAge());
                System.out.println("Sex: " + student.getSex());
                System.out.println("Address: " + student.getAddress());
                System.out.println("\n");
            }
        }
    }

    private void deleteStudent() throws IOException {
        displayStudentInfo();

        while (true) {
            System.out.print("Please enter the student ID to delete: ");
            ID = sc.next();
            if (!checkID(ID)) {
                System.out.println("The ID you entered is not exist in data.");
            } else {
                break;
            }
        }
        for (int i = 0; i < array.size(); i++) {
            if (ID.equals(array.get(i).getID())) {
                array.remove(i);
            }
        }
        System.out.println("Succeed remove");
        System.out.println("Do you want to remove another student? 'Y'es and 'N'o: ");
        confirm = sc.next().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            deleteStudent();
        }
        menuShow();
    }

    private void updateStudent() throws IOException {
        StringMessage stringMessage = new StringMessage();
        displayStudentInfo();

        while (true) {
            System.out.println("Please enter the student ID to modify");
            ID = sc.next();
            if (!checkID(ID)) {
                System.out.println("The ID you entered is not exist in data.");
            } else if (ID.equals("exit")) {
                menuShow();
            } else {
                break;
            }
        }
        do {
            System.out.print("Enter Student First Name: ");
            firstName = sc.next();
            Matcher matcherFirstName = patternString.matcher(firstName);
            stringMessage.methodTry(firstName, matcherFirstName);
        } while (stringMessage.isBool());

        do {
            System.out.print("Enter Student Last Name: ");
            lastName = sc.next();
            Matcher matcherLastName = patternString.matcher(lastName);
            stringMessage.methodTry(lastName, matcherLastName);
        } while (stringMessage.isBool());

        System.out.print("Enter Student age: ");
        age = sc.nextInt();

        do {
            System.out.print("Enter Student Address: ");
            address = sc.next();
            Matcher matcherAddress = patternString.matcher(address);
            stringMessage.methodTry(address, matcherAddress);
        } while (stringMessage.isBool());

        System.out.print("Enter Student Sex: ");
        sex = sc.next();
        while (!sex.equals("f") && !sex.equals("F") && !sex.equals("m") && !sex.equals("M")) {
            System.out.print("Input only m or f: ");
            sex = sc.next();
        }
        Student student = new Student(firstName, lastName, ID, age, sex, address);
        for (int i = 0; i < array.size(); i++) {
            if (ID.equals(array.get(i).getID())) {
                array.set(i, student);
            }
        }
        System.out.println("Succeed remove");
        System.out.println("Do you want to update another student? 'Y'es and 'N'o: ");
        confirm = sc.next().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            updateStudent();
        }
        menuShow();
    }

    private void viewStudent() throws IOException {
        displayStudentInfo();
        System.out.print("Enter to continues... ");
        sc.nextLine();
        sc.nextLine();
        menuShow();
    }

    private boolean checkID(String ID) {
        for (int i = 0; i < array.size(); i++) {
            if (ID.equals(array.get(i).getID())) {
                return true;
            }
        }
        return false;
    }

    private void searchStudent() throws IOException {
        boolean bool = true;
        String searchOpt;
        System.out.println("Search Student:::");
        System.out.println("""
                a: Search By First Name
                b: Search By ID
                c: Go Back To Main Menu""");
        searchOpt = sc.next();
        Matcher matcherABCOpt = patternABCOption.matcher(searchOpt);
        while (!matcherABCOpt.find()) {
            System.out.print("Please select from a to c only: ");
            searchOpt = sc.next();
            matcherABCOpt = patternMenuOption.matcher(searchOpt);
        }

        switch (searchOpt) {
            case "a":
                while (bool) {
                    System.out.println("Please enter the student first name to search");
                    firstName = sc.next();
                    for (int i = 0; i < array.size(); i++) {
                        if (!firstName.equals(array.get(i).getFirstName())) {
                            System.out.println("The ID you entered is not exist in data.");

                        } else if (firstName.equals("exit")) {
                            searchStudent();
                        } else {
                            break;
                        }
                    }

                    for (int i = 0; i < array.size(); i++) {
                        if (firstName.equals(array.get(i).getID())) {
                            displayStudentInfo();
                        }
                    }
                    if (confirm == 'y' || confirm == 'Y') {
                        searchStudent();
                    }
                    menuShow();
                }
                break;
            case "b":
                while (true) {
                    System.out.println("Please enter the student ID to search");
                    ID = sc.next();
                    if (!checkID(ID)) {
                        System.out.println("The ID you entered is not exist in data.");
                    } else if (ID.equals("exit")) {
                        searchStudent();
                    } else {
                        break;
                    }
                    for (int i = 0; i < array.size(); i++) {
                        if (ID.equals(array.get(i).getID())) {
                            displayStudentInfo();
                        }
                    }
                    System.out.println("Do you want to search another student? 'Y'es and 'N'o: ");
                    confirm = sc.next().charAt(0);
                    if (confirm == 'y' || confirm == 'Y') {
                        searchStudent();
                    }
                    menuShow();
                }
                break;
            case "c":
                menuShow();
        }
    }

    public void sortStudent() throws IOException {
        String sortOpt, sortOrder;
        Student student = new Student();
        System.out.println("Sort Student:::");
        System.out.println("""
                a: Sort by ID
                b: Sort by First Name
                c: Go Back To Main Menu
                """);
        sortOpt = sc.next();
        Matcher matcherABCOpt = patternABCOption.matcher(sortOpt);
        while (!matcherABCOpt.find()) {
            System.out.print("Please select from a to c only: ");
            sortOpt = sc.next();
            matcherABCOpt = patternMenuOption.matcher(sortOpt);
        }
        switch (sortOpt) {
            case "a":
                System.out.println("""
                        a: Ascending order (lowest to highest)
                        b: Descending order (highest to lowest)
                        c: Go Back To Main Menu
                        """);
                sortOrder = sc.next();
                matcherABCOpt = patternABCOption.matcher(sortOrder);
                while (!matcherABCOpt.find()) {
                    System.out.print("Please select from a to c only: ");
                    sortOrder = sc.next();
                    matcherABCOpt = patternMenuOption.matcher(sortOrder);
                }
                switch (sortOrder) {
                    case "a":
                        Collections.sort(array, Comparator.comparing(Student::getID));
                        array.forEach(System.out::println);

                        System.out.print("Enter to continues... ");
                        sc.nextLine();
                        sc.nextLine();
                        sortStudent();
                        break;
                    case "b":
                        Collections.sort(array, Comparator.comparing(Student::getID));
                        Collections.reverse(array);
                        array.forEach(System.out::println);

                        System.out.print("Enter to continues... ");
                        sc.nextLine();
                        sc.nextLine();
                        sortStudent();
                        break;
                    case "c":
                        menuShow();
                        break;
                }
                break;
            case "b":
                System.out.println("""
                        a: Ascending order (A-Z)
                        b: Descending order (Z-A)
                        c: Go Back To Main Menu
                        """);
                sortOrder = sc.next();
                matcherABCOpt = patternABCOption.matcher(sortOrder);
                while (!matcherABCOpt.find()) {
                    System.out.print("Please select from a to c only: ");
                    sortOrder = sc.next();
                    matcherABCOpt = patternMenuOption.matcher(sortOrder);
                }
                switch (sortOrder) {
                    case "a":
                        Collections.sort(array, Comparator.comparing(Student::getFirstName));
                        array.forEach(System.out::println);

                        System.out.print("Enter to continues... ");
                        sc.nextLine();
                        sc.nextLine();
                        sortStudent();
                        break;
                    case "b":
                        Collections.sort(array, Comparator.comparing(Student::getFirstName));
                        Collections.reverse(array);
                        array.forEach(System.out::println);

                        System.out.print("Enter to continues... ");
                        sc.nextLine();
                        sc.nextLine();
                        sortStudent();
                        break;
                    case "c":
                        menuShow();
                        break;
                }
                break;
            case "c":
                menuShow();
                break;
        }
    }

    public void saveFile() throws IOException {
        FileWriter fw = new FileWriter("student.txt");
        PrintWriter pw = new PrintWriter(fw);
        if (array.isEmpty()) {
            System.out.println("There is no student in data.");
            System.out.print("Enter to continues... ");
            sc.nextLine();
            sc.nextLine();
            menuShow();
        } else {
            for (int i = 0; i < array.size(); i++) {
                pw.println(array.get(i).getFirstName() + ":" + array.get(i).getLastName() + ":"
                        + array.get(i).getID() + ":" + array.get(i).getAge() + ":"
                        + array.get(i).getSex() + ":" + array.get(i).getAddress());
            }
            pw.close();
        }
        System.out.println("File Save success");
        System.out.print("Enter to continues... ");
        sc.nextLine();
        sc.nextLine();
        menuShow();
    }

    public void readFile() throws IOException {
        FileReader fr = new FileReader("student.txt");
        BufferedReader br = new BufferedReader(fr);
        displayStudent(br);
        convertToStu();
    }

    private void displayStudent(BufferedReader br) throws IOException {
        String line = br.readLine();
        while (line != null) {
            Storage.add(line);
            line = br.readLine();
        }
        br.close();
    }

    private void convertToStu() throws IOException {
        for (int i = 0; i < Storage.size(); i++) {
            String[] stu = Storage.get(i).split(":");
            array.add(new Student(stu[0], stu[1], stu[2], Integer.parseInt(stu[3]), stu[4], stu[5]));
        }
        System.out.println("File import success");
        System.out.print("Enter to continues... ");
        sc.nextLine();
        sc.nextLine();
        menuShow();
    }
}
