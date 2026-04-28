import java.util.Scanner;

public class BenFisherRazMazliah {
    

    public static void main(String[] args) {
        // Submitters: Ben Fisher, Raz Mazliah

        Scanner scanner = new Scanner(System.in);

        String[] lecturers = new String[2];
        String[] committees = new String[2];
        String[] departments = new String[2];

        int lecturerCount = 0;
        int committeeCount = 0;
        int departmentCount = 0;

        System.out.print("Enter college name: ");
        String collegeName = scanner.nextLine();

        boolean running = true;

        while (running) {
            printMenu(collegeName);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    running = false;
                    System.out.println("Exiting program.");
                    break;

                case 1:
                    if (lecturerCount == lecturers.length) {
                        lecturers = growArray(lecturers);
                    }

                    String lecturer = getUniqueName(scanner, lecturers, lecturerCount, "lecturer");
                    if (lecturer == null) {
                        break;
                    }

                    lecturers[lecturerCount] = lecturer;
                    lecturerCount++;
                    System.out.println("- Lecturer added successfully.");
                    break;

                case 2:
                    if (committeeCount == committees.length) {
                        committees = growArray(committees);
                    }

                    String committee = getUniqueName(scanner, committees, committeeCount, "committee");
                    if (committee == null) {
                        break;
                    }

                    committees[committeeCount] = committee;
                    committeeCount++;
                    System.out.println("- Committee added successfully.");
                    break;

                case 3:
                    if (departmentCount == departments.length) {
                        departments = growArray(departments);
                    }

                    String department = getUniqueName(scanner, departments, departmentCount, "department");
                    if (department == null) {
                        break;
                    }

                    departments[departmentCount] = department;
                    departmentCount++;
                    System.out.println("- Department added successfully.");
                    break;

                case 4:
                    String lecturerName = getValidInput(scanner, "- Enter lecturer name: ");
                    if (lecturerName == null) {
                        break;
                    }

                    String committeeName = getValidInput(scanner, "- Enter committee name: ");
                    if (committeeName == null) {
                        break;
                    }

                    boolean lecturerExists = exists(lecturers, lecturerCount, lecturerName);
                    boolean committeeExists = exists(committees, committeeCount, committeeName);

                    if (!lecturerExists) {
                        System.out.println("The lecturer does not exist.");
                    }

                    if (!committeeExists) {
                        System.out.println("The committee does not exist.");
                    }

                    if (lecturerExists && committeeExists) {
                        System.out.println("Both the lecturer and the committee exist.");
                        System.out.println("In part 1, no actual assignment is required.");
                    }
                    break;

                case 5:
                    System.out.println("This option is not implemented in part 1.");
                    break;

                case 6:
                    System.out.println("This option is not implemented in part 1.");
                    break;

                case 7:
                    printItems(lecturers, lecturerCount, "Lecturers:");
                    break;

                case 8:
                    printItems(committees, committeeCount, "Committees:");
                    break;

                default:
                    System.out.println("- Invalid choice. Please try again.");
            }

            System.out.println();
        }

        scanner.close();
    }

    public static void printMenu(String collegeName) {
        System.out.println("----- " + collegeName + " -----");
        System.out.println("0 - Exit");
        System.out.println("1 - Add lecturer");
        System.out.println("2 - Add committee");
        System.out.println("3 - Add department");
        System.out.println("4 - Assign lecturer to committee");
        System.out.println("5 - Show average salary of all lecturers");
        System.out.println("6 - Show average salary of lecturers in a specific department");
        System.out.println("7 - Show all lecturers");
        System.out.println("8 - Show all committees");
        System.out.print("Choose an option: ");
    }

    public static boolean exists(String[] arr, int logicalSize, String name) {
        for (int i = 0; i < logicalSize; i++) {
            if (arr[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static String[] growArray(String[] arr) {
        String[] newArr = new String[arr.length * 2];

        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }

        return newArr;
    }

    public static String getValidInput(Scanner scanner, String message) {
        String input;

        while (true) {
            System.out.println("- To go back, enter 0.");
            System.out.print(message);
            input = scanner.nextLine();

            if (input.equals("0")) {
                return null;
            }

            if (input.trim().isEmpty()) {
                System.out.println("-!- Error: no input entered. Please try again.");
                continue;
            }

            return input;
        }
    }

    public static String getUniqueName(Scanner scanner, String[] arr, int logicalSize, String type) {
        String name;

        while (true) {
            name = getValidInput(scanner, "- Enter " + type + " name: ");

            if (name == null) {
                return null;
            }

            if (exists(arr, logicalSize, name)) {
                System.out.println("-!- This " + type + " already exists. Please enter a different name.");
                continue;
            }

            return name;
        }
    }

    public static void printItems(String[] arr, int logicalSize, String title) {
        if (logicalSize == 0) {
            System.out.println("- No data to display.");
            return;
        }

        System.out.println(title);
        for (int i = 0; i < logicalSize; i++) {
            System.out.println((i + 1) + ". " + arr[i]);
        }
    }
}