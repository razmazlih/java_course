public class College {
    private final String name;

    private Lecturer[] lecturers;
    private Department[] departments;
    private Committee[] committees;

    private Department[] lecturerDepartments;
    private int[] lecturerIds;
    private Committee[][] lecturerCommittees;
    private int[] lecturerCommitteeCounts;

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
        this.lecturerCommittees = new Committee[2][];
        this.lecturerCommitteeCounts = new int[2];

        this.lecturerCount = 0;
        this.departmentCount = 0;
        this.committeeCount = 0;
    }

    public String getName() {
        return name;
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
        lecturerCommittees[lecturerCount] = new Committee[2];
        lecturerCommitteeCounts[lecturerCount] = 0;

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

        committees[committeeCount] = new Committee(committeeName.trim(), chairman);
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

        boolean addedToCollege = addCommitteeToLecturer(lecturerIndex, committee);
        boolean addedToLecturer = lecturer.addCommittee(committee);

        if (!addedToCollege || !addedToLecturer) {
            committee.removeMember(lecturer);
            removeCommitteeFromLecturer(lecturerIndex, committee);
            lecturer.removeCommittee(committee);
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

        boolean removedFromCollege = removeCommitteeFromLecturer(lecturerIndex, committee);
        boolean removedFromLecturer = lecturer.removeCommittee(committee);

        if (!removedFromCollege || !removedFromLecturer) {
            committee.addMember(lecturer);
            addCommitteeToLecturer(lecturerIndex, committee);
            lecturer.addCommittee(committee);
            return false;
        }

        return true;
    }

    public boolean updateCommitteeChairman(String committeeName, String chairmanName) {
        Committee committee = findCommitteeByName(committeeName);
        Lecturer chairman = findLecturerByName(chairmanName);

        if (committee == null || chairman == null || !chairman.isDoctorOrAbove()) {
            return false;
        }

        return committee.setChairman(chairman);
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
            result += "\n   Committees: " + getCommitteesNamesForLecturer(i);
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

    private boolean addCommitteeToLecturer(int lecturerIndex, Committee committee) {
        if (lecturerIndex < 0 || lecturerIndex >= lecturerCount || committee == null) {
            return false;
        }

        for (int i = 0; i < lecturerCommitteeCounts[lecturerIndex]; i++) {
            if (lecturerCommittees[lecturerIndex][i] == committee) {
                return false;
            }
        }

        if (lecturerCommittees[lecturerIndex] == null) {
            lecturerCommittees[lecturerIndex] = new Committee[2];
        }

        if (lecturerCommitteeCounts[lecturerIndex] == lecturerCommittees[lecturerIndex].length) {
            increaseLecturerCommitteesArray(lecturerIndex);
        }

        lecturerCommittees[lecturerIndex][lecturerCommitteeCounts[lecturerIndex]] = committee;
        lecturerCommitteeCounts[lecturerIndex]++;

        return true;
    }

    private boolean removeCommitteeFromLecturer(int lecturerIndex, Committee committee) {
        if (lecturerIndex < 0 || lecturerIndex >= lecturerCount || committee == null) {
            return false;
        }

        for (int i = 0; i < lecturerCommitteeCounts[lecturerIndex]; i++) {
            if (lecturerCommittees[lecturerIndex][i] == committee) {
                for (int j = i; j < lecturerCommitteeCounts[lecturerIndex] - 1; j++) {
                    lecturerCommittees[lecturerIndex][j] = lecturerCommittees[lecturerIndex][j + 1];
                }

                lecturerCommittees[lecturerIndex][lecturerCommitteeCounts[lecturerIndex] - 1] = null;
                lecturerCommitteeCounts[lecturerIndex]--;

                return true;
            }
        }

        return false;
    }

    private String getDepartmentNameForLecturer(int lecturerIndex) {
        Department department = lecturers[lecturerIndex].getDepartment();

        if (department == null) {
            return "No department";
        }

        return department.getName();
    }

    private String getCommitteesNamesForLecturer(int lecturerIndex) {
        if (lecturerCommitteeCounts[lecturerIndex] == 0) {
            return "No committees";
        }

        String result = "";

        for (int i = 0; i < lecturerCommitteeCounts[lecturerIndex]; i++) {
            if (i > 0) {
                result += ", ";
            }

            result += lecturerCommittees[lecturerIndex][i].getName();
        }

        return result;
    }

    private void increaseLecturersArray() {
        Lecturer[] newLecturers = new Lecturer[lecturers.length * 2];
        Department[] newLecturerDepartments = new Department[lecturerDepartments.length * 2];
        int[] newLecturerIds = new int[lecturerIds.length * 2];
        Committee[][] newLecturerCommittees = new Committee[lecturerCommittees.length * 2][];
        int[] newLecturerCommitteeCounts = new int[lecturerCommitteeCounts.length * 2];

        for (int i = 0; i < lecturerCount; i++) {
            newLecturers[i] = lecturers[i];
            newLecturerDepartments[i] = lecturerDepartments[i];
            newLecturerIds[i] = lecturerIds[i];
            newLecturerCommittees[i] = lecturerCommittees[i];
            newLecturerCommitteeCounts[i] = lecturerCommitteeCounts[i];
        }

        lecturers = newLecturers;
        lecturerDepartments = newLecturerDepartments;
        lecturerIds = newLecturerIds;
        lecturerCommittees = newLecturerCommittees;
        lecturerCommitteeCounts = newLecturerCommitteeCounts;
    }

    private void increaseDepartmentsArray() {
        Department[] newDepartments = new Department[departments.length * 2];
        System.arraycopy(departments, 0, newDepartments, 0, departmentCount);
        departments = newDepartments;
    }

    private void increaseCommitteesArray() {
        Committee[] newCommittees = new Committee[committees.length * 2];
        System.arraycopy(committees, 0, newCommittees, 0, committeeCount);
        committees = newCommittees;
    }

    private void increaseLecturerCommitteesArray(int lecturerIndex) {
        Committee[] newCommittees = new Committee[lecturerCommittees[lecturerIndex].length * 2];

        System.arraycopy(lecturerCommittees[lecturerIndex], 0, newCommittees, 0, lecturerCommitteeCounts[lecturerIndex]);

        lecturerCommittees[lecturerIndex] = newCommittees;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}