package ben_fisher_raz_matzliach;

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

    public void addLecturer(String name, int idNumber, Degree degree, String degreeName, double salary)
            throws CollegeActionException {
        if (isEmpty(name) || idNumber <= 0 || degree == null || isEmpty(degreeName) || salary < 0) {
            throw new CollegeActionException("Lecturer details are not valid.");
        }

        addLecturer(new Lecturer(name.trim(), idNumber, degree, degreeName.trim(), salary));
    }

    public void addLecturer(Lecturer lecturer) throws CollegeActionException {
        if (lecturer == null || isEmpty(lecturer.getName()) || lecturer.getIdNumber() <= 0) {
            throw new CollegeActionException("Lecturer details are not valid.");
        }

        if (findLecturerByName(lecturer.getName()) != null || findLecturerById(lecturer.getIdNumber()) != null) {
            throw new CollegeActionException("Lecturer name or ID already exists.");
        }

        if (lecturerCount == lecturers.length) {
            increaseLecturersArray();
        }

        lecturers[lecturerCount] = lecturer;
        lecturerIds[lecturerCount] = lecturer.getIdNumber();
        lecturerDepartments[lecturerCount] = null;

        lecturerCount++;
    }

    public void addDepartment(String name, int studentCount) throws CollegeActionException {
        if (isEmpty(name) || studentCount < 0) {
            throw new CollegeActionException("Department details are not valid.");
        }

        if (findDepartmentByName(name) != null) {
            throw new CollegeActionException("Department already exists.");
        }

        if (departmentCount == departments.length) {
            increaseDepartmentsArray();
        }

        departments[departmentCount] = new Department(name.trim(), studentCount);
        departmentCount++;
    }

    public void addCommittee(String committeeName, String chairmanName) throws CollegeActionException {
        if (isEmpty(committeeName) || isEmpty(chairmanName)) {
            throw new CollegeActionException("Committee name and chairman name are required.");
        }

        if (findCommitteeByName(committeeName) != null) {
            throw new CollegeActionException("Committee already exists.");
        }

        Lecturer chairman = findLecturerByName(chairmanName);

        if (chairman == null) {
            throw new CollegeActionException("Chairman lecturer does not exist.");
        }

        if (!chairman.isDoctorOrAbove()) {
            throw new CollegeActionException("Committee chairman must be a doctor or professor.");
        }

        if (committeeCount == committees.length) {
            increaseCommitteesArray();
        }

        Committee committee = new Committee(committeeName.trim(), chairman);

        chairman.addCommittee(committee);

        committees[committeeCount] = committee;
        committeeCount++;
    }

    public void addLecturerToDepartment(String lecturerName, String departmentName) throws CollegeActionException {
        int lecturerIndex = findLecturerIndexByName(lecturerName);
        Department department = findDepartmentByName(departmentName);

        if (lecturerIndex == -1 || department == null) {
            throw new CollegeActionException("Lecturer or department does not exist.");
        }

        Lecturer lecturer = lecturers[lecturerIndex];

        if (lecturer.getDepartment() != null) {
            throw new CollegeActionException("Lecturer is already assigned to a department.");
        }

        department.addLecturer(lecturer);
        lecturer.setDepartment(department);
        lecturerDepartments[lecturerIndex] = department;
    }

    public void addLecturerToCommittee(String lecturerName, String committeeName) throws CollegeActionException {
        int lecturerIndex = findLecturerIndexByName(lecturerName);
        Committee committee = findCommitteeByName(committeeName);

        if (lecturerIndex == -1 || committee == null) {
            throw new CollegeActionException("Lecturer or committee does not exist.");
        }

        Lecturer lecturer = lecturers[lecturerIndex];

        committee.addMember(lecturer);

        try {
            lecturer.addCommittee(committee);
        } catch (CollegeActionException e) {
            try {
                committee.removeMember(lecturer);
            } catch (CollegeActionException rollbackException) {
            }

            throw e;
        }
    }

    public void removeLecturerFromCommittee(String lecturerName, String committeeName) throws CollegeActionException {
        int lecturerIndex = findLecturerIndexByName(lecturerName);
        Committee committee = findCommitteeByName(committeeName);

        if (lecturerIndex == -1 || committee == null) {
            throw new CollegeActionException("Lecturer or committee does not exist.");
        }

        Lecturer lecturer = lecturers[lecturerIndex];

        committee.removeMember(lecturer);

        try {
            lecturer.removeCommittee(committee);
        } catch (CollegeActionException e) {
            try {
                committee.addMember(lecturer);
            } catch (CollegeActionException rollbackException) {
            }

            throw e;
        }
    }

    public void updateCommitteeChairman(String committeeName, String chairmanName) throws CollegeActionException {
        Committee committee = findCommitteeByName(committeeName);
        int chairmanIndex = findLecturerIndexByName(chairmanName);

        if (committee == null || chairmanIndex == -1) {
            throw new CollegeActionException("Committee or chairman lecturer does not exist.");
        }

        Lecturer chairman = lecturers[chairmanIndex];

        if (!chairman.isDoctorOrAbove()) {
            throw new CollegeActionException("Committee chairman must be a doctor or professor.");
        }

        Lecturer oldChairman = committee.getChairman();
        boolean addedCommitteeToNewChairman = false;

        if (!chairman.hasCommittee(committee)) {
            chairman.addCommittee(committee);
            addedCommitteeToNewChairman = true;
        }

        try {
            committee.setChairman(chairman);
        } catch (CollegeActionException e) {
            if (addedCommitteeToNewChairman) {
                try {
                    chairman.removeCommittee(committee);
                } catch (CollegeActionException rollbackException) {
                }
            }

            throw e;
        }

        if (oldChairman != chairman && !committee.hasMember(oldChairman) && oldChairman.hasCommittee(committee)) {
            oldChairman.removeCommittee(committee);
        }
    }

    public void cloneCommittee(String originalCommitteeName) throws CollegeActionException {
        Committee originalCommittee = findCommitteeByName(originalCommitteeName);

        if (originalCommittee == null) {
            throw new CollegeActionException("Original committee does not exist.");
        }

        String newCommitteeName = "new-" + originalCommittee.getName();

        if (findCommitteeByName(newCommitteeName) != null) {
            throw new CollegeActionException("Committee " + newCommitteeName + " already exists.");
        }

        if (committeeCount == committees.length) {
            increaseCommitteesArray();
        }

        Committee newCommittee = new Committee(newCommitteeName, originalCommittee.getChairman());
        Lecturer[] lecturersAddedToNewCommittee = new Lecturer[originalCommittee.getMemberCount() + 1];
        int lecturersAddedCount = 0;

        try {
            Lecturer chairman = originalCommittee.getChairman();
            chairman.addCommittee(newCommittee);
            lecturersAddedToNewCommittee[lecturersAddedCount] = chairman;
            lecturersAddedCount++;

            for (int i = 0; i < originalCommittee.getMemberCount(); i++) {
                Lecturer member = originalCommittee.getMember(i);
                newCommittee.addMember(member);
                member.addCommittee(newCommittee);
                lecturersAddedToNewCommittee[lecturersAddedCount] = member;
                lecturersAddedCount++;
            }
        } catch (CollegeActionException e) {
            for (int i = 0; i < lecturersAddedCount; i++) {
                try {
                    lecturersAddedToNewCommittee[i].removeCommittee(newCommittee);
                } catch (CollegeActionException rollbackException) {
                }
            }

            throw e;
        }

        committees[committeeCount] = newCommittee;
        committeeCount++;
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

    public int getArticleCountForLecturer(String lecturerName) throws CollegeActionException {
        Lecturer lecturer = findLecturerByName(lecturerName);

        if (lecturer == null) {
            throw new CollegeActionException("Lecturer does not exist.");
        }

        if (!(lecturer instanceof ArticleWriter)) {
            throw new CollegeActionException("Lecturer is not a doctor or professor.");
        }

        ArticleWriter writer = (ArticleWriter) lecturer;
        return writer.getArticleCount();
    }

    public int getCommitteeStaffCount(String committeeName) throws CollegeActionException {
        Committee committee = findCommitteeByName(committeeName);

        if (committee == null) {
            throw new CollegeActionException("Committee does not exist.");
        }

        return committee.getStaffCount();
    }

    public int getCommitteeTotalArticleCount(String committeeName) throws CollegeActionException {
        Committee committee = findCommitteeByName(committeeName);

        if (committee == null) {
            throw new CollegeActionException("Committee does not exist.");
        }

        return committee.getTotalArticleCount();
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

    @Override
    public String toString() {
        return "College name: " + name +
                ", Lecturers count: " + lecturerCount +
                ", Departments count: " + departmentCount +
                ", Committees count: " + committeeCount;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof College)) {
            return false;
        }

        College otherCollege = (College) other;

        if (name == null) {
            return otherCollege.name == null;
        }

        return name.equalsIgnoreCase(otherCollege.name);
    }
}
