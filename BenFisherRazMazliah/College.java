package BenFisherRazMazliah;

public class College {
    private final String name;

    private Lecturer[] lecturers;
    private Department[] departments;
    private Committee[] committees;

    private Department[] lecturerDepartments;
    private int[] lecturerIds;

    private int lecturerCount;
    private int departmentCount;
    private int committeeCount;

    public College(String name) {
        this.name = name;

        this.lecturers = new Lecturer[2];
        this.departments = new Department[2];
        this.committees = new Committee[2];

        this.lecturerDepartments = new Department[2];
        this.lecturerIds = new int[2];

        this.lecturerCount = 0;
        this.departmentCount = 0;
        this.committeeCount = 0;
    }

    public String getName() {
        return name;
    }

    public boolean lecturerNameExists(String name) {
        return findLecturerByName(name) != null;
    }

    public boolean lecturerIdExists(int idNumber) {
        return findLecturerById(idNumber) != null;
    }

    public boolean departmentNameExists(String name) {
        return findDepartmentByName(name) != null;
    }

    public boolean committeeNameExists(String name) {
        return findCommitteeByName(name) != null;
    }

    public boolean addLecturer(String name, int idNumber, Degree degree, String degreeName, double salary) {
        if (isEmpty(name) || idNumber <= 0 || degree == null || isEmpty(degreeName) || salary < 0) {
            return false;
        }

        if (findLecturerByName(name) != null || findLecturerById(idNumber) != null) {
            return false;
        }

        if (lecturerCount == lecturers.length) {
            increaseLecturersArray();
        }

        lecturers[lecturerCount] = new Lecturer(name.trim(), idNumber, degree, degreeName.trim(), salary);
        lecturerIds[lecturerCount] = idNumber;
        lecturerDepartments[lecturerCount] = null;

        lecturerCount++;
        return true;
    }

    public boolean addDepartment(String name, int studentCount) {
        if (isEmpty(name) || studentCount < 0) {
            return false;
        }

        if (findDepartmentByName(name) != null) {
            return false;
        }

        if (departmentCount == departments.length) {
            increaseDepartmentsArray();
        }

        departments[departmentCount] = new Department(name.trim(), studentCount);
        departmentCount++;

        return true;
    }

    public boolean addCommittee(String committeeName, String chairmanName) {
        if (isEmpty(committeeName) || isEmpty(chairmanName)) {
            return false;
        }

        if (findCommitteeByName(committeeName) != null) {
            return false;
        }

        Lecturer chairman = findLecturerByName(chairmanName);

        if (chairman == null || !chairman.isDoctorOrAbove()) {
            return false;
        }

        if (committeeCount == committees.length) {
            increaseCommitteesArray();
        }

        Committee committee = new Committee(committeeName.trim(), chairman);

        if (!chairman.addCommittee(committee)) {
            return false;
        }

        committees[committeeCount] = committee;
        committeeCount++;

        return true;
    }

    public boolean addLecturerToDepartment(String lecturerName, String departmentName) {
        int lecturerIndex = findLecturerIndexByName(lecturerName);
        Department department = findDepartmentByName(departmentName);

        if (lecturerIndex == -1 || department == null) {
            return false;
        }

        Lecturer lecturer = lecturers[lecturerIndex];

        if (lecturer.getDepartment() != null) {
            return false;
        }

        if (!department.addLecturer(lecturer)) {
            return false;
        }

        lecturer.setDepartment(department);
        lecturerDepartments[lecturerIndex] = department;

        return true;
    }

    public boolean addLecturerToCommittee(String lecturerName, String committeeName) {
        int lecturerIndex = findLecturerIndexByName(lecturerName);
        Committee committee = findCommitteeByName(committeeName);

        if (lecturerIndex == -1 || committee == null) {
            return false;
        }

        Lecturer lecturer = lecturers[lecturerIndex];

        if (!committee.addMember(lecturer)) {
            return false;
        }

        if (!lecturer.addCommittee(committee)) {
            committee.removeMember(lecturer);
            return false;
        }

        return true;
    }

    public boolean removeLecturerFromCommittee(String lecturerName, String committeeName) {
        int lecturerIndex = findLecturerIndexByName(lecturerName);
        Committee committee = findCommitteeByName(committeeName);

        if (lecturerIndex == -1 || committee == null) {
            return false;
        }

        Lecturer lecturer = lecturers[lecturerIndex];

        if (!committee.removeMember(lecturer)) {
            return false;
        }

        if (!lecturer.removeCommittee(committee)) {
            committee.addMember(lecturer);
            return false;
        }

        return true;
    }

    public boolean updateCommitteeChairman(String committeeName, String chairmanName) {
        Committee committee = findCommitteeByName(committeeName);
        int chairmanIndex = findLecturerIndexByName(chairmanName);

        if (committee == null || chairmanIndex == -1) {
            return false;
        }

        Lecturer chairman = lecturers[chairmanIndex];

        if (!chairman.isDoctorOrAbove()) {
            return false;
        }

        Lecturer oldChairman = committee.getChairman();
        boolean addedCommitteeToNewChairman = false;

        if (!chairman.hasCommittee(committee)) {
            if (!chairman.addCommittee(committee)) {
                return false;
            }

            addedCommitteeToNewChairman = true;
        }

        if (!committee.setChairman(chairman)) {
            if (addedCommitteeToNewChairman) {
                chairman.removeCommittee(committee);
            }

            return false;
        }

        if (oldChairman != chairman && !committee.hasMember(oldChairman)) {
            oldChairman.removeCommittee(committee);
        }

        return true;
    }

    public double getAverageSalary() {
        if (lecturerCount == 0) {
            return 0;
        }

        double sum = 0;

        for (int i = 0; i < lecturerCount; i++) {
            sum += lecturers[i].getSalary();
        }

        return sum / lecturerCount;
    }

    public double getDepartmentAverageSalary(String departmentName) {
        Department department = findDepartmentByName(departmentName);

        if (department == null) {
            return -1;
        }

        return department.getAverageSalary();
    }

    public String getAllLecturersDetails() {
        if (lecturerCount == 0) {
            return "No lecturers to display.";
        }

        String result = "Lecturers:";

        for (int i = 0; i < lecturerCount; i++) {
            result += "\n" + (i + 1) + ". " + lecturers[i];
            result += "\n   Department: " + getDepartmentNameForLecturer(i);
            result += "\n   Committees: " + lecturers[i].getCommitteeNames();
        }

        return result;
    }

    public String getAllCommitteesDetails() {
        if (committeeCount == 0) {
            return "No committees to display.";
        }

        String result = "Committees:";

        for (int i = 0; i < committeeCount; i++) {
            result += "\n" + (i + 1) + ". " + committees[i];
        }

        return result;
    }

    private Lecturer findLecturerByName(String name) {
        int index = findLecturerIndexByName(name);

        if (index == -1) {
            return null;
        }

        return lecturers[index];
    }

    private int findLecturerIndexByName(String name) {
        if (isEmpty(name)) {
            return -1;
        }

        String fixedName = name.trim();

        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i].getName().equalsIgnoreCase(fixedName)) {
                return i;
            }
        }

        return -1;
    }

    private Lecturer findLecturerById(int idNumber) {
        for (int i = 0; i < lecturerCount; i++) {
            if (lecturerIds[i] == idNumber) {
                return lecturers[i];
            }
        }

        return null;
    }

    private Department findDepartmentByName(String name) {
        if (isEmpty(name)) {
            return null;
        }

        String fixedName = name.trim();

        for (int i = 0; i < departmentCount; i++) {
            if (departments[i].getName().equalsIgnoreCase(fixedName)) {
                return departments[i];
            }
        }

        return null;
    }

    private Committee findCommitteeByName(String name) {
        if (isEmpty(name)) {
            return null;
        }

        String fixedName = name.trim();

        for (int i = 0; i < committeeCount; i++) {
            if (committees[i].getName().equalsIgnoreCase(fixedName)) {
                return committees[i];
            }
        }

        return null;
    }

    private String getDepartmentNameForLecturer(int lecturerIndex) {
        Department department = lecturers[lecturerIndex].getDepartment();

        if (department == null) {
            return "No department";
        }

        return department.getName();
    }

    private void increaseLecturersArray() {
        Lecturer[] newLecturers = new Lecturer[lecturers.length * 2];
        Department[] newLecturerDepartments = new Department[lecturerDepartments.length * 2];
        int[] newLecturerIds = new int[lecturerIds.length * 2];

        for (int i = 0; i < lecturerCount; i++) {
            newLecturers[i] = lecturers[i];
            newLecturerDepartments[i] = lecturerDepartments[i];
            newLecturerIds[i] = lecturerIds[i];
        }

        lecturers = newLecturers;
        lecturerDepartments = newLecturerDepartments;
        lecturerIds = newLecturerIds;
    }

    private void increaseDepartmentsArray() {
        Department[] newDepartments = new Department[departments.length * 2];

        for (int i = 0; i < departmentCount; i++) {
            newDepartments[i] = departments[i];
        }

        departments = newDepartments;
    }

    private void increaseCommitteesArray() {
        Committee[] newCommittees = new Committee[committees.length * 2];

        for (int i = 0; i < committeeCount; i++) {
            newCommittees[i] = committees[i];
        }

        committees = newCommittees;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
