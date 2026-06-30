// Submitters:
// Ben Fisher - ID: 213160005
// Raz Mazliah - ID: 324965094

package ben_fisher_raz_matzliach;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String collegeName = readNonEmptyString(scanner, "Enter college name: ");
            College college = new College(collegeName);

            boolean running = true;

            while (running) {
                printMenu(college.getName());
                int choice = readInt(scanner, "Choose an option: ");

                switch (choice) {
                    case 0:
                        running = false;
                        System.out.println("Exiting program.");
                        break;
                    case 1:
                        addLecturer(scanner, college);
                        break;
                    case 2:
                        addCommittee(scanner, college);
                        break;
                    case 3:
                        addLecturerToCommittee(scanner, college);
                        break;
                    case 4:
                        updateCommitteeChairman(scanner, college);
                        break;
                    case 5:
                        removeLecturerFromCommittee(scanner, college);
                        break;
                    case 6:
                        addDepartment(scanner, college);
                        break;
                    case 7:
                        addLecturerToDepartment(scanner, college);
                        break;
                    case 8:
                        showAverageSalary(college);
                        break;
                    case 9:
                        showDepartmentAverageSalary(scanner, college);
                        break;
                    case 10:
                        System.out.println(college.getAllLecturersDetails());
                        break;
                    case 11:
                        System.out.println(college.getAllCommitteesDetails());
                        break;
                    case 12:
                        compareLecturerArticles(scanner, college);
                        break;
                    case 13:
                        compareCommittees(scanner, college);
                        break;
                    case 14:
                        cloneCommittee(scanner, college);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
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
        System.out.println("12 - Compare two doctors/professors by article count");
        System.out.println("13 - Compare two committees");
        System.out.println("14 - Clone/copy committee data");
    }

    private static void addLecturer(Scanner scanner, College college) {
        String name = readNonEmptyString(scanner, "Enter lecturer name: ");
        while (college.lecturerNameExists(name)) {
            System.out.println("Lecturer name already exists. Please enter another name.");
            name = readNonEmptyString(scanner, "Enter lecturer name: ");
        }

        int idNumber = readPositiveInt(scanner, "Enter lecturer ID number: ");
        while (college.lecturerIdExists(idNumber)) {
            System.out.println("Lecturer ID already exists. Please enter another ID.");
            idNumber = readPositiveInt(scanner, "Enter lecturer ID number: ");
        }

        Degree degree = readDegree(scanner);
        String[] articles = new String[0];
        String professorshipBody = "";

        if (degree == Degree.DOCTOR || degree == Degree.PROFESSOR) {
            articles = readArticles(scanner);
        }

        if (degree == Degree.PROFESSOR) {
            professorshipBody = readNonEmptyString(scanner,
                    "Enter the organization/body that granted the professorship: ");
        }

        String degreeName = readNonEmptyString(scanner, "Enter degree name: ");
        double salary = readNonNegativeDouble(scanner, "Enter salary: ");
        Lecturer lecturer;

        if (degree == Degree.DOCTOR) {
            lecturer = new DoctorLecturer(name, idNumber, degreeName, salary, articles);
        } else if (degree == Degree.PROFESSOR) {
            lecturer = new ProfessorLecturer(name, idNumber, degreeName, salary, articles, professorshipBody);
        } else {
            lecturer = new Lecturer(name, idNumber, degree, degreeName, salary);
        }

        try {
            college.addLecturer(lecturer);
            System.out.println("Lecturer added successfully.");
        } catch (CollegeActionException e) {
            printActionError(e);
        }
    }

    private static void addCommittee(Scanner scanner, College college) {
        String committeeName = readNonEmptyString(scanner, "Enter committee name: ");
        while (college.committeeNameExists(committeeName)) {
            System.out.println("Committee name already exists. Please enter another name.");
            committeeName = readNonEmptyString(scanner, "Enter committee name: ");
        }

        String chairmanName = readNonEmptyString(scanner, "Enter chairman lecturer name: ");

        try {
            college.addCommittee(committeeName, chairmanName);
            System.out.println("Committee added successfully.");
        } catch (CollegeActionException e) {
            printActionError(e);
        }
    }

    private static void addLecturerToCommittee(Scanner scanner, College college) {
        String lecturerName = readNonEmptyString(scanner, "Enter lecturer name: ");
        String committeeName = readNonEmptyString(scanner, "Enter committee name: ");

        try {
            college.addLecturerToCommittee(lecturerName, committeeName);
            System.out.println("Lecturer added to committee successfully.");
        } catch (CollegeActionException e) {
            printActionError(e);
        }
    }

    private static void updateCommitteeChairman(Scanner scanner, College college) {
        String committeeName = readNonEmptyString(scanner, "Enter committee name: ");
        String chairmanName = readNonEmptyString(scanner, "Enter new chairman lecturer name: ");

        try {
            college.updateCommitteeChairman(committeeName, chairmanName);
            System.out.println("Committee chairman updated successfully.");
        } catch (CollegeActionException e) {
            printActionError(e);
        }
    }

    private static void removeLecturerFromCommittee(Scanner scanner, College college) {
        String lecturerName = readNonEmptyString(scanner, "Enter lecturer name: ");
        String committeeName = readNonEmptyString(scanner, "Enter committee name: ");

        try {
            college.removeLecturerFromCommittee(lecturerName, committeeName);
            System.out.println("Lecturer removed from committee successfully.");
        } catch (CollegeActionException e) {
            printActionError(e);
        }
    }

    private static void addDepartment(Scanner scanner, College college) {
        String departmentName = readNonEmptyString(scanner, "Enter department name: ");
        while (college.departmentNameExists(departmentName)) {
            System.out.println("Department name already exists. Please enter another name.");
            departmentName = readNonEmptyString(scanner, "Enter department name: ");
        }

        int studentCount = readNonNegativeInt(scanner, "Enter number of students: ");

        try {
            college.addDepartment(departmentName, studentCount);
            System.out.println("Department added successfully.");
        } catch (CollegeActionException e) {
            printActionError(e);
        }
    }

    private static void addLecturerToDepartment(Scanner scanner, College college) {
        String lecturerName = readNonEmptyString(scanner, "Enter lecturer name: ");
        String departmentName = readNonEmptyString(scanner, "Enter department name: ");

        try {
            college.addLecturerToDepartment(lecturerName, departmentName);
            System.out.println("Lecturer added to department successfully.");
        } catch (CollegeActionException e) {
            printActionError(e);
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

    private static void compareLecturerArticles(Scanner scanner, College college) {
        String firstName = readNonEmptyString(scanner, "Enter first lecturer name: ");
        String secondName = readNonEmptyString(scanner, "Enter second lecturer name: ");

        try {
            int firstCount = college.getArticleCountForLecturer(firstName);
            int secondCount = college.getArticleCountForLecturer(secondName);
            printComparisonResult(firstName, firstCount, secondName, secondCount, "articles");
        } catch (CollegeActionException e) {
            printActionError(e);
        }
    }

    private static void compareCommittees(Scanner scanner, College college) {
        String firstName = readNonEmptyString(scanner, "Enter first committee name: ");
        String secondName = readNonEmptyString(scanner, "Enter second committee name: ");
        int criterion = readCommitteeComparisonCriterion(scanner);

        try {
            int firstValue;
            int secondValue;
            String criterionName;

            if (criterion == 1) {
                firstValue = college.getCommitteeStaffCount(firstName);
                secondValue = college.getCommitteeStaffCount(secondName);
                criterionName = "staff members";
            } else {
                firstValue = college.getCommitteeTotalArticleCount(firstName);
                secondValue = college.getCommitteeTotalArticleCount(secondName);
                criterionName = "articles";
            }

            printComparisonResult(firstName, firstValue, secondName, secondValue, criterionName);
        } catch (CollegeActionException e) {
            printActionError(e);
        }
    }

    private static void cloneCommittee(Scanner scanner, College college) {
        String originalName = readNonEmptyString(scanner, "Enter original committee name: ");

        try {
            college.cloneCommittee(originalName);
            System.out.println("Committee cloned successfully as new-" + originalName + ".");
        } catch (CollegeActionException e) {
            printActionError(e);
        }
    }

    private static int readCommitteeComparisonCriterion(Scanner scanner) {
        while (true) {
            System.out.println("Choose comparison criterion:");
            System.out.println("1 - By number of staff assigned to the committee");
            System.out.println("2 - By total number of articles written by the committee staff");

            int criterion = readInt(scanner, "Enter criterion number: ");

            if (criterion == 1 || criterion == 2) {
                return criterion;
            }

            System.out.println("Invalid criterion. Please try again.");
        }
    }

    private static void printComparisonResult(String firstName, int firstValue, String secondName, int secondValue,
            String criterionName) {
        if (firstValue > secondValue) {
            System.out.println(firstName + " has more " + criterionName + " (" + firstValue + " vs "
                    + secondValue + ").");
        } else if (secondValue > firstValue) {
            System.out.println(secondName + " has more " + criterionName + " (" + secondValue + " vs "
                    + firstValue + ").");
        } else {
            System.out.println(firstName + " and " + secondName + " are equal by " + criterionName + " ("
                    + firstValue + ").");
        }
    }

    private static void printActionError(CollegeActionException e) {
        System.out.println("Could not complete action: " + e.getMessage());
    }

    private static String[] readArticles(Scanner scanner) {
        int articleCount = readNonNegativeInt(scanner, "Enter number of published articles: ");
        String[] articles = new String[articleCount];

        for (int i = 0; i < articleCount; i++) {
            articles[i] = readNonEmptyString(scanner, "Enter article title " + (i + 1) + ": ");
        }

        return articles;
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
                case 1:
                    return Degree.FIRST;
                case 2:
                    return Degree.SECOND;
                case 3:
                    return Degree.DOCTOR;
                case 4:
                    return Degree.PROFESSOR;
                default:
                    System.out.println("Invalid degree. Please try again.");
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
