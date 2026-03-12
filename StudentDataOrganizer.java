import java.io.*;
import java.util.*;

// ============================
// Abstract Class
// ============================
abstract class StudentEntity {
    String name;

    public StudentEntity(String name) {
        this.name = name;
    }

    abstract void display();
}

// ============================
// Student Class
// ============================
class Student extends StudentEntity {

    int rollNo;
    int marks;
    int attendance;

    public Student(String name, int rollNo, int marks, int attendance) {
        super(name);
        this.rollNo = rollNo;
        this.marks = marks;
        this.attendance = attendance;
    }

    public void display() {
        System.out.println("--------------------------");
        System.out.println("Name: " + name);
        System.out.println("Roll No: " + rollNo);
        System.out.println("Marks: " + marks);
        System.out.println("Attendance: " + attendance + "%");
    }

    public String toFileString() {
        return name + "," + rollNo + "," + marks + "," + attendance;
    }
}

// ============================
// File Handling Class
// ============================
class DataManager {

    static String FILE = "students.txt";

    public static void save(List<Student> students) {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE));

            for (Student s : students) {
                bw.write(s.toFileString());
                bw.newLine();
            }

            bw.close();

        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    public static void load(List<Student> students) {

        try {

            File file = new File(FILE);

            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null) {

                String[] d = line.split(",");

                String name = d[0];
                int roll = Integer.parseInt(d[1]);
                int marks = Integer.parseInt(d[2]);
                int att = Integer.parseInt(d[3]);

                students.add(new Student(name, roll, marks, att));
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
    }
}

// ============================
// Main Application
// ============================
public class StudentDataOrganizer {

    static List<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        DataManager.load(students);

        login();

        menu();
    }

    // ============================
    // Login
    // ============================
    static void login() {

        String user = "admin";
        String pass = "1234";

        while (true) {

            System.out.println("===== LOGIN =====");

            System.out.print("Username: ");
            String u = sc.nextLine();

            System.out.print("Password: ");
            String p = sc.nextLine();

            if (u.equals(user) && p.equals(pass)) {
                System.out.println("Login Successful!\n");
                break;
            } else {
                System.out.println("Invalid Login!\n");
            }
        }
    }

    // ============================
    // Menu
    // ============================
    static void menu() {

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1 Add Student");
            System.out.println("2 View Students");
            System.out.println("3 Search Student");
            System.out.println("4 Sort Students by Marks");
            System.out.println("5 Update Student");
            System.out.println("6 Delete Student");
            System.out.println("7 Save & Exit");

            System.out.print("Choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    sortStudents();
                    break;

                case 5:
                    updateStudent();
                    break;

                case 6:
                    deleteStudent();
                    break;

                case 7:
                    DataManager.save(students);
                    System.out.println("Data Saved. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    // ============================
    // Add Student
    // ============================
    static void addStudent() {

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Roll No: ");
        int roll = sc.nextInt();

        System.out.print("Marks: ");
        int marks = sc.nextInt();

        System.out.print("Attendance: ");
        int att = sc.nextInt();
        sc.nextLine();

        students.add(new Student(name, roll, marks, att));

        DataManager.save(students);

        System.out.println("Student Added!");
    }

    // ============================
    // View Students
    // ============================
    static void viewStudents() {

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students) {
            s.display();
        }
    }

    // ============================
    // Linear Search
    // ============================
    static void searchStudent() {

        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {

            if (s.rollNo == roll) {

                System.out.println("Student Found:");
                s.display();
                return;
            }
        }

        System.out.println("Student Not Found.");
    }

    // ============================
    // Bubble Sort
    // ============================
    static void sortStudents() {

        for (int i = 0; i < students.size() - 1; i++) {

            for (int j = 0; j < students.size() - i - 1; j++) {

                if (students.get(j).marks < students.get(j + 1).marks) {

                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }

        System.out.println("Students Sorted by Marks!");
    }

    // ============================
    // Update Student
    // ============================
    static void updateStudent() {

        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {

            if (s.rollNo == roll) {

                System.out.print("New Marks: ");
                s.marks = sc.nextInt();

                System.out.print("New Attendance: ");
                s.attendance = sc.nextInt();
                sc.nextLine();

                DataManager.save(students);

                System.out.println("Student Updated!");
                return;
            }
        }

        System.out.println("Student Not Found.");
    }

    // ============================
    // Delete Student
    // ============================
    static void deleteStudent() {

        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        sc.nextLine();

        Iterator<Student> it = students.iterator();

        while (it.hasNext()) {

            Student s = it.next();

            if (s.rollNo == roll) {

                it.remove();

                DataManager.save(students);

                System.out.println("Student Deleted!");
                return;
            }
        }

        System.out.println("Student Not Found.");
    }
}