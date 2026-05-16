// Submitters:
// Ben Fisher
// Raz Mazliah

import java.util.Scanner;

public class BenFisherRazMazliah {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String collegeName = readNonEmptyString(scanner, "Enter college name: ");
            College college = new College(collegeName);

            boolean running = true;

            while (running) {
                printMenu(college.getName());
                int choice = readInt(scanner, "Choose an option: ");

                switch (choice) {
                    case 0 -> {
                        running = false;
                        System.out.println("Exiting program.");
                    }
                    case 1 -> addLecturer(scanner, college);
                    case 2 -> addCommittee(scanner, college);
                    case 3 -> addLecturerToCommittee(scanner, college);
                    case 4 -> updateCommitteeChairman(scanner, college);
                    case 5 -> removeLecturerFromCommittee(scanner, college);
                    case 6 -> addDepartment(scanner, college);
                    case 7 -> addLecturerToDepartment(scanner, college);
                    case 8 -> showAverageSalary(college);
                    case 9 -> showDepartmentAverageSalary(scanner, college);
                    case 10 -> System.out.println(college.getAllLecturersDetails());
                    case 11 -> System.out.println(college.getAllCommitteesDetails());
                    default -> System.out.println("Invalid choice. Please try again.");
                }

                System.out.println();
            }
        }
    }

    private static void printMenu(String collegeName) {
        System.out.println("----- " + collegeName + " -----");
        System.out.println("0 - Exit");
        System.out.println("1 - Add lecturer");
        System.out.println("2 - Add committee");
        System.out.println("3 - Add lecturer to committee");
        System.out.println("4 - Update committee chairman");
        System.out.println("5 - Remove lecturer from committee");
        System.out.println("6 - Add department");
        System.out.println("7 - Add lecturer to department");
        System.out.println("8 - Show average salary of all lecturers");
        System.out.println("9 - Show average salary of lecturers in a specific department");
        System.out.println("10 - Show all lecturers");
        System.out.println("11 - Show all committees");
    }

    private static void addLecturer(Scanner scanner, College college) {
        String name = readNonEmptyString(scanner, "Enter lecturer name: ");
        int idNumber = readPositiveInt(scanner, "Enter lecturer ID number: ");
        Degree degree = readDegree(scanner);
        String degreeName = readNonEmptyString(scanner, "Enter degree name: ");
        double salary = readNonNegativeDouble(scanner, "Enter salary: ");

        boolean success = college.addLecturer(name, idNumber, degree, degreeName, salary);

        if (success) {
            System.out.println("Lecturer added successfully.");
        } else {
            System.out.println("Could not add lecturer. Name or ID may already exist.");
        }
    }

    private static void addCommittee(Scanner scanner, College college) {
        String committeeName = readNonEmptyString(scanner, "Enter committee name: ");
        String chairmanName = readNonEmptyString(scanner, "Enter chairman lecturer name: ");

        boolean success = college.addCommittee(committeeName, chairmanName);

        if (success) {
            System.out.println("Committee added successfully.");
        } else {
            System.out.println("Could not add committee. Check that the committee does not exist and the chairman is a doctor or professor.");
        }
    }

    private static void addLecturerToCommittee(Scanner scanner, College college) {
        String lecturerName = readNonEmptyString(scanner, "Enter lecturer name: ");
        String committeeName = readNonEmptyString(scanner, "Enter committee name: ");

        boolean success = college.addLecturerToCommittee(lecturerName, committeeName);

        if (success) {
            System.out.println("Lecturer added to committee successfully.");
        } else {
            System.out.println("Could not add lecturer to committee. Check that both exist and the lecturer is not already a member.");
        }
    }

    private static void updateCommitteeChairman(Scanner scanner, College college) {
        String committeeName = readNonEmptyString(scanner, "Enter committee name: ");
        String chairmanName = readNonEmptyString(scanner, "Enter new chairman lecturer name: ");

        boolean success = college.updateCommitteeChairman(committeeName, chairmanName);

        if (success) {
            System.out.println("Committee chairman updated successfully.");
        } else {
            System.out.println("Could not update chairman. Check that the committee exists and the new chairman is a doctor or professor.");
        }
    }

    private static void removeLecturerFromCommittee(Scanner scanner, College college) {
        String lecturerName = readNonEmptyString(scanner, "Enter lecturer name: ");
        String committeeName = readNonEmptyString(scanner, "Enter committee name: ");

        boolean success = college.removeLecturerFromCommittee(lecturerName, committeeName);

        if (success) {
            System.out.println("Lecturer removed from committee successfully.");
        } else {
            System.out.println("Could not remove lecturer from committee. Check that both exist and the lecturer is a committee member.");
        }
    }

    private static void addDepartment(Scanner scanner, College college) {
        String departmentName = readNonEmptyString(scanner, "Enter department name: ");
        int studentCount = readNonNegativeInt(scanner, "Enter number of students: ");

        boolean success = college.addDepartment(departmentName, studentCount);

        if (success) {
            System.out.println("Department added successfully.");
        } else {
            System.out.println("Could not add department. Department may already exist.");
        }
    }

    private static void addLecturerToDepartment(Scanner scanner, College college) {
        String lecturerName = readNonEmptyString(scanner, "Enter lecturer name: ");
        String departmentName = readNonEmptyString(scanner, "Enter department name: ");

        boolean success = college.addLecturerToDepartment(lecturerName, departmentName);

        if (success) {
            System.out.println("Lecturer added to department successfully.");
        } else {
            System.out.println("Could not add lecturer to department. Check that both exist and the lecturer is not already assigned to a department.");
        }
    }

    private static void showAverageSalary(College college) {
        double average = college.getAverageSalary();
        System.out.println("Average salary of all lecturers: " + String.format("%.2f", average));
    }

    private static void showDepartmentAverageSalary(Scanner scanner, College college) {
        String departmentName = readNonEmptyString(scanner, "Enter department name: ");
        double average = college.getDepartmentAverageSalary(departmentName);

        if (average == -1) {
            System.out.println("Department does not exist.");
        } else {
            System.out.println("Average salary in department: " + String.format("%.2f", average));
        }
    }

    private static Degree readDegree(Scanner scanner) {
        while (true) {
            System.out.println("Choose degree:");
            System.out.println("1 - FIRST");
            System.out.println("2 - SECOND");
            System.out.println("3 - DOCTOR");
            System.out.println("4 - PROFESSOR");

            int choice = readInt(scanner, "Enter degree number: ");

            switch (choice) {
                case 1 -> {
                    return Degree.FIRST;
                }
                case 2 -> {
                    return Degree.SECOND;
                }
                case 3 -> {
                    return Degree.DOCTOR;
                }
                case 4 -> {
                    return Degree.PROFESSOR;
                }
                default -> System.out.println("Invalid degree. Please try again.");
            }
        }
    }

    private static String readNonEmptyString(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    private static int readInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a whole number.");
            }
        }
    }

    private static int readPositiveInt(Scanner scanner, String message) {
        while (true) {
            int value = readInt(scanner, message);

            if (value > 0) {
                return value;
            }

            System.out.println("Number must be positive.");
        }
    }

    private static int readNonNegativeInt(Scanner scanner, String message) {
        while (true) {
            int value = readInt(scanner, message);

            if (value >= 0) {
                return value;
            }

            System.out.println("Number cannot be negative.");
        }
    }

    private static double readNonNegativeDouble(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                double value = Double.parseDouble(input);

                if (value >= 0) {
                    return value;
                }

                System.out.println("Number cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid number.");
            }
        }
    }
}