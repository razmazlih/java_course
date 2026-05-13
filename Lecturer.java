public class Lecturer {
    private String name;
    private int idNumber;
    private Degree degree;
    private String degreeName;
    private double salary;
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
        this.committees = new Committee[10];
        this.committeeCount = 0;
    }

    private void increaseCommitteesArray() {
        Committee[] newCommittees = new Committee[committees.length * 2];

        for (int i = 0; i < committeeCount; i++) {
            newCommittees[i] = committees[i];
        }

        committees = newCommittees;
    }

    public boolean addCommittee(Committee committee) {
        if (committee == null) {
            return false;
        }

        for (int i = 0; i < committeeCount; i++) {
            if (committees[i] == committee) {
                return false;
            }
        }
        
        if (committeeCount == committees.length) {
            increaseCommitteesArray();
        }

        committees[committeeCount] = committee;
        committeeCount++;
        return true;
    }

    public boolean isDoctorOrAbove() {
        return degree.isDoctorOrAbove();
    }
}
