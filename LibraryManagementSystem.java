import java.util.ArrayList;
import java.util.Scanner;
class Student {
    String studentName;
    String email;
    public Student(String studentName, String email) {
        this.studentName = studentName;
        this.email = email;
    }
}
class Book {
    String bookTitle;
    String author;
    boolean isAvailable;
    public Book(String bookTitle, String author) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.isAvailable = true;
    }
}
class Transaction {
    Student student;
    Book book;
    String dueDate;
    public Transaction(Student student, Book book, String dueDate) {
        this.student = student;
        this.book = book;
        this.dueDate = dueDate;
    }
}
public class LibraryManagementSystem {
    static ArrayList<Student> students = new ArrayList<>();
    static ArrayList<Book> books = new ArrayList<>();
    static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nLibrary Management System Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Add Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display All Transactions");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character
            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    addBook(scanner);
                    break;
                case 3:
                    borrowBook(scanner);
                    break;
                case 4:
                    returnBook(scanner);
                    break;
                case 5:
                    displayTransactions();
                    break;
                case 6:
                    System.out.println("Exiting Library Management System. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void addStudent(Scanner scanner) {
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();
        System.out.print("Enter student email: ");
        String email = scanner.nextLine();
        Student student = new Student(studentName, email);
        students.add(student);
        System.out.println("Student added successfully!");
    }
    private static void addBook(Scanner scanner) {
        System.out.print("Enter book title: ");
        String bookTitle = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        Book book = new Book(bookTitle, author);
        books.add(book);
        System.out.println("Book added successfully!");
    }
    private static void borrowBook(Scanner scanner) {
        System.out.print("Enter student email: ");
        String email = scanner.nextLine();
        Student student = findStudentByEmail(email);
        if (student == null) {
            System.out.println("Student not found. Please add the student first.");
            return;
        }
        System.out.print("Enter book title: ");
        String bookTitle = scanner.nextLine();

        Book book = findBookByTitle(bookTitle);
        if (book == null || !book.isAvailable) {
            System.out.println("Book not found or already borrowed.");
            return;
        }
        System.out.print("Enter due date (yyyy-MM-dd): ");
        String dueDate = scanner.nextLine();
        Transaction transaction = new Transaction(student, book, dueDate);
        transactions.add(transaction);
        book.isAvailable = false;
        System.out.println("Book borrowed successfully!");
    }
    private static void returnBook(Scanner scanner) {
        System.out.print("Enter student email: ");
        String email = scanner.nextLine();
        Student student = findStudentByEmail(email);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.print("Enter book title: ");
        String bookTitle = scanner.nextLine();
        Book book = findBookByTitle(bookTitle);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        Transaction transaction = findTransaction(student, book);
        if (transaction == null) {
            System.out.println("Transaction not found. Book may not be borrowed by this student.");
            return;
        }
        transactions.remove(transaction);
        book.isAvailable = true;
        System.out.println("Book returned successfully!");
    }
    private static void displayTransactions() {
        System.out.println("\nAll Transactions:");
        System.out.printf("%-20s %-20s %-20s %-20s\n", "Student Name", "Email", "Book Title", "Due Date");
        System.out.println("-------------------------------------------------------------------");
        for (Transaction transaction : transactions) {
            System.out.printf("%-20s %-20s %-20s %-20s\n",
                    transaction.student.studentName,
                    transaction.student.email,
                    transaction.book.bookTitle,
                    transaction.dueDate);
        }
    }
    private static Student findStudentByEmail(String email) {
        for (Student student : students) {
            if (student.email.equals(email)) {
                return student;
            }
        }
        return null;
    }
    private static Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.bookTitle.equals(title)) {
                return book;
            }
        }
        return null;
    }
    private static Transaction findTransaction(Student student, Book book) {
        for (Transaction transaction : transactions) {
            if (transaction.student == student && transaction.book == book) {
                return transaction;
            }
        }
        return null;
    }
}
