package ben_fisher_raz_matzliach;

import java.util.ArrayList;
import java.io.Serializable;

public class Lecturer implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final int idNumber;
    private final Degree degree;
    private final String degreeName;
    private final double salary;
    private Department department;
    private ArrayList<Committee> committees;

    public Lecturer(String name, int idNumber, Degree degree, String degreeName, double salary) {
        this.name = name;
        this.idNumber = idNumber;
        this.degree = degree;
        this.degreeName = degreeName;
        this.salary = salary;
        this.department = null;
        this.committees = new ArrayList<Committee>();
    }

    public String getName() {
        return name;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public double getSalary() {
        return salary;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public void addCommittee(Committee committee) throws CollegeActionException {
        if (committee == null) {
            throw new CollegeActionException("Committee does not exist.");
        }

        if (hasCommittee(committee)) {
            throw new CollegeActionException("Lecturer is already assigned to this committee.");
        }

        committees.add(committee);
    }

    public void removeCommittee(Committee committee) throws CollegeActionException {
        if (committee == null) {
            throw new CollegeActionException("Committee does not exist.");
        }

        if (!committees.remove(committee)) {
            throw new CollegeActionException("Lecturer is not assigned to this committee.");
        }
    }

    public boolean hasCommittee(Committee committee) {
        if (committee == null) {
            return false;
        }

        return committees.contains(committee);
    }

    public String getCommitteeNames() {
        if (committees.isEmpty()) {
            return "No committees";
        }

        String result = "";

        for (int i = 0; i < committees.size(); i++) {
            if (i > 0) {
                result += ", ";
            }

            result += committees.get(i).getName();

            if (committees.get(i).getChairman() == this) {
                result += " (chairman)";
            }
        }

        return result;
    }

    public boolean isDoctorOrAbove() {
        return degree.isDoctorOrAbove();
    }

    public int getArticleCount() {
        return 0;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", ID: " + idNumber +
                ", Degree: " + degree +
                ", Degree name: " + degreeName +
                ", Salary: " + salary;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Lecturer)) {
            return false;
        }

        Lecturer otherLecturer = (Lecturer) other;
        return idNumber == otherLecturer.idNumber;
    }
}
