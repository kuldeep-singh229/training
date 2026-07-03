import java.io.*;
import java.util.*;

class Student implements Serializable {
    private int rollNo;
    private String name;
    private String grade;

    public Student(int rollNo, String name, String grade) {
        this.rollNo = rollNo;
        this.name = name;
        this.grade = grade;
    }

    public int getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Roll No: " + rollNo +
               ", Name: " + name +
               ", Grade: " + grade;
    }
}

class StudentManagement {
    private List<Student> students = new ArrayList<>();
    private static final String DATA_FILE = "students.dat";

    // Add Student
    public void addStudent(Student s) {
        if (rollExists(s.getRollNo())) {
            System.out.println("A student with this roll number already exists.");
            return;
        }
        students.add(s);
        System.out.println("Student added successfully.");
    }

    // Remove Student
    public void removeStudent(int rollNo) {
        Student s = searchStudent(rollNo);

        if (s != null) {
            students.remove(s);
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    // Search Student
    public Student searchStudent(int rollNo) {
        for (Student s : students) {
            if (s.getRollNo() == rollNo) {
                return s;
            }
        }
        return null;
    }

    // Display All Students
    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        System.out.println("\nStudent Records:");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // Edit Student
    public void editStudent(int rollNo, String name, String grade) {
        Student s = searchStudent(rollNo);

        if (s != null) {
            s.setName(name);
            s.setGrade(grade);
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    // Save to File
    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(students);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // Load from File
    public void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("No previous data found.");
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                List<?> loaded = (List<?>) obj;
                List<Student> temp = new ArrayList<>();
                for (Object item : loaded) {
                    if (item instanceof Student) {
                        temp.add((Student) item);
                    }
                }
                students = temp;
                System.out.println("Data loaded successfully.");
            } else {
                System.out.println("Data file has invalid format.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private boolean rollExists(int rollNo) {
        return searchStudent(rollNo) != null;
    }
}

public class StudentManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManagement sm = new StudentManagement();

        sm.loadFromFile();

        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Edit Student");
            System.out.println("6. Save Data");
            System.out.println("7. Exit");

            choice = readInt(sc, "Enter choice: ");

            switch (choice) {
                case 1:
                    int roll = readInt(sc, "Enter Roll Number: ");
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine().trim();

                    System.out.print("Enter Grade: ");
                    String grade = sc.nextLine().trim();

                    if (name.isEmpty() || grade.isEmpty()) {
                        System.out.println("Fields cannot be empty.");
                        break;
                    }

                    sm.addStudent(new Student(roll, name, grade));
                    break;

                case 2:
                    roll = readInt(sc, "Enter Roll Number: ");
                    sm.removeStudent(roll);
                    break;

                case 3:
                    roll = readInt(sc, "Enter Roll Number: ");

                    Student s = sm.searchStudent(roll);
                    if (s != null) {
                        System.out.println(s);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 4:
                    sm.displayStudents();
                    break;

                case 5:
                    roll = readInt(sc, "Enter Roll Number: ");
                    sc.nextLine();

                    System.out.print("Enter New Name: ");
                    name = sc.nextLine().trim();

                    System.out.print("Enter New Grade: ");
                    grade = sc.nextLine().trim();

                    if (name.isEmpty() || grade.isEmpty()) {
                        System.out.println("Fields cannot be empty.");
                        break;
                    }
                    sm.editStudent(roll, name, grade);
                    break;

                case 6:
                    sm.saveToFile();
                    break;

                case 7:
                    sm.saveToFile();
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 7);

        sc.close();
    }

    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!sc.hasNextInt()) {
                System.out.println("Please enter a valid integer.");
                sc.nextLine();
                continue;
            }
            int value = sc.nextInt();
            if (value < 0) {
                System.out.println("Please enter a non-negative integer.");
                continue;
            }
            return value;
        }
    }
}