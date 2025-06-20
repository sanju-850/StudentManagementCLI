package project;

import java.util.*;

class Student {
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name;
    }
}

class Course {
    private String code;
    private String title;

    public Course(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }

    @Override
    public String toString() {
        return code + " - " + title;
    }
}


class Exam {
    private Course course;
    private String date;

    public Exam(Course course, String date) {
        this.course = course;
        this.date = date;
    }

    public Course getCourse() { return course; }
    public String getDate() { return date; }

    @Override
    public String toString() {
        return "Exam for " + course.getTitle() + " on " + date;
    }
}

class Mark {
    private Student student;
    private Course course;
    private int score;

    public Mark(Student student, Course course, int score) {
        this.student = student;
        this.course = course;
        this.score = score;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public int getScore() { return score; }

    public String getGrade() {
        if (score >= 90) return "A+";
        if (score >= 80) return "A";
        if (score >= 70) return "B";
        if (score >= 60) return "C";
        if (score >= 50) return "D";
        return "F";
    }

    @Override
    public String toString() {
        return student.getName() + " scored " + score + " (" + getGrade() + ") in " + course.getTitle();
    }
}


class Student_Manager {
    private List<Student> students = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Exam> exams = new ArrayList<>();
    private List<Mark> marks = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Students");
            System.out.println("2. Courses");
            System.out.println("3. Exams");
            System.out.println("4. Marks");
            System.out.println("5. Exit");
            int choice = getIntInput("Choose option: ");

            switch (choice) {
                case 1 -> handleStudents();
                case 2 -> handleCourses();
                case 3 -> handleExams();
                case 4 -> handleMarks();
                case 5 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void handleStudents() {
        System.out.println("1. Add student\n2. View students");
        int choice = getIntInput("Choose option: ");

        if (choice == 1) {
            int id = getIntInput("Enter ID: ");
            if (findStudent(id) != null) {
                System.out.println("Student with this ID already exists.");
                return;
            }
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            students.add(new Student(id, name));
            System.out.println("Student added.");
        } else if (choice == 2) {
            if (students.isEmpty()) System.out.println("No students found.");
            students.forEach(System.out::println);
        }
    }

    private void handleCourses() {
        System.out.println("1. Add course\n2. View courses");
        int choice = getIntInput("Choose option: ");

        if (choice == 1) {
            System.out.print("Enter code: ");
            String code = scanner.nextLine();
            if (findCourse(code) != null) {
                System.out.println("Course already exists.");
                return;
            }
            System.out.print("Enter title: ");
            String title = scanner.nextLine();
            courses.add(new Course(code, title));
            System.out.println("Course added.");
        } else if (choice == 2) {
            if (courses.isEmpty()) System.out.println("No courses found.");
            courses.forEach(System.out::println);
        }
    }

    private void handleExams() {
        System.out.println("1. Schedule exam\n2. View exams");
        int choice = getIntInput("Choose option: ");

        if (choice == 1) {
            System.out.print("Enter course code: ");
            String code = scanner.nextLine();
            Course course = findCourse(code);
            if (course == null) {
                System.out.println("Course not found.");
                return;
            }
            System.out.print("Enter date (YYYY-MM-DD): ");
            String date = scanner.nextLine();
            exams.add(new Exam(course, date));
            System.out.println("Exam scheduled.");
        } else if (choice == 2) {
            if (exams.isEmpty()) System.out.println("No exams found.");
            exams.forEach(System.out::println);
        }
    }

    private void handleMarks() {
        System.out.println("1. Assign mark\n2. View marks\n3. Search marks by student ID");
        int choice = getIntInput("Choose option: ");

        if (choice == 1) {
            int id = getIntInput("Student ID: ");
            Student student = findStudent(id);
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            System.out.print("Enter course code: ");
            String code = scanner.nextLine();
            Course course = findCourse(code);
            if (course == null) {
                System.out.println("Course not found.");
                return;
            }

            int score = getIntInput("Enter score (0–100): ");
            if (score < 0 || score > 100) {
                System.out.println("Invalid score.");
                return;
            }

            marks.add(new Mark(student, course, score));
            System.out.println("Mark added.");
        } else if (choice == 2) {
            if (marks.isEmpty()) System.out.println("No marks recorded.");
            marks.forEach(System.out::println);
        } else if (choice == 3) {
            int id = getIntInput("Enter student ID: ");
            marks.stream()
                    .filter(m -> m.getStudent().getId() == id)
                    .forEach(System.out::println);
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please try again.");
            }
        }
    }

    private Student findStudent(int id) {
        return students.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    private Course findCourse(String code) {
        return courses.stream().filter(c -> c.getCode().equalsIgnoreCase(code)).findFirst().orElse(null);
    }
}


public class StudentManagementCLI {
    public static void main(String[] args) {
        Student_Manager manager = new Student_Manager();
        manager.start();
    }
}