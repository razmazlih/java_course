package ben_fisher_raz_matzliach;

import java.util.ArrayList;
import java.io.Serializable;

public class College implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;

    private ArrayList<Lecturer> lecturers;
    private ArrayList<Department> departments;
    private ArrayList<Committee> committees;

    public College(String name) {
        this.name = name;
        this.lecturers = new ArrayList<Lecturer>();
        this.departments = new ArrayList<Department>();
        this.committees = new ArrayList<Committee>();
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

        lecturers.add(lecturer);
    }

    public void addDepartment(String name, int studentCount) throws CollegeActionException {
        if (isEmpty(name) || studentCount < 0) {
            throw new CollegeActionException("Department details are not valid.");
        }

        if (findDepartmentByName(name) != null) {
            throw new CollegeActionException("Department already exists.");
        }

        departments.add(new Department(name.trim(), studentCount));
    }

    public void addCommittee(String committeeName, String chairmanName, CommitteeMemberType memberType)
            throws CollegeActionException {
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

        Committee committee = new Committee(committeeName.trim(), chairman, memberType);

        chairman.addCommittee(committee);

        committees.add(committee);
    }

    public void addLecturerToDepartment(String lecturerName, String departmentName) throws CollegeActionException {
        Lecturer lecturer = findLecturerByName(lecturerName);
        Department department = findDepartmentByName(departmentName);

        if (lecturer == null || department == null) {
            throw new CollegeActionException("Lecturer or department does not exist.");
        }

        Department currentDepartment = lecturer.getDepartment();

        if (currentDepartment == department) {
            throw new CollegeActionException("Lecturer is already assigned to this department.");
        }

        if (currentDepartment != null) {
            currentDepartment.removeLecturer(lecturer);
            lecturer.setDepartment(null);
        }

        try {
            department.addLecturer(lecturer);
            lecturer.setDepartment(department);
        } catch (CollegeActionException e) {
            if (currentDepartment != null) {
                try {
                    currentDepartment.addLecturer(lecturer);
                    lecturer.setDepartment(currentDepartment);
                } catch (CollegeActionException rollbackException) {
                }
            }

            throw e;
        }
    }

    public void addLecturerToCommittee(String lecturerName, String committeeName) throws CollegeActionException {
        Lecturer lecturer = findLecturerByName(lecturerName);
        Committee committee = findCommitteeByName(committeeName);

        if (lecturer == null || committee == null) {
            throw new CollegeActionException("Lecturer or committee does not exist.");
        }

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
        Lecturer lecturer = findLecturerByName(lecturerName);
        Committee committee = findCommitteeByName(committeeName);

        if (lecturer == null || committee == null) {
            throw new CollegeActionException("Lecturer or committee does not exist.");
        }

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
        Lecturer chairman = findLecturerByName(chairmanName);

        if (committee == null || chairman == null) {
            throw new CollegeActionException("Committee or chairman lecturer does not exist.");
        }

        Lecturer oldChairman = committee.getChairman();
        boolean wasRegularMember = committee.hasMember(chairman);
        boolean addedCommitteeToNewChairman = false;

        try {
            committee.setChairman(chairman);

            if (!chairman.hasCommittee(committee)) {
                chairman.addCommittee(committee);
                addedCommitteeToNewChairman = true;
            }

            if (wasRegularMember) {
                committee.removeMember(chairman);
            }
        } catch (CollegeActionException e) {
            try {
                committee.setChairman(oldChairman);
            } catch (InvalidChairmanException rollbackException) {
            }

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

        Committee newCommittee = new Committee(newCommitteeName, originalCommittee.getChairman(),
                originalCommittee.getAllowedMemberType());
        ArrayList<Lecturer> lecturersAddedToNewCommittee = new ArrayList<Lecturer>();

        try {
            Lecturer chairman = originalCommittee.getChairman();
            chairman.addCommittee(newCommittee);
            lecturersAddedToNewCommittee.add(chairman);

            for (int i = 0; i < originalCommittee.getMemberCount(); i++) {
                Lecturer member = originalCommittee.getMember(i);
                newCommittee.addMember(member);
                member.addCommittee(newCommittee);
                lecturersAddedToNewCommittee.add(member);
            }
        } catch (CollegeActionException e) {
            for (int i = 0; i < lecturersAddedToNewCommittee.size(); i++) {
                try {
                    lecturersAddedToNewCommittee.get(i).removeCommittee(newCommittee);
                } catch (CollegeActionException rollbackException) {
                }
            }

            throw e;
        }

        committees.add(newCommittee);
    }

    public double getAverageSalary() {
        if (lecturers.isEmpty()) {
            return 0;
        }

        double sum = 0;

        for (int i = 0; i < lecturers.size(); i++) {
            sum += lecturers.get(i).getSalary();
        }

        return sum / lecturers.size();
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
        if (lecturers.isEmpty()) {
            return "No lecturers to display.";
        }

        String result = "Lecturers:";

        for (int i = 0; i < lecturers.size(); i++) {
            result += "\n" + (i + 1) + ". " + lecturers.get(i);
            result += "\n   Department: " + getDepartmentNameForLecturer(i);
            result += "\n   Committees: " + lecturers.get(i).getCommitteeNames();
        }

        return result;
    }

    public String getAllCommitteesDetails() {
        if (committees.isEmpty()) {
            return "No committees to display.";
        }

        String result = "Committees:";

        for (int i = 0; i < committees.size(); i++) {
            result += "\n" + (i + 1) + ". " + committees.get(i);
        }

        return result;
    }

    private Lecturer findLecturerByName(String name) {
        if (isEmpty(name)) {
            return null;
        }

        String fixedName = name.trim();

        for (int i = 0; i < lecturers.size(); i++) {
            if (lecturers.get(i).getName().equalsIgnoreCase(fixedName)) {
                return lecturers.get(i);
            }
        }

        return null;
    }

    private Lecturer findLecturerById(int idNumber) {
        for (int i = 0; i < lecturers.size(); i++) {
            if (lecturers.get(i).getIdNumber() == idNumber) {
                return lecturers.get(i);
            }
        }

        return null;
    }

    private Department findDepartmentByName(String name) {
        if (isEmpty(name)) {
            return null;
        }

        String fixedName = name.trim();

        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getName().equalsIgnoreCase(fixedName)) {
                return departments.get(i);
            }
        }

        return null;
    }

    private Committee findCommitteeByName(String name) {
        if (isEmpty(name)) {
            return null;
        }

        String fixedName = name.trim();

        for (int i = 0; i < committees.size(); i++) {
            if (committees.get(i).getName().equalsIgnoreCase(fixedName)) {
                return committees.get(i);
            }
        }

        return null;
    }

    private String getDepartmentNameForLecturer(int lecturerIndex) {
        Department department = lecturers.get(lecturerIndex).getDepartment();

        if (department == null) {
            return "No department";
        }

        return department.getName();
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "College name: " + name +
                ", Lecturers count: " + lecturers.size() +
                ", Departments count: " + departments.size() +
                ", Committees count: " + committees.size();
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
