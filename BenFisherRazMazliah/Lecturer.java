package BenFisherRazMazliah;

public class Lecturer {
    private final String name;
    private final int idNumber;
    private final Degree degree;
    private final String degreeName;
    private final double salary;
    private Department department;
    private Committee[] committees;
    private int committeeCount;

    public Lecturer(String name, int idNumber, Degree degree, String degreeName, double salary) {
        this.name = name;
        this.idNumber = idNumber;
        this.degree = degree;
        this.degreeName = degreeName;
        this.salary = salary;
        this.department = null;
        this.committees = new Committee[2];
        this.committeeCount = 0;
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

    private void increaseCommitteesArray() {
        Committee[] newCommittees = new Committee[committees.length * 2];

        for (int i = 0; i < committeeCount; i++) {
            newCommittees[i] = committees[i];
        }

        committees = newCommittees;
    }

    public void addCommittee(Committee committee) throws CollegeActionException {
        if (committee == null) {
            throw new CollegeActionException("Committee does not exist.");
        }

        if (hasCommittee(committee)) {
            throw new CollegeActionException("Lecturer is already assigned to this committee.");
        }

        if (committeeCount == committees.length) {
            increaseCommitteesArray();
        }

        committees[committeeCount] = committee;
        committeeCount++;
    }

    public void removeCommittee(Committee committee) throws CollegeActionException {
        if (committee == null) {
            throw new CollegeActionException("Committee does not exist.");
        }

        for (int i = 0; i < committeeCount; i++) {
            if (committees[i] == committee) {
                for (int j = i; j < committeeCount - 1; j++) {
                    committees[j] = committees[j + 1];
                }

                committees[committeeCount - 1] = null;
                committeeCount--;
                return;
            }
        }

        throw new CollegeActionException("Lecturer is not assigned to this committee.");
    }

    public boolean hasCommittee(Committee committee) {
        if (committee == null) {
            return false;
        }

        for (int i = 0; i < committeeCount; i++) {
            if (committees[i] == committee) {
                return true;
            }
        }

        return false;
    }

    public String getCommitteeNames() {
        if (committeeCount == 0) {
            return "No committees";
        }

        String result = "";

        for (int i = 0; i < committeeCount; i++) {
            if (i > 0) {
                result += ", ";
            }

            result += committees[i].getName();

            if (committees[i].getChairman() == this) {
                result += " (chairman)";
            }
        }

        return result;
    }

    public boolean isDoctorOrAbove() {
        return degree.isDoctorOrAbove();
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", ID: " + idNumber +
                ", Degree: " + degree +
                ", Degree name: " + degreeName +
                ", Salary: " + salary;
    }
}
